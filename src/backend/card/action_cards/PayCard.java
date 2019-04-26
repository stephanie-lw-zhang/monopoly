package backend.card.action_cards;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.assetholder.AbstractAssetHolder;

import java.util.ArrayList;
import java.util.List;

public class PayCard extends ActionCard {
    private List<AbstractAssetHolder> payers;
    private List<AbstractAssetHolder> payees;
    private double amount;

    public PayCard(List<AbstractAssetHolder> payers, List<AbstractAssetHolder> payees, double amount, String type) {
        super(type);
        this.payers =  payers;
        this.payees = payees;
        this.amount = amount;
    }


    @Override
    public List<Object> getParameters() {
        List<Object> parameters = new ArrayList<Object>();
        parameters.add( payers );
        parameters.add( payees );
        parameters.add( amount );
        return parameters;
    }
}
