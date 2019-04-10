package BackEnd.Board;

/**
 * This class extends AbstractBoard and represents the traditional
 * format of a Monopoly board
 */

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Tile.GoTile;
import BackEnd.Tile.GoToJailTile;
import BackEnd.Tile.JailTile;
import BackEnd.Tile.PropertyTiles.BuildingTile;
import BackEnd.Tile.TileInterface;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Map;

public class StandardBoard extends AbstractBoard {

    public StandardBoard(List<AbstractPlayer> playerList, Map<TileInterface, List<TileInterface>> adjacencyList, Map<Color, List<BuildingTile>> colorListMap, TileInterface go) {
        super(playerList, adjacencyList, colorListMap, go, 2);
    }

    public void movePlayer(AbstractPlayer p, int numMoves) {
        TileInterface tile = getPlayerTile(p);

        TileInterface next = null;
        for(int i = 0; i < numMoves; i++){
            //this needs to change for a non-standard board, could be informed by property file
            next = getAdjacentTiles(tile).get(0);
            tile = next;
            tile.applyPassedAction(p);
        }
        if(tile instanceof GoToJailTile) tile = getJailTile();
        getPlayerTileMap().put(p, tile);
    }
}
