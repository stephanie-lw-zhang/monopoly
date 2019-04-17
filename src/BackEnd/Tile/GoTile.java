package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GoTile extends Tile {

    private double landedOnMoney;
    private double passedMoney;

    public GoTile(double landedOnMoney, double passedMoney) {
        this.landedOnMoney = landedOnMoney;
        this.passedMoney = passedMoney;
    }

    public GoTile(Element n){
        this.landedOnMoney = Integer.parseInt(getTagValue("LandedMoney", n));
        this.passedMoney = Integer.parseInt(getTagValue("PassedMoney", n));
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

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}
