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
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        List<String> possibleActions = new ArrayList<>(  );
        possibleActions.add("payTaxFixed");
//        player.paysTo(getBank(),getAmountToDeduct());
        return possibleActions;
    }

}
