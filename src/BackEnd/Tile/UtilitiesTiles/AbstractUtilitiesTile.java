package BackEnd.Tile.UtilitiesTiles;

import BackEnd.Player.AbstractPlayer;
import BackEnd.Tile.TileInterface;

public abstract class AbstractUtilitiesTile implements TileInterface {

    private double amountToDeduct;

    public AbstractUtilitiesTile(int money) {
        this.amountToDeduct = money;
    }

    @Override
    public void applyLandedOnAction(AbstractPlayer player) {
        player.setMoney(player.getMoney() - amountToDeduct);
    }

    @Override
    public void applyPassedAction(AbstractPlayer p) {
        return;
    }
}
