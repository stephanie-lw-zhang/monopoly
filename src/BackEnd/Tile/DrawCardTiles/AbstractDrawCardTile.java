package BackEnd.Tile.DrawCardTiles;

import BackEnd.Deck.AbstractDeck;
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Tile.TileInterface;

public abstract class AbstractDrawCardTile implements TileInterface {

    private AbstractDeck myDeck;

    public AbstractDrawCardTile(AbstractDeck deck) {
        myDeck = deck;
    }

    @Override
    public void applyLandedOnAction(AbstractPlayer player) {
        myDeck.drawTopCard();
    }

    @Override
    public void applyPassedAction(AbstractPlayer player) {
        return;
    }

}
