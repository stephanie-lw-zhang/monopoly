package backend.card;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import javafx.application.Application;

public class HoldableCard extends ApplicationCard {
    public HoldableCard(AbstractBoard board) {
        super( board );
    }

    @Override
    public void applyTo(AbstractPlayer player) {

    }
}
