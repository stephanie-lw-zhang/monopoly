package BackEnd.Board;

/**
 * This class is an abstraction of the game board which contains
 * fundamental pieces to the game itself
 */
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Tile.JailTile;
import BackEnd.Tile.PropertyTiles.BuildingTile;
import BackEnd.Tile.TileInterface;
import BackEnd.Tile.GoTile;
import javafx.scene.paint.Color;

import java.util.*;

/**
 *
 */
public abstract class AbstractBoard {
    private Map<AbstractPlayer, TileInterface> playerPositionMap;
    private Map<TileInterface, List<TileInterface>> adjacencyList;
    private Map<Color, List<BuildingTile>> colorListMap;

    /**
     * Constructor that takes in the list of players, tiles, and an adjacency list for the graph of tiles
     */

    public AbstractBoard(List<AbstractPlayer> playerList, Map<TileInterface, List<TileInterface>> adjacencyList, Map<Color, List<BuildingTile>> colorListMap) {
        this.adjacencyList = adjacencyList;
        this.colorListMap = colorListMap;
        playerPositionMap = new HashMap<>();
        for (AbstractPlayer p : playerList) playerPositionMap.put(p, adjacencyList.keySet().stream().findFirst().get());
    }

    /**
     * gets the Tile that the player is currently on
     */
    public TileInterface getPlayerTile(AbstractPlayer p) {
        return playerPositionMap.get(p);
    }

    /**
     * Checks if the player is on the GO Tile and gives the player $200 if so
     */
    public void checkIfGo(AbstractPlayer p, TileInterface tile) {
        if (tile instanceof GoTile) {
            p.setMoney(p.getMoney() + 200);
        }
    }

    /**
     * Moves the player on the board by reassigning its tile mapping
     */
    public abstract void movePlayer(AbstractPlayer p, int[] rolls);

    public Map<AbstractPlayer, TileInterface> getPlayerTileMap() {
        return playerPositionMap;
    }

    public List<TileInterface> getAdjacentTiles(TileInterface tile) {
        return adjacencyList.get(tile);
    }

    public TileInterface getJailTile(){
        TileInterface tile = null;
        for (TileInterface key: adjacencyList.keySet()) {
            if(key instanceof JailTile){
                tile = key;
            }
        }
        return tile;
    }

    public Map<Color, List<BuildingTile>> getColorListMap() {
        return colorListMap;
    }

}
