package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

public abstract class Tile {

//    public enum Actions {
//        BUY ("buy"),
//        AUCTION("auction"),
//        PAY_RENT("payRent"),
//        PAY_TAX_FULL("payTaxFull"),
//        PAY_TAX_PERCENTAGE("payTaxPercentage"),
//        DRAW_CARD("drawCard"),
//        SELL("sell"),
//        GO_TO_JAIL("goToJail");
//
//        private String methodName;
//
//        Actions(String actionMethod){
//            methodName = actionMethod;
//        }
//    }

//    void applyLandedOnAction(AbstractPlayer p);
    public abstract List<String> applyLandedOnAction(AbstractPlayer p);

    public abstract void applyPassedAction(AbstractPlayer p);

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

}
