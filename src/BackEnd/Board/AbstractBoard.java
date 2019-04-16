package BackEnd.Board;


/**
 * This class is an abstraction of the game board which contains
 * fundamental pieces to the game itself
 *
 * @author Sam
 *             updated Constructor for PlayerList
 */
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Tile.JailTile;
import BackEnd.Tile.AbstractPropertyTile;
import BackEnd.Tile.TileInterface;

import java.util.*;

/**
 *
 */
public abstract class AbstractBoard {

    private Map<AbstractPlayer, TileInterface>      playerPositionMap;
    private Map<TileInterface, List<TileInterface>> adjacencyMap;
    private Map<String, List<AbstractPropertyTile>> propertyCategoryToSpecificListMap;
    private List<AbstractPlayer>                    myPlayerList;
    private int                                     numDie;

    /**
     * Constructor that takes in the list of players, tiles, and an adjacency list for the graph of tiles
     */
    public AbstractBoard(List<AbstractPlayer> playerList, Map<TileInterface, List<TileInterface>> adjacencyMap, Map<String, List<AbstractPropertyTile>> colorListMap, TileInterface go, int nDie) {
        myPlayerList = playerList;
        adjacencyMap = adjacencyMap;
        propertyCategoryToSpecificListMap = colorListMap;
        playerPositionMap = new HashMap<>();
        numDie = nDie;
        for (AbstractPlayer p : playerList) playerPositionMap.put(p, go);
    }

    /**
     * gets the Tile that the player is currently on
     */
    public TileInterface getPlayerTile(AbstractPlayer p) {
        return playerPositionMap.get(p);
    }

    /**
     * Moves the player on the board by reassigning its tile mapping
     */
    public abstract void movePlayer(AbstractPlayer p, int numMoves);

    public Map<AbstractPlayer, TileInterface> getPlayerTileMap() {
        return playerPositionMap;
    }

    public List<TileInterface> getAdjacentTiles(TileInterface tile) {
        return adjacencyMap.get(tile);
    }

    public TileInterface getJailTile(){
        TileInterface tile = null;
        for (TileInterface key: adjacencyMap.keySet()) {
            if(key instanceof JailTile){
                tile = key;
            }
        }
        return tile;
    }

    public List<AbstractPlayer> getMyPlayerList() { return myPlayerList; }
    public Map<String, List<AbstractPropertyTile>> getColorListMap() {
        return propertyCategoryToSpecificListMap;
    }
    public int getNumDie() { return numDie; }
}
