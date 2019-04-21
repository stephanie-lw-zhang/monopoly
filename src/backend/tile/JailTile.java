package backend.tile;

import backend.assetholder.AbstractPlayer;

import org.w3c.dom.Element;

import java.util.HashSet;
import java.util.List;
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
        setTileIndex(Integer.parseInt(getTagValue("TileNumber", n)));
    }

    @Override
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        return null;
    }

    public void addCriminal(AbstractPlayer player) {
        criminals.add(player);
    }

    public void removeCriminal(AbstractPlayer player) {
        //throw exception?? if player is not in the hashset
        criminals.remove(player);
        player.getOutOfJail();
    }
}
