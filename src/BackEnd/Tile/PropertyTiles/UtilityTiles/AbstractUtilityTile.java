package BackEnd.Tile.PropertyTiles.UtilityTiles;

import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;
import Controller.Game;
import BackEnd.Tile.PropertyTiles.AbstractPropertyTile;
import org.w3c.dom.Element;

public abstract class AbstractUtilityTile extends AbstractPropertyTile {
    private Double rentMultiplierOwnSingle = 4.0;
    private Double rentMultiplierOwnDouble = 10.0;
//    private int roll;


    public AbstractUtilityTile(Bank bank, AbstractCard card, String tiletype, double tileprice) {
        super( bank, card, tiletype, tileprice );
    }

    public AbstractUtilityTile(Element n){
        super(n);
    }

//    public void setRoll(AbstractPlayer p) {
//        roll = p.getRoll();
//    }
//
//    private int getRoll(){
//        return roll;
//    }

    @Override
    public double calculateRentPrice(Game game) {
        if (isMortgaged()) {
            return 0;
        }
        else {
            int utilitiesOwned = 0;
            for(AbstractPropertyTile property: this.getOwner().getProperties()){
                if(property instanceof AbstractUtilityTile){
                    utilitiesOwned += 1;
                }
            }

            // TODO ==================================================
            // TODO: REPLACE WITH USING TURN AS ROLL NOT GAME
            // TODO ==================================================
            int[] diceTotal = game.rollValue();
            int diceSum = 0;
            for (int i = 0; i < diceTotal.length; i++) {
                diceSum += diceTotal[i];
            }
            // TODO ==================================================

            if(utilitiesOwned == 1){

                return rentMultiplierOwnSingle * diceSum;
            } else {
                return rentMultiplierOwnDouble * diceSum;
            }
        }
    }
}
