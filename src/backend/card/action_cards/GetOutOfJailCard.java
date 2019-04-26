package backend.card.action_cards;

import backend.assetholder.AbstractPlayer;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class GetOutOfJailCard extends ActionCard {

    public GetOutOfJailCard(Element n){
        super("");
        this.setType(getTagValue("Type", n));
    }

    @Override
    public String getActionType() {
        return "GetOutOfJail";
    }

    public GetOutOfJailCard(String type) {
        super( type );
    }

    @Override
    public List<Object> getParameters() {
        return new ArrayList<>(  );
    }
}
