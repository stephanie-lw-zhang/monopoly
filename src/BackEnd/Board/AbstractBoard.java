package BackEnd.Board;

/**
 * This class is an abstraction of the game board which contains
 * fundamental pieces to the game itself
 */
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Tile.TileInterface;
import BackEnd.Tile.GoTile;
import javafx.scene.paint.Color;

import java.util.*;

/**
 *
 */
public abstract class AbstractBoard {

    private Map<AbstractPlayer, TileInterface> playerPositionMap;
    private List<List<TileInterface>> adjacencyList;
    private List<TileInterface> tiles;
    private Map<Color, List<TileInterface>> colorListMap;

    /**
     * Constructor that takes in the list of players, tiles, and an adjacency list for the graph of tiles
     */
    public AbstractBoard(List<AbstractPlayer> playerList, List<TileInterface> tiles, List<List<TileInterface>> adjacencyList,Map<Color, List<TileInterface>> colorListMap) {
        this.tiles = tiles;
        this.adjacencyList = adjacencyList;
        this.colorListMap = colorListMap;
        playerPositionMap = new HashMap<>();
        for (AbstractPlayer p : playerList) playerPositionMap.put(p, tiles.get(0));
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

    public int getTileIndex(TileInterface tile) {
        return tiles.indexOf(tile);
    }

    public List<TileInterface> getAdjacentTiles(int tileIndex) {
        return adjacencyList.get(tileIndex);
    }

}
