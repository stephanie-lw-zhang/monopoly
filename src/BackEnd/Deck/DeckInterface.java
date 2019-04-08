package BackEnd.Deck;

import BackEnd.Card.AbstractCard;

import java.util.List;

public interface DeckInterface {

    AbstractCard drawCard();

    void returnCard(AbstractCard card);

    void addCards(List<AbstractCard> cardList);

}
