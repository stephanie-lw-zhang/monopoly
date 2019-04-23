package backend.tile;

import backend.assetholder.AbstractPlayer;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class GoToJailTile extends Tile {

    public GoToJailTile(Element n){
        setTileType(getTagValue("TileType", n));
        setTileIndex(Integer.parseInt(getTagValue("TileNumber", n)));
    }

    @Override
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        List<String> possibleActions = new ArrayList<>(  );
        possibleActions.add("GO TO JAIL");
        return possibleActions;
    }

    public void putPlayerInJail(AbstractPlayer player) {
        player.addTurnInJail();
    }

    @Override
    public boolean isGoToJailTile(){
        return true;
    }
}

