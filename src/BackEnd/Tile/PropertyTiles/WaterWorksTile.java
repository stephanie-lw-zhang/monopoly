package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;

public class WaterWorksTile extends AbstractPropertyTile {
    //maybe abstract utilities? so no duplicate code on applylandedonaction

    public WaterWorksTile(Bank bank, AbstractCard card, String tiletype, double tileprice) {
        super(bank, card, tiletype, tileprice);
    }

    @Override
    public void applyLandedOnAction(AbstractPlayer p) {

    }
}
