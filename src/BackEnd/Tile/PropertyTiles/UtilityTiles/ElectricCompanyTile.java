package BackEnd.Tile.PropertyTiles.UtilityTiles;


import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;
import BackEnd.Card.PropertyCard;
import BackEnd.Tile.PropertyTiles.AbstractPropertyTile;
import org.w3c.dom.Element;

public class ElectricCompanyTile extends AbstractUtilityTile {

    public ElectricCompanyTile(Bank bank, PropertyCard card, String tiletype, double tileprice) {
        super(bank, card, tiletype, tileprice);
    }

    public ElectricCompanyTile(Element n){
        super(n);
    }

}
