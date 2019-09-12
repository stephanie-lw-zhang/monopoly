package backend.card.action_cards;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for holdable cards (such as get out of jaile) that extends ActionCard
 * The actions of these cards are not applied immediately
 * @Author Stephanie Zhang
 */
public abstract class HoldableCard extends ActionCard {
    String name;

    /**
     * Constructor
     * @param text the text is "Save" because it's a holdable card, so the initial action is to save it
     * @param name the name of the card, to be displayed when choosing cards
     */
    public HoldableCard(String text, String name) {
        super( "Save", text );
        this.name = name;
    }

    /**
     * Constructor for xml file purposes
     * @param n
     */
    public HoldableCard(Element n){
        super("Save", "");
        setText(getTagValue("Message", n));
        name = getTagValue("Type", n);
    }

    /**
     *
     * @return this card as a parameter
     */
    @Override
    public List<Object> getParameters() {
        List<Object> parameters =  new ArrayList<>(  );
        parameters.add( this );
        return parameters;
    }

    /**
     *
     * @return the name of the card
     */
    public String getName(){
        return name;
    }

    /**
     *
     * @return "Save", which is the action type
     */
    public String getActionType() {
        return "Save";
    }

    /**
     *
     * @return the holdable card's specific action, to be applied at the user's discretion
     */
    public abstract String getHoldableCardAction();
}
