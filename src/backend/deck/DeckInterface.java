package backend.deck;

import backend.card.AbstractCard;

import java.util.List;

public interface DeckInterface {

    AbstractCard drawCard();

    void putBack(AbstractCard card);

    void addCards(List<AbstractCard> cardList);

}
