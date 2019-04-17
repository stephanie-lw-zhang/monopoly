package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Tile.AbstractTaxTile;
import org.w3c.dom.Element;

public class IncomeTaxTile extends AbstractTaxTile {

    public IncomeTaxTile(int money, Bank bank, int index) {
        super(money, bank, index);
    }

    public IncomeTaxTile(Bank bank, Element n){
        super(bank,n);
    }

    @Override
    public void applyLandedOnAction(AbstractPlayer player) {
        //interaction with front-end: pay full or 10%?
        if (true) {
            player.paysTo(getBank(),getAmountToDeduct());
        }
        else {
            player.paysTo(getBank(), player.getMoney() * 0.1);
        }
    }
}
