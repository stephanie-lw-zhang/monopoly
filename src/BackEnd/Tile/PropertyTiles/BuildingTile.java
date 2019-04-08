package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;
import BackEnd.Card.BuildingCard;

import java.awt.*;
import java.util.List;

public class BuildingTile extends AbstractPropertyTile {

    private Color tilecolor;
    private int numberOfHouses;
    private int numberOfHotels;
    private BuildingCard card;

    public BuildingTile(Bank bank, AbstractCard card, String tiletype, double tileprice) {
        super(bank, card, tiletype, tileprice);
        card = (BuildingCard)this.getCard();
        numberOfHouses = 0;
        numberOfHotels = 0;
    }

    //fix this
    @Override
    public void applyLandedOnAction(AbstractPlayer player) {
        //controller will send player option to buy property? interact with front-end
        if (getOwner() instanceof Bank) {
            if (true) {
                buyProperty(player);
            }
//            else {
//                auctionProperty();
//            }
        }
        else if (!player.equals(getOwner())) {
            player.paysTo(getOwner(),priceToPay());
        }
    }

    private double priceToPay() {
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
            return card.getNoHousesOrHotelsRent();
        }
    }

    //fix this
    public void upgrade(AbstractPlayer player) {
        List<AbstractPropertyTile> properties = player.getProperties();
        Color thisColor = this.getTilecolor();
        if (properties.size() == 0) {
            //throw exception: NO PROPERTIES
        }
        else if (!this.getOwner().equals(player)) {
            //throw exception: YOU DO NOT OWN THIS PROPERTY
        }
        else {
            if (checkIfPlayerOwnsAllOfOneColor(properties, thisColor)) {

            }
        }
    }

    private boolean checkIfPlayerOwnsAllOfOneColor(List<AbstractPropertyTile> properties, Color thisColor) {
        int totalNumber = 0;
        for (AbstractPropertyTile tile : properties) {
            if (tile instanceof BuildingTile) {
                BuildingTile building = (BuildingTile)tile;
                if (building.getTilecolor().equals(thisColor)) {
                    totalNumber++;
                }
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
            //throw exception?
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
