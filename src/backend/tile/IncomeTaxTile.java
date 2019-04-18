package backend.tile;

import backend.assetholder.AbstractPlayer;
import backend.assetholder.Bank;
import controller.Actions;

import java.util.ArrayList;
import java.util.List;

public class IncomeTaxTile extends AbstractTaxTile {

    public IncomeTaxTile(int money, Bank bank) {
        super(money, bank);
    }

    @Override
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        //interaction with front-end: pay full or 10%?
        List<String> possibleActions = new ArrayList<>(  );
        possibleActions.add("payTaxFixed");
        possibleActions.add("payTaxPercentage");
//        if (true) {
//            player.paysTo(getBank(),getAmountToDeduct());
//        }
//        else {
//            player.paysTo(getBank(), player.getMoney() * 0.1);
//        }
        return possibleActions;
    }
}
