package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import api.Monopoly.BackEnd.AbstractTile;
import org.w3c.dom.Element;

public class FreeParkingTile extends Tile {

    //private double landedOnMoney;
    private int index;
    //might need to calculate money?
    public FreeParkingTile(double landedOnMoney, int index){
        //this.landedOnMoney = landedOnMoney;
        this.index = index;
    }

    public FreeParkingTile(Element n){
        index = Integer.parseInt(getTagValue("TileNumber", n));
    }


    public void applyLandedOnAction(AbstractPlayer player) {
        //player.setMoney(player.getMoney() + landedOnMoney);
        return;
    }

    public void applyPassedAction(AbstractPlayer player) {
        return;
    }

}
