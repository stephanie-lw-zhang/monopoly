package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import org.w3c.dom.Element;

public class GoToJailTile implements TileInterface {

    private JailTile jail;

    public GoToJailTile(JailTile jail) {
        this.jail = jail;
    }

    public GoToJailTile(Element n){}

    @Override
    public void applyLandedOnAction(AbstractPlayer player) {
        player.addTurnInJail();
        jail.addCriminal(player);
    }

    @Override
    public void applyPassedAction(AbstractPlayer player) {
        return;
    }
}
