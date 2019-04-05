package BackEnd.Tile.PropertyTiles;

import BackEnd.Player.AbstractPlayer;
import BackEnd.Tile.TileInterface;

import java.awt.*;

public abstract class AbstractPropertyTile implements TileInterface {
    private String tiletype;
    private Color tilecolor;
    private int tilerent;
    private int tilerentwithcolorset;
    private int tilerent1house;
    private int tilerent2house;
    private int tilerent3house;
    private int tilerenthotel;
    private int tilemortgagevalue;
    private int tilehouseprice;
    private int tilehotelprice;

    @Override
    public void applyLandedOnAction(AbstractPlayer player) {

    }

    @Override
    public void applyPassedAction(AbstractPlayer player) {
        return;
    }

}
