package backend.card.action_cards;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.tile.Tile;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class MoveCard extends ActionCard {
    private Tile tile;

    public MoveCard(Tile tile, String type) {
        super(type);
        this.tile = tile;
    }

    public MoveCard(Element n){
        super("");
        this.setType(getTagValue("Type", n));
    }

    @Override
    public List<Object> getParameters() {
        List<Object> parameters = new ArrayList<Object>();
        parameters.add( tile );
        return parameters;
    }
}
