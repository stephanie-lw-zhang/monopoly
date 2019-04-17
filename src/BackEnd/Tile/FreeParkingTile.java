package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
//import api.Monopoly.BackEnd.AbstractTile;
import org.w3c.dom.Element;

public class FreeParkingTile extends Tile {

    //private double landedOnMoney;

    //might need to calculate money?
    public FreeParkingTile(double landedOnMoney){
        //this.landedOnMoney = landedOnMoney;
    }

    public FreeParkingTile(Element n){}


    public void applyLandedOnAction(AbstractPlayer player) {
        //player.setMoney(player.getMoney() + landedOnMoney);
        return;
    }

    public void applyPassedAction(AbstractPlayer player) {
        return;
    }

}
