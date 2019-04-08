package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;

public class RailroadTile extends AbstractPropertyTile {

    public RailroadTile(Bank bank, AbstractCard card, String tiletype, double tileprice) {
        super(bank, card, tiletype, tileprice);
    }

    @Override
    public void applyLandedOnAction(AbstractPlayer player) {

    }

    @Override
    public void applyPassedAction(AbstractPlayer player) {

    }

    @Override
    public double sellToBankPrice() {
        return 0;
    }
}
