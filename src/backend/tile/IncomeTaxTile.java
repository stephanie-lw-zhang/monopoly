package backend.tile;

import backend.assetholder.AbstractPlayer;
import backend.assetholder.Bank;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class IncomeTaxTile extends AbstractTaxTile {

    public IncomeTaxTile(int money, Bank bank, String tileType, int index) {
        super(money, bank, tileType, index);
    }

    public IncomeTaxTile(Bank bank, Element n){
        super(bank, n);
    }

    @Override
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        //interaction with front-end: pay full or 10%?
        List<String> possibleActions = new ArrayList<>(  );
        possibleActions.add("payTaxFixed");
        possibleActions.add("payTaxPercentage");
//        if (true) {
//            player.payFullAmountTo(getBank(),getAmountToDeduct());
//        }
//        else {
//            player.payFullAmountTo(getBank(), player.getMoney() * 0.1);
//        }
        return possibleActions;
    }
}
