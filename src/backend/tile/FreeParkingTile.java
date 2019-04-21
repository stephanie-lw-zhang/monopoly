package backend.tile;

import backend.assetholder.AbstractPlayer;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class FreeParkingTile extends Tile {

    //private double landedOnMoney;
    private int index;
    //might need to calculate money?
    public FreeParkingTile(double landedOnMoney, int index){
        //this.landedOnMoney = landedOnMoney;
        this.index = index;
    }

    public FreeParkingTile(Element n){
        setTileIndex(Integer.parseInt(getTagValue("TileNumber", n)));
    }


    public List<String> applyLandedOnAction(AbstractPlayer player) {
        //player.setMoney(player.getMoney() + landedOnMoney);
        List<String> possibleActions = new ArrayList<>(  );
        possibleActions.add("collectMoney");
        return possibleActions;
    }

}
