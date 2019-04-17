package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class FreeParkingTile extends Tile {

    //private double landedOnMoney;

    //might need to calculate money?
    public FreeParkingTile(double landedOnMoney){
        //this.landedOnMoney = landedOnMoney;
    }

    public FreeParkingTile(Element n){}


    public List<String> applyLandedOnAction(AbstractPlayer player) {
        //player.setMoney(player.getMoney() + landedOnMoney);
        List<String> possibleActions = new ArrayList<String>(  );
        possibleActions.add("Collect Money");
        return possibleActions;
    }

    public void applyPassedAction(AbstractPlayer player) {
        return;
    }

}
