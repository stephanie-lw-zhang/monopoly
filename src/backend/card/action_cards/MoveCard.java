package backend.card.action_cards;

import backend.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class MoveCard extends ActionCard {
    private Tile tile;

    public MoveCard(Tile tile, String type, String text) {
        super(type, text);
        this.tile = tile;
    }

    @Override
    public List<Object> getParameters() {
        List<Object> parameters = new ArrayList<Object>();
        parameters.add( tile );
        return parameters;
    }
}
