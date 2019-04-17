package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Tile.AbstractTaxTile;
import org.w3c.dom.Element;

public class LuxuryTaxTile extends AbstractTaxTile {

    public LuxuryTaxTile(int money, Bank bank, int index) {
        super(money, bank, index);
    }

    public LuxuryTaxTile(Bank bank, Element n){
        super(bank, n);
    }

    @Override
    public void applyLandedOnAction(AbstractPlayer player) {
        player.paysTo(getBank(),getAmountToDeduct());
    }
}
