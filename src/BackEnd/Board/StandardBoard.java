package BackEnd.Board;

/**
 * This class extends AbstractBoard and represents the traditional
 * format of a Monopoly board
 */

import BackEnd.Player.AbstractPlayer;
import BackEnd.Tile.TileInterface;

import java.util.List;

public class StandardBoard extends AbstractBoard {

    public StandardBoard(List<AbstractPlayer> playerList, List<TileInterface> tiles, List<List<TileInterface>> adjacencyList){
        super(playerList, tiles, adjacencyList);
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
