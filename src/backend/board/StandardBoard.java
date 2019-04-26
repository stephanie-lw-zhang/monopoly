package backend.board;

/**
 * This class extends AbstractBoard and represents the traditional
 * format of a Monopoly board
 */

import backend.assetholder.AbstractPlayer;
import backend.assetholder.Bank;
import exceptions.MultiplePathException;
import exceptions.TileNotFoundException;
import backend.tile.AbstractPropertyTile;
import backend.tile.Tile;
import configuration.XMLData;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardBoard extends AbstractBoard {

    public StandardBoard(List<AbstractPlayer> playerList,
                         Map<Tile, List<Tile>> adjacencyMap,
                         Map<String, List<AbstractPropertyTile>> colorListMap,
                         Tile go,
                         Bank bank) {
        super(playerList, adjacencyMap, colorListMap, go, 2, bank);
    }

    public StandardBoard(List<AbstractPlayer> playerList, XMLData data){
        super(playerList, data);
    }

    public Map<Tile, List<String>> movePlayer(AbstractPlayer p, int numMoves) throws MultiplePathException{
        Map<Tile, List<String>> passedTileActions = new HashMap<>();
        for(int i = 0; i < numMoves; i++){
            Map.Entry<Tile,List<String>> passedTileAction = movePlayerByOne( p );
            if (passedTileAction!=null) {
                if (!passedTileActions.containsKey(passedTileAction.getKey())) {
                    passedTileActions.put(passedTileAction.getKey(),passedTileAction.getValue());
                }
                else {
                    for (String str : passedTileAction.getValue()) {
                        passedTileActions.get(passedTileAction.getKey()).add(str);
                    }
                }
            }
        }
        return passedTileActions;
    }

    public Map.Entry<Tile,List<String>> movePlayerByOne (AbstractPlayer p) throws MultiplePathException{
        Map.Entry<Tile,List<String>> passedTileAction = null;
        Tile tile = getPlayerTile(p);
        Tile next;
        //this needs to change for a non-standard board, could be informed by property file
        if(getAdjacentTiles(tile).size() == 1){
            next = getAdjacentTiles(tile).get(0);
            tile = next;
            if (tile.applyPassedAction(p).size() > 0) {
                passedTileAction = new AbstractMap.SimpleEntry<>(tile,tile.applyPassedAction(p));
            }
            tile.applyPassedAction(p);
            try {
                if(tile.isGoToJailTile()) tile = getJailTile();
                getPlayerTileMap().put(p, tile);
            } catch (TileNotFoundException e) {
                e.popUp();
            }
        } else {
            throw new MultiplePathException( "There are multiple paths, please choose one" );
        }
        return passedTileAction;
    }

    @Override
    public void movePlayer(AbstractPlayer p, Tile tile) {
        getPlayerTileMap().put(p, tile);

    }
}

