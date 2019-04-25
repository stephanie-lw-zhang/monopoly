package backend.deck;

import backend.card.ActionCard;

import java.util.List;

public interface DeckInterface {

    ActionCard drawCard();

    void putBack(ActionCard card);

    void addCards(List<ActionCard> cardList);

}
