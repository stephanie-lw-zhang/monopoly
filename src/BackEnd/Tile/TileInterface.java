package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;

public interface TileInterface {

//    void applyLandedOnAction(AbstractPlayer p);
    public default void applyLandedOnAction(AbstractPlayer p) {
        System.out.println("Player " + p.getMyPlayerName() + " landed on ");
    }

    void applyPassedAction(AbstractPlayer p);
}
