package backend.tile;

import backend.assetholder.AbstractPlayer;
import backend.assetholder.Bank;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class LuxuryTaxTile extends AbstractTaxTile {

    public LuxuryTaxTile(int money, Bank bank, int index) {
        super(money, bank, index);
    }

    public LuxuryTaxTile(Bank bank, Element n){
        super(bank, n);
    }

    @Override
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        List<String> possibleActions = new ArrayList<>(  );
        possibleActions.add("payTaxFixed");
//      player.payFullAmountTo(getBank(),getAmountToDeduct());
        return possibleActions;
    }

}
