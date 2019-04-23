package backend.tile;

import backend.assetholder.Bank;
import backend.card.BuildingCard;
import backend.card.PropertyCard;
import org.w3c.dom.Element;

public class UtilityTile extends AbstractNonBuildingPropertyTile {


    public UtilityTile(Bank bank, PropertyCard card, String tiletype, int index) {
        super( bank, card, tiletype, index);
    }

    public UtilityTile(Bank bank, Element n){
        super(bank, n);
        setCard( new PropertyCard(n.getElementsByTagName("Card").item(0)) );
    }

    //public UtilityTile(Element n) {
    //    super( n );
    //}

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

