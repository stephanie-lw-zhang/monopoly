package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;

import Controller.Actions;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class GoToJailTile extends Tile {

    public GoToJailTile(Element n){}

    @Override
    public List<Actions> applyLandedOnAction(AbstractPlayer player) {
        List<Actions> possibleActions = new ArrayList<>(  );
        possibleActions.add(Actions.GO_TO_JAIL);
        return possibleActions;
    }

    public void putPlayerInJail(AbstractPlayer player) {
        player.addTurnInJail();
    }
}
