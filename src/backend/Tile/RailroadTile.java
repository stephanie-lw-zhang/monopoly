package backend.Tile;

import backend.AssetHolder.Bank;
import backend.Card.PropertyCard;

public class RailroadTile extends AbstractNonBuildingPropertyTile {


    public RailroadTile(Bank bank, PropertyCard card, String tiletype, double tileprice) {
        super( bank, card, tiletype, tileprice );
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
