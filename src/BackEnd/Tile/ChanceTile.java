package BackEnd.Tile;

import BackEnd.Deck.DeckInterface;
import BackEnd.Tile.AbstractDrawCardTile;
import org.w3c.dom.Element;

public class ChanceTile extends AbstractDrawCardTile {

    public ChanceTile(DeckInterface deck, int index) {
        super(deck, index);
    }

    public ChanceTile(Element n){
        super(n);
    }

}
