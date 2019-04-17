package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Tile.AbstractTaxTile;

import java.util.ArrayList;
import java.util.List;

public class LuxuryTaxTile extends AbstractTaxTile {

    public LuxuryTaxTile(int money, Bank bank) {
        super(money, bank);
    }

    @Override
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        List<String> possibleActions = new ArrayList<String>(  );
        possibleActions.add( "pay tax");
//        player.paysTo(getBank(),getAmountToDeduct());

        return possibleActions;
    }

}
