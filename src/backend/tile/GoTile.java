package backend.tile;

import backend.assetholder.AbstractPlayer;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

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
        setTileIndex(Integer.parseInt(getTagValue("TileNumber", n)));
    }

    public double getLandedOnMoney() {
        return landedOnMoney;
    }


    public List<String> applyLandedOnAction(AbstractPlayer player) {
        List<String> possibleActions = new ArrayList<>( );
//        player.setMoney(player.getMoney() + landedOnMoney);
        possibleActions.add("COLLECT MONEY");
        return possibleActions;
    }

    public List<String> applyPassedAction(AbstractPlayer player) {
        List<String> possibleActions = new ArrayList<>();
        possibleActions.add("COLLECT MONEY");
        return possibleActions;
    }

//    public void applyPassedAction(AbstractPlayer player) {
//        player.setMoney(player.getMoney() + passedMoney);
//    }

    //private String getTagValue(String tag, Element element) {
    //    NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
    //    Node node = nodeList.item(0);
    //    return node.getNodeValue();
    //}
}
