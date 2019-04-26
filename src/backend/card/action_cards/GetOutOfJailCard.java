package backend.card.action_cards;

import backend.assetholder.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;

public class GetOutOfJailCard extends ActionCard {

    public GetOutOfJailCard(String type) {
        super( type );
    }

    @Override
    public List<Object> getParameters() {
        return new ArrayList<>(  );
    }
}
