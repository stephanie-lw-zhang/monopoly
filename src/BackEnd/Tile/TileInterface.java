package BackEnd.Tile;

import BackEnd.Player.AbstractPlayer;

public interface TileInterface {
    void applyLandedOnAction(AbstractPlayer p);

    void applyPassedAction(AbstractPlayer p);
}
