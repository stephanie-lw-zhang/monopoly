package backend.card.action_cards;

import backend.assetholder.AbstractPlayer;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class GetOutOfJailCard extends HoldableCard {

    public GetOutOfJailCard(String type, String text, Element n) {
        super( type, text );

    }

    public GetOutOfJailCard(Element n){
        super("", "");
        this.setType(getTagValue("Type", n));
    }

    @Override
    public String getActionType() {
        return "GetOutOfJail";
    }



}
