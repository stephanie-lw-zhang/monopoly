package backend.card;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.tile.Tile;

public class MoveCard extends ActionCard {
    private Tile tile;

    public MoveCard(AbstractBoard board, Tile tile) {
        super(board);
        this.tile = tile;
    }

    @Override
    public void applyTo(AbstractPlayer player) {
        getBoard().movePlayer( player, tile );
    }
}
