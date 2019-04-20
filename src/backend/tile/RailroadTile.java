package backend.tile;

import backend.assetholder.Bank;
import backend.card.PropertyCard;
import org.w3c.dom.Element;

public class RailroadTile extends AbstractNonBuildingPropertyTile {


    public RailroadTile(Bank bank, PropertyCard card, String tiletype, double tileprice, int index) {
        super( bank, card, tiletype, tileprice, index);
    }

    public RailroadTile(Bank bank, Element n){
        super(bank, n);
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
