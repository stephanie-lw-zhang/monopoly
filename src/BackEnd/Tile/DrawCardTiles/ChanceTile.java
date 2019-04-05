package BackEnd.Tile.DrawCardTiles;

import BackEnd.Deck.AbstractDeck;
import BackEnd.Player.AbstractPlayer;
import BackEnd.Tile.DrawCardTiles.AbstractDrawCardTile;

public class ChanceTile extends AbstractDrawCardTile {

    public ChanceTile(AbstractDeck deck) {
        super(deck);
    }

    @Override
    public void applyLandedOnAction(AbstractPlayer player) {

    }
}
