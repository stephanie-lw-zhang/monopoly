package backend.card.action_cards;

import backend.assetholder.AbstractAssetHolder;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PayBuildingsCard extends ActionCard {
    private List<AbstractAssetHolder> payers;
    private List<AbstractAssetHolder> payees;
    private Map<String, Double> baseToMultiplier;

    public PayBuildingsCard(List<AbstractAssetHolder> payers, List<AbstractAssetHolder> payees, Map<String, Double> baseToMultiplier, String type, String text) {
        super(type, text);
        this.payers =  payers;
        this.payees = payees;
        this.baseToMultiplier = baseToMultiplier;
    }
    
    public PayBuildingsCard(Element n){
        super("", "");
        this.setType(getTagValue("Type", n));
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
