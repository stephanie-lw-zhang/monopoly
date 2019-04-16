package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import org.w3c.dom.Element;

public class FreeParkingTile implements TileInterface {

    //private double landedOnMoney;

    //might need to calculate money?
    public FreeParkingTile(double landedOnMoney){
        //this.landedOnMoney = landedOnMoney;
    }

    public FreeParkingTile(Element n){}

    @Override
    public void applyLandedOnAction(AbstractPlayer player) {
        //player.setMoney(player.getMoney() + landedOnMoney);
        return;
    }

    @Override
    public void applyPassedAction(AbstractPlayer player) {
        return;
    }

}
