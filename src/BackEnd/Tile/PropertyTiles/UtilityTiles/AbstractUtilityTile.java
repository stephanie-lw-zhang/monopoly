package BackEnd.Tile.PropertyTiles.UtilityTiles;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;
import BackEnd.Controller.Game;
import BackEnd.Tile.PropertyTiles.AbstractPropertyTile;

public abstract class AbstractUtilityTile extends AbstractPropertyTile {
    private Double rentMultiplierOwnSingle = 4.0;
    private Double rentMultiplierOwnDouble = 10.0;
    private int roll;


    public AbstractUtilityTile(Bank bank, AbstractCard card, String tiletype, double tileprice) {
        super( bank, card, tiletype, tileprice );
    }

    public void setRoll(AbstractPlayer p) {
        roll = p.getRoll();
    }

    private int getRoll(){
        return roll;
    }

    @Override
    public double calculateRentPrice(Game game) {
        int utilitiesOwned = 0;
        for(AbstractPropertyTile property: this.getOwner().getProperties()){
            if(property instanceof AbstractUtilityTile){
                utilitiesOwned += 1;
            }
        }
        if(utilitiesOwned == 1){
            return rentMultiplierOwnSingle * getRoll();
        } else {
            return rentMultiplierOwnDouble * getRoll();
        }
    }
}
