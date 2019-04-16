package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.Bank;
import org.w3c.dom.Element;

public class UtilityTile extends AbstractNonBuildingPropertyTile {


    public UtilityTile(Bank bank, PropertyCard card, String tiletype, double tileprice) {
        super( bank, card, tiletype, tileprice );
    }

    public UtilityTile(Element n) {
        super( n );
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

