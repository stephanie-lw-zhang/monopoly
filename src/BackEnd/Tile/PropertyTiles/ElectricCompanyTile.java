package BackEnd.Tile.PropertyTiles;


import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;

public class ElectricCompanyTile extends AbstractPropertyTile {


    public ElectricCompanyTile(Bank bank, AbstractCard card, String tiletype, double tileprice) {
        super(bank, card, tiletype, tileprice);
    }

    @Override
    public double sellToBankPrice() {
        return 0;
    }

    @Override
    public void applyLandedOnAction(AbstractPlayer p) {

    }
}
