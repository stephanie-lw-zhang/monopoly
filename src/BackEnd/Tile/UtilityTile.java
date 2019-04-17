package BackEnd.Tile;

import BackEnd.AssetHolder.Bank;
import BackEnd.Card.PropertyCard;
import BackEnd.Tile.AbstractNonBuildingPropertyTile;
import org.w3c.dom.Element;

public class UtilityTile extends AbstractNonBuildingPropertyTile {


    public UtilityTile(Bank bank, PropertyCard card, String tiletype, double tileprice, int index) {
        super( bank, card, tiletype, tileprice, index);
    }

    public UtilityTile(Bank bank, PropertyCard card, Element n){
        super(bank, card, n);
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

