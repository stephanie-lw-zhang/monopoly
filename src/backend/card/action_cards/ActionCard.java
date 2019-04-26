package backend.card.action_cards;

import backend.assetholder.AbstractPlayer;
import backend.card.AbstractCard;

import java.util.List;

public abstract class ActionCard extends AbstractCard {

        public abstract String getActionType();

        public abstract List<Object> getParameters();

}
