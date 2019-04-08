package BackEnd.Tile.TaxTiles;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;

public class IncomeTaxTile extends AbstractTaxTile {

    public IncomeTaxTile(int money, Bank bank) {
        super(money, bank);
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
