package BackEnd.Tile;

import BackEnd.Deck.DeckInterface;
import BackEnd.Tile.AbstractDrawCardTile;
import org.w3c.dom.Element;

public class CommunityChestTile extends AbstractDrawCardTile {

    public CommunityChestTile(DeckInterface deck) {
        super(deck);
    }

    public CommunityChestTile(Element n){
        super(n);
    }

}
