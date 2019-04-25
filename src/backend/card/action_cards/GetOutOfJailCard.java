package backend.card.action_cards;

import backend.assetholder.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;

public class GetOutOfJailCard extends ActionCard {

    @Override
    public String getActionType() {
        return "GetOutOfJail";
    }

    @Override
    public List<Object> getParameters() {
        return new ArrayList<>(  );
    }
}
