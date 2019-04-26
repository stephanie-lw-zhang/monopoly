package backend.card.action_cards;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class MoveCard extends ActionCard {
    private Tile tile;
    private String type;

    public MoveCard(Tile tile, String type) {
        this.tile = tile;
        this.type = type;
    }

    @Override
    public String getActionType() {
        return type;
    }

    @Override
    public List<Object> getParameters() {
        List<Object> parameters = new ArrayList<Object>();
        parameters.add( tile );
        return parameters;
    }
}
