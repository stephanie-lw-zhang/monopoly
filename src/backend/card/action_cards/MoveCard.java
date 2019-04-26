package backend.card.action_cards;

import backend.tile.Tile;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class MoveCard extends ActionCard {
    private Tile tile;
    private int index;

    public MoveCard(Tile tile, String type, String text) {
        super(type, text);
        this.tile = tile;
        index = tile.getTileIndex();
    }

    public MoveCard(Element n){
        super("");
        this.setType(getTagValue("Type", n));
        index = Integer.parseInt(getTagValue("TileIndex", n));
    }

    public void setTile(Tile t){
        tile = t;
    }

    public int getIndex(){
        return index;
    }

    @Override
    public List<Object> getParameters() {
        List<Object> parameters = new ArrayList<Object>();
        parameters.add( tile );
        return parameters;
    }
}
