package backend.card;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.assetholder.AbstractAssetHolder;

import java.util.List;

public class PayCard extends ApplicationCard {
    private List<AbstractAssetHolder> payers;
    private List<AbstractAssetHolder> payees;
    private double amount;

    public PayCard(AbstractBoard board, List<AbstractAssetHolder> payers, List<AbstractAssetHolder> payees, double amount) {
        super( board );
        this.payers =  payers;
        this.payees = payees;
        this.amount = amount;
    }

    @Override
    public void applyTo(AbstractPlayer player) {
        for(AbstractAssetHolder payer: payers){
            for(AbstractAssetHolder payee: payees){
<<<<<<< HEAD
                payer.paysFullAmountTo( payee, amount );
=======
                payer.payFullAmountTo( payee, amount );
>>>>>>> 14f2f2b37ae82b8302118ba2ecba0554310e8564
            }
        }
    }
}
