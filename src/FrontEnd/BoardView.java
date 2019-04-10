package FrontEnd;

import BackEnd.Board.AbstractBoard;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class BoardView {
    private AbstractBoard myModel;
    private AnchorPane myRoot;

    public BoardView(AbstractBoard board){
        makeBoard();
        myModel = board;
        myRoot = new AnchorPane();
    }

    public Node getBoardNode(){ return myRoot; }

    public void setUp(){

    }

    public void makeBoard(){
        placeTile("test","test","BLUE");
    }

    public void placeTile(String tileName, String tileDescription, String tileColor){
        var tile = new RectangularTileView(tileName,tileDescription,tileColor);
        tile.makeTileViewNode(new double[]{100,100});
        myRoot.getChildren().add(tile.getNodeOfTileView());
    }
}
