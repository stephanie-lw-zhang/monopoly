package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
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

    public AbstractTaxTile(Bank bank, Element n){
        this.bank = bank;
        this.amountToDeduct = Integer.parseInt(getTagValue("TileRent", n));
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

}
