package backend.card.action_cards;

import backend.card.AbstractCard;

import java.util.List;

public abstract class ActionCard extends AbstractCard {
        private String type;

        public ActionCard(String type) {
                this.type = type;
        }

        public String getActionType() {
                return type;
        }

        public abstract List<Object> getParameters();

        public void setType(String s){
                type = s;
        }

}
