package backend.card.action_cards;

import backend.assetholder.AbstractAssetHolder;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Card where you pay a certain amount for each building type you have, extends ActionCard
 * @author stephaniezhang
 */

public class PayBuildingsCard extends ActionCard {
    private List<AbstractAssetHolder> payers;
    private List<AbstractAssetHolder> payees;
    private String payerString;
    private String payeeString;
    private Map<String, Double> baseToMultiplier;

    /**
     * Constructor
     * @param payers -- list of asset holders that pay
     * @param payees -- list of asset holders that receive money from each payer
     * @param baseToMultiplier -- map where the key is the building type ("base"), and value is the how much you have to pay for each of those buildings ("multiplier")
     * @param type -- action type, used in super
     * @param text -- message, used in super
     */
    public PayBuildingsCard(List<AbstractAssetHolder> payers, List<AbstractAssetHolder> payees, Map<String, Double> baseToMultiplier, String type, String text) {
        super(type, text);
        this.payers =  payers;
        this.payees = payees;
        this.baseToMultiplier = baseToMultiplier;
    }

    /**
     * constructor for xml purposes
     * @param n
     */
    public PayBuildingsCard(Element n){
        super("", "");
        baseToMultiplier = new HashMap<>();
        constructBaseToMultiplier(n.getElementsByTagName("Multiplier"));
        payerString = getTagValue("Payer", n);
        payeeString = getTagValue("Payee", n);
        setType(getTagValue("Type", n));
        setText(getTagValue("Message", n));
    }

    /**
     *
     * @return payers, payees, and map of base to multiplier
     */
    @Override
    public List<Object> getParameters() {
        List<Object> parameters = new ArrayList<Object>();
        parameters.add( payers );
        parameters.add( payees );
        parameters.add( baseToMultiplier );
        return parameters;
    }

    /**
     * construct the map for base to multiplier
     * @param nodeList
     */
    public void constructBaseToMultiplier(NodeList nodeList){
        for(int i = 0; i < nodeList.getLength(); i++){
            Element element = (Element) nodeList.item(i);
            baseToMultiplier.put(getTagValue("Property", element), Double.parseDouble(getTagValue("Amount", element)));
        }
    }

    /**
     *
     * @return payer as string
     */
    public String getPayerString(){
        return payerString;
    }

    /**
     *
     * @return payee as string
     */
    public String getPayeeString(){
        return payeeString;
    }

    /**
     * Sets the payers to p
     * @param p
     */
    public void setPayers(List<AbstractAssetHolder> p){
        payers = p;
    }

    /**
     * sets the payees to p
     * @param p
     */
    public void setPayees(List<AbstractAssetHolder> p){
        payees = p;
    }

}
