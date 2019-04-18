package backend.Deck;

import backend.Card.AbstractCard;

import java.util.List;

public interface DeckInterface {

    AbstractCard drawCard();

    void returnCard(AbstractCard card);

    void addCards(List<AbstractCard> cardList);

}
