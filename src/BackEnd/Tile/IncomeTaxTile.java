package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;

import java.util.ArrayList;
import java.util.List;

public class IncomeTaxTile extends AbstractTaxTile {

    public IncomeTaxTile(int money, Bank bank) {
        super(money, bank);
    }

    @Override
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        //interaction with front-end: pay full or 10%?
        List<String> possibleActions = new ArrayList<String>(  );
        possibleActions.add( "Pay Full");
        possibleActions.add( "Pay Percentage" );
//        if (true) {
//            player.paysTo(getBank(),getAmountToDeduct());
//        }
//        else {
//            player.paysTo(getBank(), player.getMoney() * 0.1);
//        }
        return possibleActions;
    }
}
