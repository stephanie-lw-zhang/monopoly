package backend.card;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;

public class GetOutOfJailCard extends ActionCard {
    public GetOutOfJailCard(AbstractBoard board) {
        super( board );
    }

    @Override
    public void applyTo(AbstractPlayer player) {
        getBoard().getJailTile().removeCriminal(player);
    }
}
