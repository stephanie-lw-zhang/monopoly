package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;

import java.util.HashSet;
import java.util.Set;

public class JailTile implements TileInterface {

    private Set<AbstractPlayer> criminals;

    public JailTile() {
        criminals = new HashSet<>();
    }

    @Override
    public void applyLandedOnAction(AbstractPlayer player) {
        return;
    }

    @Override
    public void applyPassedAction(AbstractPlayer player) {
        return;
    }

    public void addCriminal(AbstractPlayer player) {
        criminals.add(player);
    }

    public void removeCriminal(AbstractPlayer player) {
        //throw exception?? if player is not in the hashset
        criminals.remove(player);
    }
}
