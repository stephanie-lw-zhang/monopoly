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
<<<<<<< HEAD
<<<<<<< HEAD
//      player.paysFullAmountTo(getBank(),getAmountToDeduct());
=======
//      player.payFullAmountTo(getBank(),getAmountToDeduct());
>>>>>>> 14f2f2b37ae82b8302118ba2ecba0554310e8564
=======
//      player.payFullAmountTo(getBank(),getAmountToDeduct());
>>>>>>> 9a9c68a39301a66f4dfefd6560b6893148453277
        return possibleActions;
    }

}
