package backend.tile;

import backend.assetholder.AbstractPlayer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

public abstract class Tile {

    private int index;

    public abstract List<String> applyLandedOnAction(AbstractPlayer p);

    public String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    public int getTileIndex(){
        return index;
    }

    public void setTileIndex(int i){
        index = i;
    }

    public boolean individualUpdateEvenCheck(boolean upgrade, int thresholdForUpdate, BuildingTile tile){
        return true;
    };

    public boolean isJailTile(){
        return false;
    }

    public boolean isGoToJailTile(){
        return true;
    }

}
