package backend.tile;

import backend.assetholder.AbstractPlayer;
import backend.card.AbstractCard;
import backend.deck.DeckInterface;
import controller.Actions;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractDrawCardTile extends Tile {

    private DeckInterface myDeck;

    public AbstractDrawCardTile(DeckInterface deck) {
        myDeck = deck;
    }

    @Override
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        List<String> possibleActions = new ArrayList<>();
        possibleActions.add("drawCard");
//        myDeck.drawCard();
        return possibleActions;
    }

    public AbstractDrawCardTile( Element n) {
        //TODO: Finish this implementation
    }

    public AbstractCard drawCard(){
        return myDeck.drawCard();
    }
}
