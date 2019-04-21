package backend.card;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import javafx.application.Application;

public class GetOutOfJailCard extends ApplicationCard {
    public GetOutOfJailCard(AbstractBoard board) {
        super( board );
    }

    @Override
    public void applyTo(AbstractPlayer player) {
        getBoard().getJailTile().removeCriminal(player);
    }
}
