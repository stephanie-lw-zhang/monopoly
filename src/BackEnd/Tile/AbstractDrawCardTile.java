package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Deck.DeckInterface;
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
        List<String> possibleActions = new ArrayList<String>();
        possibleActions.add( "draw card" );
//        myDeck.drawCard();
        return possibleActions;
    }

    public AbstractDrawCardTile( Element n) {
        //TODO: Finish this implementation
    }

    public void applyPassedAction (AbstractPlayer player){
        return;
    }
}
