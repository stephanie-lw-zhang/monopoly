package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;

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
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        List<String> possibleActions = new ArrayList<String>(  );
        possibleActions.add( "Visiting Jail");
        return possibleActions;
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
