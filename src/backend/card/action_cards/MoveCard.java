package backend.card.action_cards;

import backend.tile.Tile;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for action cards that only move the player, extends ActionCard class
 * @author stephaniezhang
 */
public class MoveCard extends ActionCard {
    private Tile tile;
    private int index;

    /**
     * Constructor
     * @param tile -- the tile the player should move to
     * @param type -- action type, used in super
     * @param text -- message, used in super
     */
    public MoveCard(Tile tile, String type, String text) {
        super(type, text);
        this.tile = tile;
        index = tile.getTileIndex();
    }

    /**
     * Constructor used for xml purposes
     * @param n
     */
    public MoveCard(Element n){
        super("", "");
        setType(getTagValue("Type", n));
        setText(getTagValue("Message", n));
        index = Integer.parseInt(getTagValue("TileIndex", n));
    }

    /**
     * Set the target tile
     * @param t
     */
    public void setTile(Tile t){
        tile = t;
    }

    /**
     *
     * @return index of the tile
     */
    public int getIndex(){
        return index;
    }

    /**
     *
     * @return the tile and its index
     */
    @Override
    public List<Object> getParameters() {
        List<Object> parameters = new ArrayList<Object>();
        parameters.add( tile );
        parameters.add(index);
        return parameters;
    }
}
