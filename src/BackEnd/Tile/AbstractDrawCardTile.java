package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Card.AbstractCard;
import BackEnd.Deck.DeckInterface;
import Controller.Actions;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractDrawCardTile extends Tile {

    private DeckInterface myDeck;

    public AbstractDrawCardTile(DeckInterface deck) {
        myDeck = deck;
    }

    @Override
    public List<Actions> applyLandedOnAction(AbstractPlayer player) {
        List<Actions> possibleActions = new ArrayList<>();
        possibleActions.add(Actions.DRAW_CARD);
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
