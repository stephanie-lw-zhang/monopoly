package BackEnd.Tile.DrawCardTiles;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Deck.DeckInterface;
import BackEnd.Tile.TileInterface;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDrawCardTile implements TileInterface {

    private DeckInterface myDeck;

    public AbstractDrawCardTile(DeckInterface deck) {
        myDeck = deck;
    }

    @Override
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        List<String> possibleActions = new ArrayList<String>(  );
        possibleActions.add("draw card");
//        myDeck.drawCard();
        return possibleActions;
    }

    @Override
    public void applyPassedAction(AbstractPlayer player) {
        return;
    }

}
