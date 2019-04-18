package backend.tile;

import backend.assetholder.AbstractPlayer;
import backend.assetholder.Bank;
import controller.Actions;

import java.util.ArrayList;
import java.util.List;

public class LuxuryTaxTile extends AbstractTaxTile {

    public LuxuryTaxTile(int money, Bank bank) {
        super(money, bank);
    }

    @Override
    public List<Actions> applyLandedOnAction(AbstractPlayer player) {
        List<Actions> possibleActions = new ArrayList<>(  );
        possibleActions.add(Actions.PAY_TAX_FIXED );
//        player.paysTo(getBank(),getAmountToDeduct());
        return possibleActions;
    }

}
