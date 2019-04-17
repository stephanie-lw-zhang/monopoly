package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import Controller.Actions;

import java.util.ArrayList;
import java.util.List;

public class IncomeTaxTile extends AbstractTaxTile {

    public IncomeTaxTile(int money, Bank bank) {
        super(money, bank);
    }

    @Override
    public List<Actions> applyLandedOnAction(AbstractPlayer player) {
        //interaction with front-end: pay full or 10%?
        List<Actions> possibleActions = new ArrayList<>(  );
        possibleActions.add(Actions.PAY_TAX_FULL);
        possibleActions.add(Actions.PAY_TAX_PERCENTAGE);
//        if (true) {
//            player.paysTo(getBank(),getAmountToDeduct());
//        }
//        else {
//            player.paysTo(getBank(), player.getMoney() * 0.1);
//        }
        return possibleActions;
    }
}
