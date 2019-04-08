package BackEnd.Tile.DrawCardTiles;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Deck.DeckInterface;
import BackEnd.Tile.TileInterface;

public abstract class AbstractDrawCardTile implements TileInterface {

    private DeckInterface myDeck;

    public AbstractDrawCardTile(DeckInterface deck) {
        myDeck = deck;
    }

    @Override
    public void applyLandedOnAction(AbstractPlayer player) {
        myDeck.drawCard();
    }

//    @Override
//    public void applyPassedAction(AbstractPlayer player) {
//        return;
//    }

}
