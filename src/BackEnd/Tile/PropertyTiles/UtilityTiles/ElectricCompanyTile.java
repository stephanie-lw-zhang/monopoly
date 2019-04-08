package BackEnd.Tile.PropertyTiles.UtilityTiles;


import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;
import BackEnd.Tile.PropertyTiles.AbstractPropertyTile;

public class ElectricCompanyTile extends AbstractUtilityTile {


    public ElectricCompanyTile(Bank bank, AbstractCard card, String tiletype, double tileprice) {
        super(bank, card, tiletype, tileprice);
    }

    @Override
    public double sellToBankPrice() {
        return getTileprice()/2;
        //assumption.
    }

}
