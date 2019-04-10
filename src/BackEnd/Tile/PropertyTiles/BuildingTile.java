package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Board.AbstractBoard;
import BackEnd.Card.AbstractCard;
import BackEnd.Card.BuildingCard;
import Controller.Game;

import java.awt.*;
import java.util.List;

public class BuildingTile extends AbstractPropertyTile {
//change color
    private Color tilecolor;
//    private int numberOfHouses;
//    private int numberOfHotels;
    private String currentInUpgradeOrder;
    private BuildingCard card;

    public BuildingTile(Bank bank, AbstractCard card, String tiletype, double tileprice, Color tilecolor) {
        super(bank, card, tiletype, tileprice);
        this.card = (BuildingCard)this.getCard();
        this.tilecolor = tilecolor;
//        numberOfHouses = 0;
//        numberOfHotels = 0;
        currentInUpgradeOrder = this.card.getUpgradeOrderIndex(0);
    }

    //this is hardcoded loL!
    //store these as strings and make a hashmap of price lookup
    public double calculateRentPrice(Game game) {
        if (isMortgaged()) {
            return 0;
        }
        else {
            return card.lookupPrice(currentInUpgradeOrder);
            //REMIND LUIS ABOUT THIS?
//            else {
//                if (checkIfPlayerOwnsAllOfOneColor(game.getBoard().getColorListMap().get(this.getTilecolor()))) {
//                    return (card.getNoHousesOrHotelsRent() * 2);
//                }
//                else {
//                    return card.getNoHousesOrHotelsRent();
//                }
//            }
        }
    }

    public boolean checkIfOwnerIsCurrentPlayer(AbstractPlayer player) {
        return (!this.getOwner().equals(player));
            //throw exception: YOU DO NOT OWN THIS PROPERTY
    }

    public void upgrade(AbstractPlayer player, AbstractBoard board) {
        //this will be throwing an exception (see property card class)
        //this can only happen if owner is player -- controller must call checkIfOwnerIsCurrentPlayer
        List<BuildingTile> properties = board.getColorListMap().get(this.getTilecolor());
        if (checkIfPlayerOwnsAllOfOneColor(properties) && checkIfUpgradingEvenly(properties)) {
            if (this.getNumberOfHouses() < 4) {
                this.numberOfHouses++;
                getBank().subtractOneHouse();
            }
            else {
                this.numberOfHouses = 0;
                this.numberOfHotels++;
                getBank().subtractOneHotel();
                getBank().addHouses(4);
            }
        }
    }

    private boolean checkIfPlayerOwnsAllOfOneColor(List<BuildingTile> properties) {
        for (BuildingTile tile : properties) {
            if (!(tile.getOwner().equals(this.getOwner()))) {
                //throw exception: YOU CANNOT UPGRADE WITHOUT A MONOPOLY ON COLOR
                return false;
            }
        }
        return true;
    }

    private boolean checkIfUpgradingEvenly(List<BuildingTile> properties) {
        int minToUpgrade = card.getCurrentUpgradeOrderIndex(this.getCurrentInUpgradeOrder());
        for (BuildingTile tile : properties) {
            if (((BuildingCard)tile.getCard()).getCurrentUpgradeOrderIndex(tile.getCurrentInUpgradeOrder()) < minToUpgrade) {
                //throw exception: YOU CANNOT UPGRADE UNEVENLY
                return false;
            }
        }
        return true;
    }

    @Override
    public double sellToBankPrice() {
        if (!isMortgaged()) {
//  REMIND LUIS: DIVIDE BY 2 for selling back to bank
//            return (numberOfHouses * card.lookupPrice(currentInUpgradeOrder) + numberOfHotels * card.getPropertyHotelPrice()) / 2;
            return card.sellToBankPriceLookupTable(currentInUpgradeOrder);
        }
        else {
            //throw exception: CANNOT SELL WHEN MORTGAGED
        }
        return 0;
    }

    // Before an improved property can be mortgaged, all the Houses and Hotels on all the properties of its color-group must be sold back to the Bank at half price.
/** need controller logic: throw exception for **/
//    public void mortgageImprovedProperty(AbstractPlayer player, AbstractBoard board) {
//        List<BuildingTile> properties = board.getColorListMap().get(this.getTilecolor());
//        for (BuildingTile building : properties) {
//            getBank().paysTo(player,building.sellToBankPrice());
//            getBank().addHouses(this.getNumberOfHouses());
//            getBank().addHotels(this.getNumberOfHotels());
//        }
//        super.mortgageProperty();
//    }

    public boolean checkIfMortgagingImprovedProperty() {
        return (this.numberOfHotels > 0 || this.numberOfHouses > 0);
    }

    public Color getTilecolor() {
        return tilecolor;
    }

//    public int getNumberOfHouses() {
//        return numberOfHouses;
//    }
//
//    public int getNumberOfHotels() {
//        return numberOfHotels;
//    }

    public String getCurrentInUpgradeOrder() {
        return currentInUpgradeOrder;
    }
}
