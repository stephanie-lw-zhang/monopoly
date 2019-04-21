package backend.tile;

import backend.assetholder.AbstractPlayer;
import backend.assetholder.Bank;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class IncomeTaxTile extends AbstractTaxTile {

    public IncomeTaxTile(int money, Bank bank, int index) {
        super(money, bank, index);
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
<<<<<<< HEAD
<<<<<<< HEAD
//            player.paysFullAmountTo(getBank(),getAmountToDeduct());
//        }
//        else {
//            player.paysFullAmountTo(getBank(), player.getMoney() * 0.1);
=======
=======
>>>>>>> 9a9c68a39301a66f4dfefd6560b6893148453277
//            player.payFullAmountTo(getBank(),getAmountToDeduct());
//        }
//        else {
//            player.payFullAmountTo(getBank(), player.getMoney() * 0.1);
<<<<<<< HEAD
>>>>>>> 14f2f2b37ae82b8302118ba2ecba0554310e8564
=======
>>>>>>> 9a9c68a39301a66f4dfefd6560b6893148453277
//        }
        return possibleActions;
    }
}
