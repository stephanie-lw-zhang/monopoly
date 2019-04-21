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

                payer.payFullAmountTo( payee, amount );

            }
        }
    }
}
