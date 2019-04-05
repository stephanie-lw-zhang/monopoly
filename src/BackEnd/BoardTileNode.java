package BackEnd;

import BackEnd.Board.AbstractTile;

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
    AbstractTile tileInfo;
    List<BoardTileNode> nexts;

    public BoardTileNode(AbstractTile tInfo, List<BoardTileNode> n) {
        tileInfo = tInfo;
        nexts = n;
    }

    public BoardTileNode(AbstractTile tInfo) {
        this(tInfo, new ArrayList<BoardTileNode>());
    }

    public void addNext(BoardTileNode n) {
        nexts.add(n);
    }
}