package BackEnd.Tile.PropertyTiles.UtilityTiles;


import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;
import BackEnd.Tile.PropertyTiles.AbstractPropertyTile;
import org.w3c.dom.Element;

public class ElectricCompanyTile extends AbstractUtilityTile {

    public ElectricCompanyTile(Bank bank, AbstractCard card, String tiletype, double tileprice) {
        super(bank, card, tiletype, tileprice);
    }

    public ElectricCompanyTile(Element n){
        super(n);
    }

}
