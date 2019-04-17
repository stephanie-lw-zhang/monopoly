package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Deck.DeckInterface;
import org.w3c.dom.Element;

public abstract class AbstractDrawCardTile extends Tile {

    private DeckInterface myDeck;

    public AbstractDrawCardTile(DeckInterface deck) {
        myDeck = deck;
    }

    public AbstractDrawCardTile(Element n){
        //TODO: Finish this implementation
    }

    public void applyLandedOnAction(AbstractPlayer player) {
        myDeck.drawCard();
    }

    public void applyPassedAction(AbstractPlayer player) {
        return;
    }


}
