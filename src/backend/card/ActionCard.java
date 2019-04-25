package backend.card;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;

public abstract class ActionCard {

        private AbstractBoard board;

        public ActionCard(AbstractBoard board) {
                this.board = board;
        }

        public AbstractBoard getBoard(){
                return board;
        }

        public abstract void applyTo(AbstractPlayer player);

}
