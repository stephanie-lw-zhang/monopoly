package backend.card.action_cards;

import backend.assetholder.AbstractPlayer;
import backend.card.AbstractCard;
import backend.tile.Tile;

import java.util.List;

public abstract class ActionCard extends AbstractCard {
        private String type;

        public ActionCard(String type) {
                this.type = type;
        }

        public String getActionType() {
                return type;
        }

//        @Override
//        public int compareTo(ActionCard o) {
//                return this.type.compareTo(o.getActionType());
//        }

        public abstract List<Object> getParameters();

        public void setType(String s){
                type = s;
        }

}
