package backend.tile;

import backend.deck.DeckInterface;
import org.w3c.dom.Element;

public class CommunityChestTile extends AbstractDrawCardTile {

    public CommunityChestTile(DeckInterface deck, int index) {
        super(deck, index);
    }

    public CommunityChestTile(Element n){
        super(n);
    }

    public void nothing(){
        return;
    }

}
