package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class GoToJailTile extends Tile {

    private JailTile jail;

    public GoToJailTile(JailTile jail) {
        this.jail = jail;
    }

    public GoToJailTile(Element n){}

    @Override
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        List<String> possibleActions = new ArrayList<String>(  );
        return possibleActions;
    }

    @Override
    public void applyPassedAction(AbstractPlayer player) {
        return;
    }
}
