package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
<<<<<<< HEAD
=======
//import api.Monopoly.BackEnd.AbstractTile;
>>>>>>> 0d3378c11f6e47b4fccb9f2af6e064ee1f79ebf2
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
