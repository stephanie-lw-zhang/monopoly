package backend.card.action_cards;

import backend.assetholder.AbstractAssetHolder;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for actions cards the only have money transactions, extends ActionCard
 * @Author Stephanie Zhang
 */
public class PayCard extends ActionCard {
    private List<AbstractAssetHolder> payers;
    private List<AbstractAssetHolder> payees;
    private String payerString;
    private String payeeString;
    private double amount;

    /**
     * Constructor
     * @param payers -- list of assetholders that pay
     * @param payees -- list of assetholders that receive money from each payer
     * @param amount -- amount paid from each payer to each payee
     * @param type -- action type, used in super
     * @param text -- message, used in super
     */
    public PayCard(List<AbstractAssetHolder> payers, List<AbstractAssetHolder> payees, double amount, String type, String text) {
        super(type,text );
        this.payers =  payers;
        this.payees = payees;
        this.amount = amount;
    }

    /**
     * constructor for xml purposes
     * @param n
     */
    public PayCard(Element n){
        super("", "");
        payerString = getTagValue("Payer", n);
        payeeString = getTagValue("Payee", n);
        amount = Double.parseDouble(getTagValue("Amount", n));
        setText(getTagValue("Message", n));
        setType(getTagValue("Type", n));
    }

    /**
     *
     * @return payers, payees, and amount to be paid
     */
    @Override
    public List<Object> getParameters() {
        List<Object> parameters = new ArrayList<>();
        parameters.add( payers );
        parameters.add( payees );
        parameters.add( amount );
        return parameters;
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
