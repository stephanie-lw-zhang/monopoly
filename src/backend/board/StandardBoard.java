package backend.board;

/**
 * This class extends AbstractBoard and represents the traditional
 * format of a Monopoly board
 */

import backend.assetholder.AbstractPlayer;
import backend.assetholder.Bank;
import backend.exceptions.TileNotFoundException;
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

    public void movePlayer(AbstractPlayer p, int numMoves) {
            Tile tile = getPlayerTile(p);
            Tile next;
            for(int i = 0; i < numMoves; i++){
                //this needs to change for a non-standard board, could be informed by property file
                next = getAdjacentTiles(tile).get(0);
                tile = next;
                if (tile.applyPassedAction(p) != null) {
                    //tile.applyPassedAction(p);
                }
            }
            try {
                if(tile.isGoToJailTile()) tile = getJailTile();
                getPlayerTileMap().put(p, tile);
            } catch (TileNotFoundException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void movePlayer(AbstractPlayer p, Tile tile) {
        getPlayerTileMap().put(p, tile);
    }
}

