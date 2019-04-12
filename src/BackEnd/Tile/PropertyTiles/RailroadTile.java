package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;
import BackEnd.Card.RailroadCard;
import Controller.Game;

public class RailroadTile extends AbstractPropertyTile {

    private RailroadCard card;
    private String currentInUpgradeOrder;


    public RailroadTile(Bank bank, AbstractCard card, String tiletype, double tileprice) {
        super(bank, card, tiletype, tileprice);
        this.card = (RailroadCard)card;
        currentInUpgradeOrder = this.card.getUpgradeOrderAtIndex(0);
    }

    public double calculateRentPrice(Game game) {
        if (isMortgaged()) {
            return 0;
        }
        else {
            return card.lookupPrice(currentInUpgradeOrder);

//            int railroadsOwned = 0;
//            for(AbstractPropertyTile property: this.getOwner().getProperties()){
//                if(property instanceof RailroadTile){
//                    railroadsOwned += 1;
//                }
//            }
//            if (railroadsOwned == 1) {
//                return card.();
//            }
//            else if (railroadsOwned == 2) {
//                return card.getRailroadRent2Owned();
//            }
//            else if (railroadsOwned == 3) {
//                return card.getRailroadRent3Owned();
//            }
//            else if (railroadsOwned == 4) {
//                return card.getRailroadrent4Owned();
//            }
//            else {
//                return 0;
//            }

        }
    }
}
