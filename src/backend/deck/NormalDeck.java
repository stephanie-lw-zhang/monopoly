package backend.deck;

import backend.card.action_cards.ActionCard;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class NormalDeck implements DeckInterface {
    private Queue<ActionCard> myDeck = new PriorityQueue<>();

    public int getMyDeckSize() {
        //for Testing
        return myDeck.size();
    }

    @Override
    public ActionCard drawCard() {
        if(myDeck != null){
            return myDeck.remove();
        } else{
            return null;
            //EXCEPTION HANDLING
        }
        //draws "top" card
    }

    @Override
    public void putBack(ActionCard card) {
        myDeck.add(card);
        //adds card to "bottom"
    }

    @Override
    public void addCards(List<ActionCard> cardList) {
        for(ActionCard card: cardList){
            myDeck.add( card );
        }
    }
}
