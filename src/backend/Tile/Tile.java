package backend.Tile;

import backend.AssetHolder.AbstractPlayer;
import controller.Actions;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

public abstract class Tile {

    public abstract List<Actions> applyLandedOnAction(AbstractPlayer p);

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

}
