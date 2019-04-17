package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Deck.DeckInterface;
import api.Monopoly.BackEnd.AbstractTile;
import org.w3c.dom.Element;

public abstract class AbstractDrawCardTile extends Tile {

    private DeckInterface myDeck;
    private int index;

    public AbstractDrawCardTile(DeckInterface deck, int index) {
        myDeck = deck;
        this.index = index;
    }

    public AbstractDrawCardTile(Element n){
        index = Integer.parseInt(getTagValue("TileNumber", n));
    }

    public void applyLandedOnAction(AbstractPlayer player) {
        myDeck.drawCard();
    }

    public void applyPassedAction(AbstractPlayer player) {
        return;
    }


}
