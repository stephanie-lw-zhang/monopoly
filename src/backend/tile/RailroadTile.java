package backend.tile;

import backend.assetholder.Bank;
import backend.card.PropertyCard;
import org.w3c.dom.Element;

public class RailroadTile extends AbstractNonBuildingPropertyTile {

    public RailroadTile(Bank bank, Element n){
        super(bank, n);
    }

    public double calculateRentPrice(int roll) {
        if (isMortgaged()) {
            return 0;
        }
        else {
            return getCard().lookupPrice(getCurrentInUpgradeOrder());
        }
    }



}
