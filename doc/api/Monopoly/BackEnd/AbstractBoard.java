package api.Monopoly.BackEnd;

import java.util.*;

/**
 * 
 */
public abstract class AbstractBoard {

    private Map<AbstractPlayer, AbstractTile> playerToTileMap;
    private List<List<AbstractTile>> adjacencyList;
    private List<AbstractTile> tiles;

    /**
     * Constructor that takes in the list of players, tiles, and an adjacency list for the graph of tiles
     */
    public AbstractBoard(List<AbstractPlayer> playerList, List<AbstractTile> tiles, List<List<AbstractTile>> adjacencyList) {
        this.tiles = tiles;
        this.adjacencyList = adjacencyList;
        playerToTileMap = new HashMap<>();
        for(AbstractPlayer p: playerList) playerToTileMap.put(p, tiles.get(0));
    }

    /**
     * gets the Tile that the player is currently on
     */
    public AbstractTile getPlayerTile(AbstractPlayer p){
        return playerToTileMap.get(p);
    }

    /**
     * Checks if the player is on the GO Tile and gives the player $200 if so
     */
    public void checkIfGo(AbstractPlayer p, AbstractTile tile) {
        // TODO implement here
        if (tile instanceof GoTile){
            //p.addFunds(200);
        }
    }

    /**
     * Moves the player on the board by reassigning its tile mapping
     */
    public abstract void movePlayer(AbstractPlayer p, int[] rolls, boolean jailed);

    public Map<AbstractPlayer, AbstractTile> getPlayerTileMap(){
        return playerToTileMap;
    }
    public int getTileIndex(AbstractTile tile){
        return tiles.indexOf(tile);
    }
    public List<AbstractTile> getAdjacentTiles(int tileIndex){
        return adjacencyList.get(tileIndex);
    }

    /**
     * @param config
     */
    public void initialize(Scanner config) {

    }

}