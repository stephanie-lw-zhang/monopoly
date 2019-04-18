package backend.tile;

import backend.assetholder.AbstractPlayer;
import org.w3c.dom.Element;

import controller.Actions;

import java.util.ArrayList;
import java.util.List;

public class GoToJailTile extends Tile {

    public GoToJailTile(Element n){}

    @Override
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        List<String> possibleActions = new ArrayList<>(  );
        possibleActions.add("goToJail");
        return possibleActions;
    }

    public void putPlayerInJail(AbstractPlayer player) {
        player.addTurnInJail();
    }
}
