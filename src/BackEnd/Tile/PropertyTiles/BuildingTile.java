package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.AbstractAssetHolder;
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;
import BackEnd.Card.BuildingCard;
import BackEnd.Card.PropertyCard;
import Controller.Game;
import org.w3c.dom.Element;

import java.util.List;

public class BuildingTile extends AbstractPropertyTile {
    private String tilecolor;
    private BuildingCard card;

    public BuildingTile(Bank bank, PropertyCard card, String tiletype, double tileprice, String tilecolor) {
        super(bank, card, tiletype, tileprice);
        this.card = (BuildingCard)this.getCard();
        this.tilecolor = tilecolor;
    }

    public BuildingTile(Element n){
        super(n);
    }

    //store these as strings and make a hashmap of price lookup
    public double calculateRentPrice(Game game) {
        if (isMortgaged()) {
            return 0;
        }
        else {
            return card.lookupPrice(getCurrentInUpgradeOrder());
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

    /**
     * Houses and Hotels may be sold back to the Bank at any time for one-half the price paid for them.
     * All houses on one colour-group may be sold at once,
     * or they may be sold one house at a time (one hotel equals five houses),
     * evenly, in reverse of the manner in which they were erected.
     */
    public void sellAllBuildingsOnTile() {
        getBank().recalculateTotalPropertiesLeftAfterWholeSale(this);
        setCurrentInUpgradeOrder(card.getUpgradeOrderAtIndex(0));
        getBank().paysTo(getOwner(),sellToBankPrice());
    }

    /**
     * or they may be sold one house at a time (one hotel equals five houses),
     * evenly, in reverse of the manner in which they were erected.
     */
    public void sellOneAtATime(List<AbstractPropertyTile> properties) {
        if(checkIfUpdatingEvenly(properties,false));
        getBank().recalculateTotalPropertiesLeftOneBuildingUpdate(this);
        setCurrentInUpgradeOrder(card.previousInUpgradeOrder(getCurrentInUpgradeOrder()));
    }

    @Override
    public void sellTo(AbstractAssetHolder assetHolder, double price, List<AbstractPropertyTile> sameColorProperties) {
        super.sellTo(assetHolder,price, sameColorProperties);
        if (assetHolder instanceof  AbstractPlayer
                && checkIfPlayerOwnsAllOfOneColor(sameColorProperties)
                && card.getUpgradeOrderIndexOf(getCurrentInUpgradeOrder()) == 0){
                upgrade((AbstractPlayer) assetHolder, sameColorProperties);
        }
    }

    public void upgrade(AbstractPlayer player, List<AbstractPropertyTile> sameCategoryProperties) {
        //this will be throwing an exception (see property card class)
        //this can only happen if owner is player -- controller must call checkIfOwnerIsCurrentPlayer
//        List<AbstractPropertyTile> properties = board.getColorListMap().get(this.getTilecolor());
        if (checkIfPlayerOwnsAllOfOneColor(sameCategoryProperties) && checkIfUpdatingEvenly(sameCategoryProperties, true)) {
            //throw exception if not caught in nextInUpgradeOrder
            double payment = card.getPriceNeededToUpgradeLookupTable(getCurrentInUpgradeOrder());
            player.paysTo(getBank(), payment);
            setCurrentInUpgradeOrder(card.nextInUpgradeOrder(getCurrentInUpgradeOrder()));
            getBank().recalculateTotalPropertiesLeftOneBuildingUpdate(this);
        }
    }

    public boolean checkIfPlayerOwnsAllOfOneColor(List<AbstractPropertyTile> properties) {
        for (AbstractPropertyTile tile : properties) {
            if (tile instanceof BuildingTile && !(tile.getOwner().equals(this.getOwner()))) {
                //throw exception: YOU CANNOT UPGRADE WITHOUT A MONOPOLY ON COLOR
                return false;
            }
        }
        return true;
    }

    //UPGRADE OR DOWNGRADE
    private boolean checkIfUpdatingEvenly(List<AbstractPropertyTile> properties, boolean upgrade) {
        int thresholdForUpdate = card.getUpgradeOrderIndexOf(this.getCurrentInUpgradeOrder());
        for (AbstractPropertyTile tile : properties) {
            if (tile instanceof BuildingTile) {
                BuildingTile currentTile = (BuildingTile) tile;
                BuildingCard currentCard = (BuildingCard) currentTile.getCard();
                if (upgrade) {
                    if (currentCard.getUpgradeOrderIndexOf(currentTile.getCurrentInUpgradeOrder()) < thresholdForUpdate) {
                        //throw exception: YOU CANNOT UPGRADE UNEVENLY
                        return false;
                    }
                }
                else {
                    if (currentCard.getUpgradeOrderIndexOf(currentTile.getCurrentInUpgradeOrder()) > thresholdForUpdate) {
                        //throw exception: YOU CANNOT DOWNGRADE UNEVENLY
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public double sellToBankPrice() {
        if (!isMortgaged()) {
//  REMIND LUIS: DIVIDE BY 2 for selling back to bank
//            return (numberOfHouses * card.lookupPrice(currentInUpgradeOrder) + numberOfHotels * card.getPropertyHotelPrice()) / 2;
            return card.getOneBuildingSellToBankPrice(getCurrentInUpgradeOrder());
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
        return (card.getUpgradeOrderIndexOf(getCurrentInUpgradeOrder()) > 0);
    }

    public String getTilecolor() {
        return tilecolor;
    }
}