package BackEnd.Tile;

import BackEnd.AssetHolder.Bank;
import BackEnd.Card.PropertyCard;
import BackEnd.Tile.AbstractNonBuildingPropertyTile;
import org.w3c.dom.Element;

public class RailroadTile extends AbstractNonBuildingPropertyTile {


    public RailroadTile(Bank bank, PropertyCard card, String tiletype, double tileprice, int index) {
        super( bank, card, tiletype, tileprice, index);
    }

    public RailroadTile(Bank bank, PropertyCard card, Element n){
        super(bank, card, n);
    }

    //public RailroadTile(Element n) {
    //    super( n );
    //}

    public double calculateRentPrice(int roll) {
        if (isMortgaged()) {
            return 0;
        }
        else {
            return getCard().lookupPrice(getCurrentInUpgradeOrder());
        }
    }



}
