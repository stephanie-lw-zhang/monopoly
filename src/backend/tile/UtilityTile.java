package backend.tile;

import backend.assetholder.Bank;
import backend.card.property_cards.PropertyCard;
import org.w3c.dom.Element;

public class UtilityTile extends AbstractNonBuildingPropertyTile {

    public UtilityTile(Bank bank, Element n){
        super(bank, n);
        setCard( new PropertyCard(n.getElementsByTagName("Card").item(0)) );
    }

    @Override
    public double calculateRentPrice(int roll) {
        if (isMortgaged()) {
            return 0;
        }
        else {
            return getCard().lookupPrice( getCurrentInUpgradeOrder() ) * roll;


//            int utilitiesOwned = 0;
//            for(AbstractPropertyTile property: this.getOwner().getProperties()){
//                if(property instanceof AbstractUtilityTile){
//                    utilitiesOwned += 1;
//                }
//            }
//
//            // TODO ==================================================
//            // TODO: REPLACE WITH USING TURN AS ROLL NOT GAME
//            // TODO ==================================================

//            // TODO ==================================================


        }
    }
}

