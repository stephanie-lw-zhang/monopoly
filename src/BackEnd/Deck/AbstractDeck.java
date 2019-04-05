package BackEnd.Deck;

import BackEnd.Card.AbstractCard;

import java.util.PriorityQueue;
import java.util.Queue;

public class AbstractDeck {

    private Queue<AbstractCard> myDeck;

    public AbstractDeck() {
        myDeck = new PriorityQueue<>();
    }

    public void addCardToBottom(AbstractCard card) {
        myDeck.add(card);
    }

    public void shuffleDeck() {

    }

    public AbstractCard drawTopCard() {
        return myDeck.remove();
    }

}
