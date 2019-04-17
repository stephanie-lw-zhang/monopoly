package BackEnd.Board;

/**
 * This class extends AbstractBoard and represents the traditional
 * format of a Monopoly board
 */

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Tile.GoToJailTile;
import BackEnd.Tile.AbstractPropertyTile;
import BackEnd.Tile.Tile;

import java.util.List;
import java.util.Map;

public class StandardBoard extends AbstractBoard {

    public StandardBoard(List<AbstractPlayer> playerList, Map<Tile, List<Tile>> adjacencyMap, Map<String, List<AbstractPropertyTile>> colorListMap, Tile go) {
        super(playerList, adjacencyMap, colorListMap, go, 2);
    }

    public Map<Tile, List<Tile>> makeAdjacencyMap() {
        return null;
    }

    public void movePlayer(AbstractPlayer p, int numMoves) {
        Tile tile = getPlayerTile(p);

        Tile next = null;
        for(int i = 0; i < numMoves; i++) {
            //this needs to change for a non-standard board, could be informed by property file
            next = getAdjacentTiles(tile).get(0);
            tile = next;
            tile.applyPassedAction(p);
        }
        if(tile instanceof GoToJailTile) tile = getJailTile();
        getPlayerTileMap().put(p, tile);
    }
}
