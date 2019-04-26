package backend.board;

/**
 * This class extends AbstractBoard and represents the traditional
 * format of a Monopoly board
 */

import backend.assetholder.AbstractPlayer;
import backend.assetholder.Bank;
import backend.tile.BuildingTile;
import exceptions.MultiplePathException;
import exceptions.TileNotFoundException;
import backend.tile.AbstractPropertyTile;
import backend.tile.Tile;
import configuration.XMLData;

import java.util.*;

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

    public List<Tile> movePlayer(AbstractPlayer p, int numMoves) throws MultiplePathException{
        List<Tile> passedTiles = new ArrayList<>();
        for(int i = 0; i < numMoves; i++){
            Tile passedTile = movePlayerByOne( p );
            if (passedTile != null && i != numMoves-1) {
                passedTiles.add(passedTile);
            }
        }
        return passedTiles;
    }

    public Tile movePlayerByOne (AbstractPlayer p) throws MultiplePathException{
        Tile passedTile = null;
        Tile tile = getPlayerTile(p);
        Tile next;
        //this needs to change for a non-standard board, could be informed by property file
        if(getAdjacentTiles(tile).size() == 1){
            next = getAdjacentTiles(tile).get(0);
            tile = next;
            if (tile.applyPassedAction(p).size() > 0) {
                passedTile = tile;
            }
        } else {
            throw new MultiplePathException( "There are multiple paths, please choose one" );
        }
        return passedTile;
    }

    @Override
    public void movePlayer(AbstractPlayer p, Tile tile) {
        getPlayerTileMap().put(p, tile);

    }
}

