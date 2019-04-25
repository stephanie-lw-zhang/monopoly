package backend.board;

/**
 * This class extends AbstractBoard and represents the traditional
 * format of a Monopoly board
 */

import backend.assetholder.AbstractPlayer;
import backend.assetholder.Bank;
import exception.MultiplePathException;
import exception.TileNotFoundException;
import backend.tile.AbstractPropertyTile;
import backend.tile.Tile;
import configuration.XMLData;

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

    public void movePlayer(AbstractPlayer p, int numMoves) throws MultiplePathException{
            for(int i = 0; i < numMoves; i++){
                movePlayerByOne( p );
            }

    }

    public void movePlayerByOne (AbstractPlayer p) throws MultiplePathException{
        Tile tile = getPlayerTile(p);
        Tile next;
        //this needs to change for a non-standard board, could be informed by property file
        if(getAdjacentTiles(tile).size() == 1){
            next = getAdjacentTiles(tile).get(0);
            tile = next;
            tile.applyPassedAction(p);
            try {
                if(tile.isGoToJailTile()) tile = getJailTile();
                getPlayerTileMap().put(p, tile);
            } catch (TileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            throw new MultiplePathException( "There are multiple paths, please choose one" );
        }

    }

    @Override
    public void movePlayer(AbstractPlayer p, Tile tile) {
        getPlayerTileMap().put(p, tile);
    }
}

