package BackEnd.Tile.TaxTiles;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Tile.TileInterface;

public abstract class AbstractTaxTile implements TileInterface {

    private double amountToDeduct;
    private Bank bank;

    public AbstractTaxTile(int money, Bank bank) {
        this.amountToDeduct = money;
        this.bank = bank;
    }

//    @Override
//    public void applyPassedAction(AbstractPlayer p) {
//        return;
//    }

    public Bank getBank() {
        return bank;
    }

    public double getAmountToDeduct() {
        return amountToDeduct;
    }
}
