package backend.tile;

import backend.assetholder.AbstractPlayer;

import controller.Actions;
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
        List<String> possibleActions = new ArrayList<>(  );
        possibleActions.add("collectMoney");
        return possibleActions;
    }

}
