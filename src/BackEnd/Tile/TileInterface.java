package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;

import java.util.List;

public interface TileInterface {
    List<String> applyLandedOnAction(AbstractPlayer p);

    void applyPassedAction(AbstractPlayer p);
}
