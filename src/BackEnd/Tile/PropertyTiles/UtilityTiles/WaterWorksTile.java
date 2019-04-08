package BackEnd.Tile.PropertyTiles.UtilityTiles;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;
import BackEnd.Tile.PropertyTiles.AbstractPropertyTile;

public class WaterWorksTile extends AbstractUtilityTile {
    //maybe abstract utilities? so no duplicate code on applylandedonaction

    public WaterWorksTile(Bank bank, AbstractCard card, String tiletype, double tileprice) {
        super(bank, card, tiletype, tileprice);
    }

    @Override
    public double sellToBankPrice() {
        return 0;
    }


}
