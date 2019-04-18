package backend.Tile;

import backend.AssetHolder.Bank;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class AbstractTaxTile extends Tile {

    private double amountToDeduct;
    private Bank bank;

    public AbstractTaxTile(int money, Bank bank) {
        this.amountToDeduct = money;
        this.bank = bank;
    }

    public AbstractTaxTile(Element n){
        this.amountToDeduct = Integer.parseInt(getTagValue("LandedMoney", n));
        //this.bank = ;
    }

    public Bank getBank() {
        return bank;
    }

    public double getAmountToDeduct() {
        return amountToDeduct;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

}
