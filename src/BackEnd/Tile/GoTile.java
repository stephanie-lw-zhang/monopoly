package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;

public class GoTile implements TileInterface {

    private double landedOnMoney;
    private double passedMoney;

    public GoTile(double landedOnMoney, double passedMoney) {
        this.landedOnMoney = landedOnMoney;
        this.passedMoney = passedMoney;
    }

    @Override
    public void applyLandedOnAction(AbstractPlayer player) {
        player.setMoney(player.getMoney() + landedOnMoney);
    }

    @Override
    public void applyPassedAction(AbstractPlayer player) {
        player.setMoney(player.getMoney() + passedMoney);
    }
}
