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
import backend.deck.DeckInterface;
import backend.dice.AbstractDice;
import backend.exceptions.TileNotFoundException;
import backend.tile.*;
import configuration.XMLData;

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
    private Bank                                    bank;
    private List<DeckInterface> myDecks;
    private AbstractDice myDice;

    /**
     * Constructor that takes in the list of players, tiles, and an adjacency list for the graph of tiles
     */
    public AbstractBoard(List<AbstractPlayer> playerList, Map<Tile, List<Tile>> adjMap, Map<String, List<AbstractPropertyTile>> colorListMap, Tile go, int nDie, Bank bnk) {
        myPlayerList = playerList;
        adjacencyMap = adjMap;
        propertyCategoryToSpecificListMap = colorListMap;
        numDie = nDie;
        bank = bnk;
        playerPositionMap = new HashMap<>();
        for (AbstractPlayer p : playerList) playerPositionMap.put(p, go);
    }

    public AbstractBoard(List<AbstractPlayer> playerList, XMLData data){
        myPlayerList = playerList;
        adjacencyMap = data.getAdjacencyList();
        propertyCategoryToSpecificListMap = data.getPropertyCategoryMap();
        numDie = data.getNumDie();
        bank = data.getBank();
        playerPositionMap = new HashMap<>();
        for (AbstractPlayer p : playerList) playerPositionMap.put(p, data.getFirstTile());
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
        //System.out.println(adjacencyMap.get(backend.tile.GoTile@746de32));
        return adjacencyMap.get(tile);
    }

    public JailTile getJailTile() throws TileNotFoundException {
        String methodName = String.valueOf(new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getReturnType());

        for (Tile key : adjacencyMap.keySet()) {
            try {
                if (key.isJailTile()) {
                    return (JailTile) key;
                } else {
                    throw new TileNotFoundException("Could not find Tile of type " + methodName);
                }
            } catch (TileNotFoundException e) {
                e.printStackTrace();
                return (JailTile) key;
            }
        }
        return null;
    }

    public GoTile getGoTile(){
        for (Tile key: adjacencyMap.keySet()) {
            if(key.isGoTile()){
                return (GoTile) key;
            }
        }
        return null;
    }

    public List<AbstractPlayer> getMyPlayerList() { return myPlayerList; }

    public Map<String, List<AbstractPropertyTile>> getColorListMap() {
        return propertyCategoryToSpecificListMap;
    }

    public int getNumDie() { return numDie; }

    public Bank getBank(){
        return bank;
    }

    public Tile getTilesIndex(int i){
        for(Tile t: adjacencyMap.keySet()){
            if(t.getTileIndex() == i) return t;
        }
        return null; //change this !!!
    }

    public AbstractPlayer getPlayerFromName(String name) {
        for(AbstractPlayer p: myPlayerList){
            if (p.getMyPlayerName().equalsIgnoreCase( name )){
                return p;
            }
        }
        return null;
        //THROW EXCEPTION "THIS PLAYER DOES NOT EXIST"
    }

    public List<String> getPlayerNamesAsStrings() {
        List<String> playerStrings = new ArrayList<>();
        for (AbstractPlayer p : myPlayerList) {
            playerStrings.add(p.getMyPlayerName());
        }
        return playerStrings;
    }

    public List<String> getPropertyTileNamesAsStrings(AbstractPlayer owner) {
        List<String> tileStrings = new ArrayList<>();
        for (AbstractPropertyTile t : owner.getProperties()) {
            tileStrings.add(t.getTitleDeed());
        }
        return tileStrings;
    }

    public List<String> getBuildingTileNamesAsStrings(AbstractPlayer owner) {
        List<String> tileStrings = new ArrayList<>();
        for (AbstractPropertyTile t : owner.getProperties()) {
            if (t.isBuildingTile()) {
                tileStrings.add(t.getTitleDeed());
            }
        }
        return tileStrings;
    }

    public Tile getPropertyTileFromName(String name) {
        for (Tile t : adjacencyMap.keySet()) {
            if (t.isPropertyTile()) {
                AbstractPropertyTile property = (AbstractPropertyTile)t;
                if (property.getTitleDeed().equalsIgnoreCase(name)) {
                    return property;
                }
            }
        }
        return null;
    }

    public List<AbstractPropertyTile> getSameSetProperties(AbstractPropertyTile property) {
        return getColorListMap().get( property.getCard().getCategory());
    }

//    public List<AbstractPropertyTile> getAllPropertiesOfSameCategoryAs(AbstractPropertyTile property){
//        List<AbstractPropertyTile> allOfCategory = new ArrayList<AbstractPropertyTile>(  );
//        String targetCategory = property.getCard().getCategory();
//        for()
//        return allOfCategory;
//    }
}
