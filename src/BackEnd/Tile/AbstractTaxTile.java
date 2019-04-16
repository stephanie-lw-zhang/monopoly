package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Tile.TileInterface;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class AbstractTaxTile implements TileInterface {

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

    @Override
    public void applyPassedAction(AbstractPlayer p) {
        return;
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
