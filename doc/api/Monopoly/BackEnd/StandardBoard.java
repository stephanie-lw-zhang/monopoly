package api.Monopoly.BackEnd;

import java.util.List;

public class StandardBoard extends AbstractBoard {

    public StandardBoard(List<AbstractPlayer> playerList, List<AbstractTile> tiles, List<List<AbstractTile>> adjacencyList){
        super(playerList, tiles, adjacencyList);
    }

    public void movePlayer(AbstractPlayer p, int[] rolls, boolean jailed) {
        if(rolls[0] != rolls[1] && jailed) return;
        getPlayerTileMap().remove(p);
        AbstractTile tile = getPlayerTile(p);
        int index = getTileIndex(tile);
        AbstractTile next = null;
        for(int i = 0; i<rolls[0]+rolls[1]; i++){
            next = getAdjacentTiles(index).get(0);
            index = getTileIndex(next);
            checkIfGo(p, next);
        }
        getPlayerTileMap().put(p, next);
    }

}
