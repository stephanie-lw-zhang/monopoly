package FrontEnd;

import BackEnd.Board.AbstractBoard;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class BoardView {
    private AbstractBoard myModel;
    private AnchorPane myRoot;
    private double myScreenWidth, myScreenHeight;
    private int myHorizontals, myVerticals;
    private double myTileHeight;

    public BoardView(double screenWidth, double screenHeight, double tileHeight, int horizontalTiles, int verticalTiles){
        myRoot = new AnchorPane();
        myRoot.setMaxWidth(screenWidth);
        myRoot.setMaxHeight(screenHeight);
        myScreenHeight = screenHeight;
        myScreenWidth = screenWidth;
        myTileHeight = tileHeight;
        myHorizontals = horizontalTiles;
        myVerticals = verticalTiles;
        makeBoard();
        //myModel = board;

    }

    private double calculateTileWidth(double sideLength, double totalTiles){
        return (sideLength-myTileHeight*2)/(totalTiles-2);
    }

    public Pane getBoardPane(){ return myRoot; }

    public void setUp(){

    }

    public void makeBoard(){
        makeCorners();
        placePropertyTile("test","test","BLUE",1,1,myHorizontals,myScreenWidth);
        placePropertyTile("test","test","BLUE",2,1,myHorizontals,myScreenWidth);
        placePropertyTile("test","test","BLUE",3,1,myHorizontals,myScreenWidth);
        placePropertyTile("test","test","BLUE",4,1,myHorizontals,myScreenWidth);
        placePropertyTile("test","test","BLUE",5,1,myHorizontals,myScreenWidth);
        placePropertyTile("test","test","BLUE",6,1,myHorizontals,myScreenWidth);
        placePropertyTile("test","test","BLUE",7,1,myHorizontals,myScreenWidth);
        placePropertyTile("test","test","BLUE",8,1,myHorizontals,myScreenWidth);
    }

    public void placePropertyTile(String tileName,
                                  String tileDescription,
                                  String tileColor,
                                  int xoffset,
                                  int yoffset,
                                  int totalTiles,
                                  double sideLength){
        var tile = new RectangularTileView(tileName,tileDescription,tileColor);
        var height = myTileHeight;
        var width = calculateTileWidth(sideLength,totalTiles);
        tile.makeTileViewNode(new double[]{width,height});
        Node tileNode = tile.getNodeOfTileView();
        myRoot.setTopAnchor(tileNode, myScreenHeight-height*yoffset);
        myRoot.setLeftAnchor(tileNode, myScreenWidth-width*xoffset-myTileHeight);
        myRoot.getChildren().add(tileNode);
    }

    public void placeCornerTile(String tileName,
                                String tileDescription,
                                String tileColor,
                                double xDiff,
                                double yDiff){
        var width = myTileHeight;
        var height = myTileHeight;
        var tile = new CornerTileView(tileName,tileDescription,tileColor);
        tile.makeTileViewNode(new double[]{width,height});
        Node tileNode = tile.getNodeOfTileView();
        myRoot.setTopAnchor(tileNode, (myScreenHeight-height)*yDiff);
        myRoot.setLeftAnchor(tileNode, (myScreenWidth-width)*xDiff);
        myRoot.getChildren().add(tileNode);
    }

    public void makeCorners(){
        placeCornerTile("Go","Go","clear",1,1);
        placeCornerTile("Jail","Go","clear",0,1);
        placeCornerTile("Parking","Go","clear",0,0);
        placeCornerTile("PoPo","Go","clear",1,0);
    }
}
