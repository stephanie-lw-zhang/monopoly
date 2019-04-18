package backend.deck;

import backend.card.AbstractCard;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class NormalDeck implements DeckInterface {
    private Queue<AbstractCard> myDeck = new PriorityQueue<>();

    @Override
    public AbstractCard drawCard() {
        if(myDeck != null){
            return myDeck.remove();
        } else{
            return null;
            //EXCEPTION HANDLING
        }
        //draws "top" card
    }

    @Override
    public void returnCard(AbstractCard card) {
        myDeck.add(card);
        //adds card to "bottom"
    }

    @Override
    public void addCards(List<AbstractCard> cardList) {
        for(AbstractCard card: cardList){
            myDeck.add( card );
        }
    }

}
