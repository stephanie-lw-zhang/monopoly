package backend.card.action_cards;

import backend.assetholder.AbstractPlayer;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class GetOutOfJailCard extends ActionCard {

    public GetOutOfJailCard(Element n){}

    @Override
    public String getActionType() {
        return "GetOutOfJail";
    }

    @Override
    public List<Object> getParameters() {
        return new ArrayList<>(  );
    }
}
