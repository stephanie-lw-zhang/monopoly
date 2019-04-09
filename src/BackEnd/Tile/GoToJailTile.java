package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractPlayer;

public class GoToJailTile implements TileInterface {

    private JailTile jail;

    public GoToJailTile(JailTile jail) {
        this.jail = jail;
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
