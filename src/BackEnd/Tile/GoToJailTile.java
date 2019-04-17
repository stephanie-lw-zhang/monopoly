package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class GoToJailTile extends Tile {

    public GoToJailTile(Element n){}

    @Override
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        List<String> possibleActions = new ArrayList<String>(  );
        possibleActions.add("Go to jail");
        return possibleActions;
    }

    public void putPlayerInJail(AbstractPlayer player) {
        player.addTurnInJail();
    }

    @Override
    public void applyPassedAction(AbstractPlayer player) {
        return;
    }
}
