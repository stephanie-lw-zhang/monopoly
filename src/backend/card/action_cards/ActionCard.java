package backend.card.action_cards;

import backend.card.AbstractCard;

import java.util.List;

/**
 * This class is an abstraction of action cards (the cards that signify an action, usually drawn from community chest of chance decks)
 *
 * @Author Stephanie
 */

public abstract class ActionCard extends AbstractCard {
        private String type;
        private String text;

        /**
         * Constructor for action cards
         * @param type -- this signifies the action, and there is a corresponding method in ActionCardController with "handle" + type that is called to apply the card
         * @param text -- the message shown on the screen when the card is drawn
         */
        public ActionCard(String type, String text) {
                this.type = type;
                this.text = text;
        }

        /**
         * returns the action as a string
         * @return String type
         */
        public String getActionType() {
                return type;
        }

        /**
         * returns the message on the action card
         * @return
         */
        public String getText(){
                return text;
        }

        /**
         * This returns the parameters needed to execute the method. This method is used in reflection, and passed into the "handle" + type method in ActionCardController.
         *
         * @return List<Object> parameters
         */
        public abstract List<Object> getParameters();

        /**
         * sets the type, used for more flexibility
         * @param s
         */
        public void setType(String s){
                type = s;
        }

        /**
         * sets the text
         * @param t
         */
        public void setText(String t) {text = t;}
}
