package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class Tile {

//    void applyLandedOnAction(AbstractPlayer p);
    public void applyLandedOnAction(AbstractPlayer p) {
        System.out.println("Player " + p.getMyPlayerName() + " landed on ");
    }

    public abstract void applyPassedAction(AbstractPlayer p);

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

}
