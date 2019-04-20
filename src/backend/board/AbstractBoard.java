package backend.board;


/**
 * This class is an abstraction of the game board which contains
 * fundamental pieces to the game itself
 *
 * @author Matt, Sam
 *             updated Constructor for PlayerList
 */
import backend.assetholder.AbstractPlayer;
import backend.assetholder.Bank;
import backend.tile.JailTile;
import backend.tile.AbstractPropertyTile;
import backend.tile.Tile;

import java.util.*;

/**
 *
 */
public abstract class AbstractBoard {
    private Map<AbstractPlayer, Tile>               playerPositionMap;
    private Map<Tile, List<Tile>>                   adjacencyMap;
    private Map<String, List<AbstractPropertyTile>> propertyCategoryToSpecificListMap;
    private List<AbstractPlayer>                    myPlayerList;
    private int                                     numDie;
    private Bank bank;

    /**
     * Constructor that takes in the list of players, tiles, and an adjacency list for the graph of tiles
     */
    public AbstractBoard(List<AbstractPlayer> playerList, Map<Tile, List<Tile>> adjacencyMap, Map<String, List<AbstractPropertyTile>> colorListMap, Tile go, int nDie, Bank bank) {
        myPlayerList = playerList;
        this.adjacencyMap = adjacencyMap;
        propertyCategoryToSpecificListMap = colorListMap;
        playerPositionMap = new HashMap<>();
        numDie = nDie;
        for (AbstractPlayer p : playerList) playerPositionMap.put(p, go);
        this.bank = bank;
    }

    /**
     * gets the tile that the player is currently on
     */
    public Tile getPlayerTile(AbstractPlayer p) {
        return playerPositionMap.get(p);
    }

    /**
     * Moves the player on the board by reassigning its tile mapping
     */
    public abstract void movePlayer(AbstractPlayer p, int numMoves);

    public abstract void movePlayer(AbstractPlayer p, Tile tile);


    public Map<AbstractPlayer, Tile> getPlayerTileMap() {
        return playerPositionMap;
    }

    public List<Tile> getAdjacentTiles(Tile tile) {
        return adjacencyMap.get(tile);
    }

    public Tile getJailTile(){
        Tile tile = null;
        for (Tile key: adjacencyMap.keySet()) {
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

    public Bank getBank(){
        return bank;
    }

//    public List<AbstractPropertyTile> getAllPropertiesOfSameCategoryAs(AbstractPropertyTile property){
//        List<AbstractPropertyTile> allOfCategory = new ArrayList<AbstractPropertyTile>(  );
//        String targetCategory = property.getCard().getCategory();
//        for()
//        return allOfCategory;
//    }
}
