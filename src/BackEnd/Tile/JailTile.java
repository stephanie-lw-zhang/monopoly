package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import Controller.Actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JailTile extends Tile {

    private Set<AbstractPlayer> criminals;

    public JailTile() {
        criminals = new HashSet<>();
    }

    @Override
    public List<Actions> applyLandedOnAction(AbstractPlayer player) {
        return null;
    }

    public void addCriminal(AbstractPlayer player) {
        criminals.add(player);
    }

    public void removeCriminal(AbstractPlayer player) {
        //throw exception?? if player is not in the hashset
        criminals.remove(player);
    }
}
