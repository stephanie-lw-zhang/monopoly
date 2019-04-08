package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Tile.TileInterface;

import java.awt.*;

public abstract class AbstractPropertyTile implements TileInterface {
    private String tiletype;
    private Color tilecolor;
    private double tilerent;
    private double tilerentwithcolorset;
    private double tilerent1house;
    private double tilerent2house;
    private double tilerent3house;
    private double tilerenthotel;
    private double tilemortgagevalue;
    private double tilehouseprice;
    private double tilehotelprice;

    @Override
    public void applyLandedOnAction(AbstractPlayer player) {

    }

    @Override
    public void applyPassedAction(AbstractPlayer player) {
        return;
    }


    public int getNumberOfHouses(){
        return 0;
        //placeholder
    }

    public int getNumberOfHotels(){
        return 0;
        //placeholder
    }

    public Double sellToBankPrice (){
        Double total = (getNumberOfHouses() * tilehouseprice + getNumberOfHotels() + tilehotelprice)/2;
        return total;
        //assume not mortgaged
    }

}
