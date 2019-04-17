package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;
import api.Monopoly.BackEnd.AbstractTile;
import org.w3c.dom.Element;

public class GoToJailTile extends Tile {

    private JailTile jail;
    int index;

    public GoToJailTile(JailTile jail, int index) {
        this.jail = jail;
        this.index = index;
    }

    public GoToJailTile(Element n){
        index = Integer.parseInt(getTagValue("TileNumber", n));
    }

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
