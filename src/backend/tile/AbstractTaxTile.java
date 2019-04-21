package backend.tile;

import backend.assetholder.Bank;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class AbstractTaxTile extends Tile {

    private double amountToDeduct;
    private Bank bank;
    private int index;

    public AbstractTaxTile(int money, Bank bank, int index) {
        this.amountToDeduct = money;
        this.bank = bank;
        this.index = index;
    }

    public AbstractTaxTile(Bank bank, Element n){
        this.bank = bank;
        this.amountToDeduct = Integer.parseInt(getTagValue("TileRent", n));
        setTileIndex(Integer.parseInt(getTagValue("TileNumber", n)));
        //this.bank = ;
    }

    public Bank getBank() {
        return bank;
    }

    public double getAmountToDeduct() {
        return amountToDeduct;
    }

}
