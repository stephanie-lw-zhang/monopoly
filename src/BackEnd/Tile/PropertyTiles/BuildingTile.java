package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.AbstractAssetHolder;
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.BuildingCard;
import BackEnd.Card.PropertyCard;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

public class BuildingTile extends AbstractPropertyTile {
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

    public BuildingTile(Bank bank, PropertyCard card, String tiletype, double tileprice, String tilecolor) {
        super(bank, card, tiletype, tileprice);
        this.card = (BuildingCard)this.getCard();
        this.tilecolor = tilecolor;
    }

    public BuildingTile(Element n){
        this.tileprice = Integer.parseInt(getTagValue("TilePrice", n));
        this.tilenumber = Integer.parseInt(getTagValue("TileNumber", n));
        this.tilename = getTagValue("TileName", n);
        this.tilerent = Integer.parseInt(getTagValue("TileRent", n));
        this.tilerentwithcolorset = Integer.parseInt(getTagValue("TileRentWithColorSet", n));
        this.tilerent1house = Integer.parseInt(getTagValue("TileRent1House", n));
        this.tilerent2house = Integer.parseInt(getTagValue("TileRent2House", n));
        this.tilerent3house = Integer.parseInt(getTagValue("TileRent3House", n));
        this.tilerent4house = Integer.parseInt(getTagValue("TileRent4House", n));
        this.tilerenthotel = Integer.parseInt(getTagValue("TileRentHotel", n));
        this.tilemortgagevalue = Integer.parseInt(getTagValue("TileMortgageValue", n));
        this.tilehouseprice = Integer.parseInt(getTagValue("TileHousePrice", n));
        this.tilehotelprice = Integer.parseInt(getTagValue("TileHotelPrice", n));
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

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    public String getTilecolor() {
        return tilecolor;
    }
}