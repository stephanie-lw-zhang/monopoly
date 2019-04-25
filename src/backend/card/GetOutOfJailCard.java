package backend.card;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import exceptions.TileNotFoundException;

public class GetOutOfJailCard extends ActionCard {
    public GetOutOfJailCard(AbstractBoard board) {
        super( board );
    }

    @Override
    public void applyTo(AbstractPlayer player) {
        try {
            getBoard().getJailTile().removeCriminal(player);
        }catch (TileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
