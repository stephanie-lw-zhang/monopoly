package backend.card.action_cards;

import backend.assetholder.AbstractAssetHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PayBuildingsCard extends ActionCard {
    private List<AbstractAssetHolder> payers;
    private List<AbstractAssetHolder> payees;
    private Map<String, Double> baseToMultiplier;

    public PayBuildingsCard(List<AbstractAssetHolder> payers, List<AbstractAssetHolder> payees, Map<String, Double> baseToMultiplier, String type) {
        super(type);
        this.payers =  payers;
        this.payees = payees;
        this.baseToMultiplier = baseToMultiplier;
    }

    @Override
    public List<Object> getParameters() {
        List<Object> parameters = new ArrayList<Object>();
        parameters.add( payers );
        parameters.add( payees );
        parameters.add( baseToMultiplier );
        return parameters;
    }
}
