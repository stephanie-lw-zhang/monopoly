package backend.tile;

import backend.assetholder.AbstractAssetHolder;
import backend.assetholder.AbstractPlayer;
import backend.assetholder.Bank;
import backend.card.BuildingCard;
import backend.card.PropertyCard;
import org.w3c.dom.Element;

import java.util.List;
import java.util.Map;

public class BuildingTile extends backend.tile.AbstractPropertyTile {
    private String tilecolor;
    private BuildingCard card;
    private int tileprice;
    private int tilenumber;
    private String tilename;
    private int tilerent;
    private int tilerentwithcolorset;
    private int tilerent1house;
    private int tilerent2house;
    private int tilerent3house;
    private int tilerent4house;
    private int tilerenthotel;
    private int tilemortgagevalue;
    private int tilehouseprice;
    private int tilehotelprice;

    public BuildingTile(Bank bank, PropertyCard card, String tiletype, double tileprice, String tilecolor, int index) {
        super(bank, card, tiletype, tileprice, index);
        this.card = (BuildingCard) this.getCard();
        this.tilecolor = tilecolor;
    }

    public BuildingTile(Bank bank, Element n){
        super(bank, n);
    }

    //store these as strings and make a hashmap of price lookup
    public double calculateRentPrice(int roll) {
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
<<<<<<< HEAD
<<<<<<< HEAD
        getBank().paysFullAmountTo(getOwner(), sellBuildingToBankPrice());
=======
        getBank().payFullAmountTo(getOwner(), sellBuildingToBankPrice());
>>>>>>> 14f2f2b37ae82b8302118ba2ecba0554310e8564
=======
        getBank().payFullAmountTo(getOwner(), sellBuildingToBankPrice());
>>>>>>> 9a9c68a39301a66f4dfefd6560b6893148453277
    }

    /**
     * or they may be sold one house at a time (one hotel equals five houses),
     * evenly, in reverse of the manner in which they were erected.
     */
    public void sellOneBuilding(List<AbstractPropertyTile> properties) {
        if(checkIfUpdatingEvenly(properties,false));
        getBank().recalculateTotalPropertiesLeftOneBuildingUpdate(this);
<<<<<<< HEAD
<<<<<<< HEAD
        getBank().paysFullAmountTo( getOwner(), sellBuildingToBankPrice() );
=======
        getBank().payFullAmountTo( getOwner(), sellBuildingToBankPrice() );
>>>>>>> 14f2f2b37ae82b8302118ba2ecba0554310e8564
=======
        getBank().payFullAmountTo( getOwner(), sellBuildingToBankPrice() );
>>>>>>> 9a9c68a39301a66f4dfefd6560b6893148453277
        setCurrentInUpgradeOrder(card.previousInUpgradeOrder(getCurrentInUpgradeOrder()));
    }

    @Override
    public void sellTo(AbstractAssetHolder assetHolder, double price, List<AbstractPropertyTile> sameColorProperties) {
        super.sellTo(assetHolder,price, sameColorProperties);
        System.out.println(sameColorProperties);
        if (assetHolder.checkIfOwnsAllOf(sameColorProperties) && card.getUpgradeOrderIndexOf(getCurrentInUpgradeOrder()) == 0){
            //assume upgrade order is as so: no house not all of same color properties, no house all of same color properties, etc.
                upgrade((AbstractPlayer) assetHolder, sameColorProperties);
        }
    }

    public void upgrade(AbstractPlayer player, List<AbstractPropertyTile> sameCategoryProperties) {
        //this will be throwing an exception (see property card class)
        //this can only happen if owner is player -- controller must call checkIfOwnerIsCurrentPlayer
//        List<AbstractPropertyTile> properties = board.getColorListMap().get(this.getTilecolor());
        String building = card.getBasePropertyType(card.nextInUpgradeOrder(getCurrentInUpgradeOrder()));
        if (player.checkIfOwnsAllOf(sameCategoryProperties) && checkIfUpdatingEvenly(sameCategoryProperties, true) && getBank().buildingsRemain( building )) {
            //throw exception if not caught in nextInUpgradeOrder
            double payment = card.getPriceNeededToUpgradeLookupTable(getCurrentInUpgradeOrder());
<<<<<<< HEAD
<<<<<<< HEAD
            player.paysFullAmountTo(getBank(), payment);
=======
            player.payFullAmountTo(getBank(), payment);
>>>>>>> 14f2f2b37ae82b8302118ba2ecba0554310e8564
=======
            player.payFullAmountTo(getBank(), payment);
>>>>>>> 9a9c68a39301a66f4dfefd6560b6893148453277
            setCurrentInUpgradeOrder(card.nextInUpgradeOrder(getCurrentInUpgradeOrder()));
            getBank().recalculateTotalPropertiesLeftOneBuildingUpdate(this);
        }
    }



    //UPGRADE OR DOWNGRADE
    private boolean checkIfUpdatingEvenly(List<AbstractPropertyTile> properties, boolean upgrade) {
        int thresholdForUpdate = card.getUpgradeOrderIndexOf(this.getCurrentInUpgradeOrder());
        for (AbstractPropertyTile tile : properties) {
                if (individualUpdateEvenCheck( upgrade, thresholdForUpdate, (BuildingTile) tile )) return false;
        }
        return true;
    }

    @Override
    public boolean individualUpdateEvenCheck(boolean upgrade, int thresholdForUpdate, BuildingTile tile) {
        BuildingTile currentTile = tile;
        BuildingCard currentCard = (BuildingCard) currentTile.getCard();
        if (upgrade) {
            if (currentCard.getUpgradeOrderIndexOf(currentTile.getCurrentInUpgradeOrder()) < thresholdForUpdate) {
                //throw exception: YOU CANNOT UPGRADE UNEVENLY
                return true;
            }
        }
        else {
            if (currentCard.getUpgradeOrderIndexOf(currentTile.getCurrentInUpgradeOrder()) > thresholdForUpdate) {
                //throw exception: YOU CANNOT DOWNGRADE UNEVENLY
                return true;
            }
        }
        return false;
    }

    //    @Override
    public double sellBuildingToBankPrice() {
        if (!isMortgaged()) {
//  REMIND LUIS: DIVIDE BY 2 for selling back to bank
//            return (numberOfHouses * card.lookupPrice(currentInUpgradeOrder) + numberOfHotels * card.getPropertyHotelPrice()) / 2;
            return card.getOneBuildingSellToBankPrice(getCurrentInUpgradeOrder());
        }
        else {
            //throw exception: CANNOT SELL_TO_BANK WHEN MORTGAGED
        }
        return 0;
    }

    // Before an improved property can be mortgaged, all the Houses and Hotels on all the properties of its color-group must be sold back to the Bank at half price.
/** need controller logic: throw exception for **/
//    public void mortgageImprovedProperty(AbstractPlayer player, AbstractBoard board) {
//        List<BuildingTile> properties = board.getColorListMap().get(this.getTilecolor());
//        for (BuildingTile building : properties) {
<<<<<<< HEAD
<<<<<<< HEAD
//            getBank().paysFullAmountTo(player,building.sellBuildingToBankPrice());
=======
//            getBank().payFullAmountTo(player,building.sellBuildingToBankPrice());
>>>>>>> 14f2f2b37ae82b8302118ba2ecba0554310e8564
=======
//            getBank().payFullAmountTo(player,building.sellBuildingToBankPrice());
>>>>>>> 9a9c68a39301a66f4dfefd6560b6893148453277
//            getBank().addHouses(this.getNumberOfHouses());
//            getBank().addHotels(this.getNumberOfHotels());
//        }
//        super.mortgageProperty();
//    }

    public boolean checkIfMortgagingImprovedProperty() {
        return (card.getUpgradeOrderIndexOf(getCurrentInUpgradeOrder()) > 0);
    }

    //private static String getTagValue(String tag, Element element) {
    //    NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
    //    Node node = nodeList.item(0);
    //    return node.getNodeValue();
    //}

    public String getTilecolor() {
        return tilecolor;
    }

    @Override
    public void recalculateTotalPropertiesLeftOneBuildingUpdate(Map<String,Integer> totalPropertiesLeft){
        String current = this.getCurrentInUpgradeOrder();
        String baseKey = card.getBasePropertyType(current);
        Integer baseValue = card.getNumericValueOfPropertyType(current);
        int currentIndex = card.getUpgradeOrderIndexOf(current);
        if(currentIndex > 0){
            String previous = card.getUpgradeOrderAtIndex(currentIndex - 1);
            String previousBaseKey = card.getBasePropertyType(previous);
            Integer previousBaseValue = card.getNumericValueOfPropertyType(previous);
            totalPropertiesLeft.put(previousBaseKey, totalPropertiesLeft.get(previousBaseKey) + previousBaseValue);
        }
        totalPropertiesLeft.put(baseKey, totalPropertiesLeft.get(baseKey) - baseValue);
    }

    @Override
    public void recalculateTotalPropertiesLeftAfterWholeSale(Map<String,Integer> totalPropertiesLeft){
        String current = this.getCurrentInUpgradeOrder();
        String baseKey = card.getBasePropertyType(current);
        Integer baseValue = card.getNumericValueOfPropertyType(current);
        totalPropertiesLeft.put(baseKey, totalPropertiesLeft.get(baseKey) + baseValue);
    }
}