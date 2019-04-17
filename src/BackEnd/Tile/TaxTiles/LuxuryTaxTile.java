package BackEnd.Tile.TaxTiles;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;

import java.util.ArrayList;
import java.util.List;

public class LuxuryTaxTile extends AbstractTaxTile {

    public LuxuryTaxTile(int money, Bank bank) {
        super(money, bank);
    }

    @Override
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        //interaction with front-end: pay full or 10%?
        List<String> possibleActions = new ArrayList<String>(  );
        possibleActions.add( "pay tax");
//        player.paysTo(getBank(),getAmountToDeduct());

        return possibleActions;
    }

}
