package BackEnd.Board;

/**
 * This class extends AbstractBoard and represents the traditional
 * format of a Monopoly board
 */

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Tile.TileInterface;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Map;

public class StandardBoard extends AbstractBoard {

    public StandardBoard(List<AbstractPlayer> playerList, List<TileInterface> tiles, List<List<TileInterface>> adjacencyList, Map<Color, List<TileInterface>> colorListMap){
        super(playerList, tiles, adjacencyList, colorListMap);
    }

    public void movePlayer(AbstractPlayer p, int[] rolls) {
        TileInterface tile = getPlayerTile(p);
        int index = getTileIndex(tile);
        TileInterface next = null;
        for(int i = 0; i<rolls[0]+rolls[1]; i++){
            //this needs to change for a non-standard board, could be informed by property file
            next = getAdjacentTiles(index).get(0);
            index = getTileIndex(next);
            checkIfGo(p, next);
        }
        getPlayerTileMap().put(p, next);
    }

}
