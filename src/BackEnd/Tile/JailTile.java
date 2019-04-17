package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import org.w3c.dom.Element;

import java.util.HashSet;
import java.util.Set;

public class JailTile extends Tile {

    private Set<AbstractPlayer> criminals;
    private int index;

    public JailTile(int index) {
        criminals = new HashSet<>();
        this.index = index;
    }

    public JailTile(Element n){
        criminals = new HashSet<>();
        index = Integer.parseInt(getTagValue("TileNumber", n));
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
