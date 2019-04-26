package backend.card.action_cards;

import java.util.ArrayList;
import java.util.List;

public abstract class HoldableCard extends ActionCard {
    String name;

    public HoldableCard(String text, String name) {
        super( "Save", text );
        this.name = name;
    }

    @Override
    public List<Object> getParameters() {
        List<Object> parameters =  new ArrayList<>(  );
        parameters.add( this );
        return parameters;
    }

    public String getName(){
        return name;
    }
}
