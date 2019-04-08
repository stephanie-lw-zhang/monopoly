package BackEnd.Tile.TaxTiles;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;

public class LuxuryTaxTile extends AbstractTaxTile {

    public LuxuryTaxTile(int money, Bank bank) {
        super(money, bank);
    }

    @Override
    public void applyLandedOnAction(AbstractPlayer player) {
        player.paysTo(getBank(),getAmountToDeduct());
    }
}
