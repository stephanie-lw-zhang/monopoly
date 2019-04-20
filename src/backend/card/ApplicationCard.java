package backend.card;

import backend.board.AbstractBoard;

public abstract class ApplicationCard {
        private AbstractBoard board;

        public ApplicationCard(AbstractBoard board) {
                this.board = board;
        }

        public AbstractBoard getBoard(){
                return board;
        }

        public abstract void applyTo(backend.assetholder.AbstractPlayer player);

}
