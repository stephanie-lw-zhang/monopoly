package backend.card.action_cards;

import backend.assetholder.AbstractPlayer;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for get out of jail cards
 *
 * @author stephaniezhang
 */

public class GetOutOfJailCard extends HoldableCard {

    /**
     * Constructor for get out of jail cards
     * @param text the text is "Save" because it's a holdable card, so the initial action is to save it
     */

    public GetOutOfJailCard(String text) {
        super( "Save", text );

    }

    /**
     * Constructor for xml file purposes
     * @param n
     */
    public GetOutOfJailCard(Element n){
        super("", "Get Out Of Jail");
        setType(getTagValue("Type", n));
        setText( getTagValue( "Message",n ) );
    }

    /**
     * get the actual action of the card (when you want to use it and not just save)
     * Again, there will be a method handle + getHoldableCardAction in ActionCardController
     * @return
     */
    @Override
    public String getHoldableCardAction() {
        return "GetOutOfJail";
    }
}