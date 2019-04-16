package BackEnd.Tile.PropertyTiles.UtilityTiles;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;
import BackEnd.Tile.PropertyTiles.AbstractPropertyTile;
import org.w3c.dom.Element;

public class WaterWorksTile extends AbstractUtilityTile {
    //maybe abstract utilities? so no duplicate code on applylandedonaction

    public WaterWorksTile(Bank bank, AbstractCard card, String tiletype, double tileprice) {
        super(bank, card, tiletype, tileprice);
    }

    public WaterWorksTile(Element n){
        super(n);
    }

}
