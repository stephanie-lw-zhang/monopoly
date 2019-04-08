package BackEnd.Tile.PropertyTiles.UtilityTiles;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;
import BackEnd.Tile.PropertyTiles.AbstractPropertyTile;

public abstract class AbstractUtilityTile extends AbstractPropertyTile {
    private Double rentMultiplierOwnSingle = 4.0;
    private Double rentMultiplierOwnDouble = 10.0;


    public AbstractUtilityTile(Bank bank, AbstractCard card, String tiletype, double tileprice) {
        super( bank, card, tiletype, tileprice );
    }

    @Override
    public abstract double sellToBankPrice();

    @Override
    public void applyLandedOnAction(AbstractPlayer p) {
        int utilitiesOwned = 0;
        for(AbstractPropertyTile property: this.getOwner().getProperties()){
            if(property instanceof AbstractUtilityTile){
                utilitiesOwned += 1;
            }
        }
        if(utilitiesOwned == 1){
            p.paysTo( this.getOwner(), rentMultiplierOwnSingle * p.getRoll() );
        } else {
            p.paysTo( this.getOwner(), rentMultiplierOwnDouble * p.getRoll() );
        }
    }
}
