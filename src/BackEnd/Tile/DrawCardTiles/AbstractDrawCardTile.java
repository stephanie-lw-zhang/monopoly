package BackEnd.Tile.DrawCardTiles;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Deck.DeckInterface;
import BackEnd.Tile.TileInterface;
import org.w3c.dom.Element;

public abstract class AbstractDrawCardTile implements TileInterface {

    private DeckInterface myDeck;

    public AbstractDrawCardTile(DeckInterface deck) {
        myDeck = deck;
    }

    public AbstractDrawCardTile(Element n){
        //TODO: Finish this implementation
    }

    @Override
    public void applyLandedOnAction(AbstractPlayer player) {
        myDeck.drawCard();
    }

    @Override
    public void applyPassedAction(AbstractPlayer player) {
        return;
    }


}
