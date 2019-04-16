package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GoTile implements TileInterface {

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

    @Override
    public void applyLandedOnAction(AbstractPlayer player) {
        player.setMoney(player.getMoney() + landedOnMoney);
    }

    @Override
    public void applyPassedAction(AbstractPlayer player) {
        player.setMoney(player.getMoney() + passedMoney);
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}
