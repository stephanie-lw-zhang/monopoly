package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Board.AbstractBoard;
import BackEnd.Card.AbstractCard;
import BackEnd.Card.BuildingCard;
import BackEnd.Tile.TileInterface;

import java.awt.*;
import java.util.List;

public class BuildingTile extends AbstractPropertyTile {

    private Color tilecolor;
    private int numberOfHouses;
    private int numberOfHotels;
    private BuildingCard card;
    private AbstractBoard board;

    public BuildingTile(Bank bank, AbstractCard card, String tiletype, double tileprice, Color tilecolor, AbstractBoard board) {
        super(bank, card, tiletype, tileprice);
        this.card = (BuildingCard)this.getCard();
        this.tilecolor = tilecolor;
        numberOfHouses = 0;
        numberOfHotels = 0;
        this.board = board;
    }

    //this is hardcoded loL!
    public double priceToPay() {
        if (numberOfHouses == 1) {
            return card.getPropertyRent1House();
        }
        else if (numberOfHouses == 2) {
            return card.getPropertyRent2House();
        }
        else if (numberOfHouses == 3) {
            return card.getPropertyRent3House();
        }
        else if (numberOfHouses == 4) {
            return card.getPropertyRent4House();
        }
        else if (numberOfHotels == 1) {
            return card.getPropertyRentHotel();
        }
        else {
            if (checkIfPlayerOwnsAllOfOneColor(board.getColorListMap().get(this.getTilecolor()))) {
                return (card.getNoHousesOrHotelsRent() * 2);
            }
            else {
                return card.getNoHousesOrHotelsRent();
            }
        }
    }

    //fix this
    public void upgrade(AbstractPlayer player) {
        if (!this.getOwner().equals(player)) {
            //throw exception: YOU DO NOT OWN THIS PROPERTY
        }
        else {
            List<BuildingTile> properties = board.getColorListMap().get(this.getTilecolor());
            if (checkIfPlayerOwnsAllOfOneColor(properties) && checkIfUpgradePossible(properties)) {
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

    private boolean checkIfUpgradePossible(List<BuildingTile> properties) {
        int minToUpgrade = this.getNumberOfHouses();
        for (BuildingTile tile : properties) {
            if (tile.getNumberOfHouses() < minToUpgrade) {
                //throw exception: YOU CANNOT UPGRADE UNEVENLY
                return false;
            }
        }
        return true;
    }

    @Override
    public double sellToBankPrice() {
        if (!isMortgaged()) {
            return (numberOfHouses * card.getPropertyHousePrice() + numberOfHotels + card.getPropertyHotelPrice()) / 2;
        }
        else {
            //throw exception: CANNOT SELL WHEN MORTGAGED
        }
        return 0;
    }

    public Color getTilecolor() {
        return tilecolor;
    }

    public int getNumberOfHouses() {
        return numberOfHouses;
    }

    public int getNumberOfHotels() {
        return numberOfHotels;
    }
}
