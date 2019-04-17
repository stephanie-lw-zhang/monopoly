package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import Controller.Actions;

import java.util.ArrayList;
import java.util.List;

public class LuxuryTaxTile extends AbstractTaxTile {

    public LuxuryTaxTile(int money, Bank bank) {
        super(money, bank);
    }

    @Override
    public List<Actions> applyLandedOnAction(AbstractPlayer player) {
        List<Actions> possibleActions = new ArrayList<>(  );
        possibleActions.add(Actions.PAY_TAX_FULL);
//        player.paysTo(getBank(),getAmountToDeduct());
        return possibleActions;
    }

}
