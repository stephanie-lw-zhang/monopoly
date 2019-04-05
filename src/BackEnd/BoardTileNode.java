package BackEnd;

import BackEnd.Tile.TileInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a node used in the graph
 * to represent the network of tiles on the game board
 *
 * @author Sam
 * @author Edward
 */
public class BoardTileNode {
    TileInterface tileInfo;
    List<BoardTileNode> nexts;

    public BoardTileNode(TileInterface tInfo, List<BoardTileNode> n) {
        tileInfo = tInfo;
        nexts = n;
    }

    public BoardTileNode(TileInterface tInfo) {
        this(tInfo, new ArrayList<BoardTileNode>());
    }

    public void addNext(BoardTileNode n) {
        nexts.add(n);
    }
}