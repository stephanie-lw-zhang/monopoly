package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import org.w3c.dom.Element;

public class GoTile extends Tile {

    private double landedOnMoney;
    private double passedMoney;
    private int index;

    public GoTile(double landedOnMoney, double passedMoney, int index) {
        this.landedOnMoney = landedOnMoney;
        this.passedMoney = passedMoney;
        this.index = index;
    }

    public GoTile(Element n){
        this.landedOnMoney = Integer.parseInt(getTagValue("LandedMoney", n));
        this.passedMoney = Integer.parseInt(getTagValue("PassedMoney", n));
        index = Integer.parseInt(getTagValue("TileNumber", n));
    }

    public double getLandedOnMoney() {
        return landedOnMoney;
    }


    public void applyLandedOnAction(AbstractPlayer player) {
        player.setMoney(player.getMoney() + landedOnMoney);
    }


    public void applyPassedAction(AbstractPlayer player) {
        player.setMoney(player.getMoney() + passedMoney);
    }
}
