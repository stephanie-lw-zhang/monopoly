package backend.tile;

import backend.deck.DeckInterface;
import org.w3c.dom.Element;

public class ChanceTile extends AbstractDrawCardTile {

    public ChanceTile(DeckInterface deck, int index) {
        super(deck, index);
    }

    public ChanceTile(Element n){
        super(n);
    }

}
