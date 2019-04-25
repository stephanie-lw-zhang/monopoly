package backend.tile;

import backend.assetholder.AbstractPlayer;
import backend.card.action_cards.ActionCard;
import backend.deck.DeckInterface;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractDrawCardTile extends Tile {

    private DeckInterface myDeck;
    private String tileType;
    private int index;

    public AbstractDrawCardTile(DeckInterface deck, String tileType, int index) {
        myDeck = deck;
        this.tileType = tileType;
        this.index = index;
    }

    public AbstractDrawCardTile(Element n) {
        setTileType(getTagValue("TileType", n));
        setTileIndex(Integer.parseInt(getTagValue("TileNumber", n)));
        //TODO: Finish this implementation
    }

    @Override
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        List<String> possibleActions = new ArrayList<>();
        possibleActions.add("DRAW CARD");
//        myDeck.drawCard();
        return possibleActions;
    }

    public ActionCard drawCard(){
        return myDeck.drawCard();
    }
}
