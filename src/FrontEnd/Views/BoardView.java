package FrontEnd.Views;

import BackEnd.Board.AbstractBoard;
import Configuration.ImportPropertyFile;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class BoardView {
    private AbstractBoard myModel;
    private AnchorPane myRoot;
    private double myScreenWidth, myScreenHeight;
    private int myHorizontals, myVerticals;
    private double myTileHeight;

    private ImportPropertyFile myPropertyFile;

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

    public BoardView(double screenWidth, double screenHeight, double tileHeight, int horizontalTiles, int verticalTiles, ImportPropertyFile propertyFile){
        myRoot = new AnchorPane();
        myRoot.setMaxWidth(screenWidth);
        myRoot.setMaxHeight(screenHeight);
        myScreenHeight = screenHeight;
        myScreenWidth = screenWidth;
        myTileHeight = tileHeight;
        myHorizontals = horizontalTiles;
        myVerticals = verticalTiles;
        myPropertyFile = propertyFile;
        makeBoard();
        //myModel = board;

    }

    private double calculateTileWidth(double sideLength, double totalTiles){
        return (double) (sideLength-myTileHeight*2)/(totalTiles-2);
    }

    public Pane getBoardPane(){ return myRoot; }

    public void setUp(){

    }

    public void makeBoard(){
        makeCorners();
        makeBottomRow();
        makeLeftRow();
        makeTopRow();
        makeRightRow();
        placeDeck("Community Chest","",100,50,0.6);
        placeDeck("Chance","",100,50,0.25);
    }

    private void makeBottomRow() {
        placePropertyTile(myPropertyFile.getProp("Tile1Name"),"test", Color.BROWN,1,1,myHorizontals,myScreenWidth,0);
        placeNonPropertyTile(myPropertyFile.getProp("Tile2Name"),"",2,1,myHorizontals,myScreenWidth,0);
        placePropertyTile(myPropertyFile.getProp("Tile3Name"),"test",Color.BROWN,3,1,myHorizontals,myScreenWidth,0);
        placeNonPropertyTile(myPropertyFile.getProp("Tile4Name"),"",4,1,myHorizontals,myScreenWidth,0);
        placePropertyTile(myPropertyFile.getProp("Tile5Name"),"",Color.BLACK ,5,1,myHorizontals,myScreenWidth,0);
        placePropertyTile(myPropertyFile.getProp("Tile6Name"),"test",Color.AZURE,6,1,myHorizontals,myScreenWidth,0);
        placeNonPropertyTile(myPropertyFile.getProp("Tile7Name"),"",7,1,myHorizontals,myScreenWidth,0);
        placePropertyTile(myPropertyFile.getProp("Tile8Name"),"test",Color.AZURE,8,1,myHorizontals,myScreenWidth,0);
        placePropertyTile(myPropertyFile.getProp("Tile9Name"),"test",Color.AZURE,9,1,myHorizontals,myScreenWidth,0);
    }

    private void makeLeftRow(){
        placePropertyTile(myPropertyFile.getProp("Tile11Name"),"test",Color.DEEPPINK,9,1,myVerticals,myScreenHeight,90);
        placePropertyTile(myPropertyFile.getProp("Tile12Name"),"",Color.BEIGE,9,2,myVerticals,myScreenHeight,90);
        placePropertyTile(myPropertyFile.getProp("Tile13Name"),"test",Color.DEEPPINK,9,3 ,myVerticals,myScreenHeight,90);
        placePropertyTile(myPropertyFile.getProp("Tile14Name"),"test",Color.DEEPPINK,9,4,myVerticals,myScreenHeight,90);
        placePropertyTile(myPropertyFile.getProp("Tile15Name"),"",Color.BLACK,9,5,myVerticals,myScreenHeight,90);
        placePropertyTile(myPropertyFile.getProp("Tile16Name"),"",Color.ORANGE,9,6,myVerticals,myScreenHeight,90);
        placeNonPropertyTile(myPropertyFile.getProp("Tile17Name"),"",9,7,myVerticals,myScreenHeight,90);
        placePropertyTile(myPropertyFile.getProp("Tile18Name"),"",Color.ORANGE,9,8,myVerticals,myScreenHeight,90);
        placePropertyTile(myPropertyFile.getProp("Tile19Name"),"",Color.ORANGE,9,9,myVerticals,myScreenHeight,90);
    }

    private void makeTopRow() {
        placePropertyTile(myPropertyFile.getProp("Tile21Name"),"test", Color.RED,1,1,myHorizontals,myScreenWidth,180);
        placeNonPropertyTile(myPropertyFile.getProp("Tile22Name"),"",2,1,myHorizontals,myScreenWidth,180);
        placePropertyTile(myPropertyFile.getProp("Tile23Name"),"test",Color.RED,3,1,myHorizontals,myScreenWidth,180);
        placePropertyTile(myPropertyFile.getProp("Tile24Name"),"test",Color.RED,4,1,myHorizontals,myScreenWidth,180);
        placePropertyTile(myPropertyFile.getProp("Tile25Name"),"",Color.BLACK,5,1,myHorizontals,myScreenWidth,180);
        placePropertyTile(myPropertyFile.getProp("Tile26Name"),"test",Color.YELLOW,6,1,myHorizontals,myScreenWidth,180);
        placePropertyTile(myPropertyFile.getProp("Tile27Name"),"test",Color.YELLOW,7,1,myHorizontals,myScreenWidth,180);
        placePropertyTile(myPropertyFile.getProp("Tile28Name"),"",Color.BEIGE,8,1,myHorizontals,myScreenWidth,180);
        placePropertyTile(myPropertyFile.getProp("Tile29Name"),"test",Color.YELLOW,9,1,myHorizontals,myScreenWidth,180);

    }

    private void makeRightRow(){
        placePropertyTile(myPropertyFile.getProp("Tile31Name"),"",Color.GREEN,1,1,myVerticals,myScreenHeight,270);
        placePropertyTile(myPropertyFile.getProp("Tile32Name"),"",Color.GREEN,2,2,myVerticals,myScreenHeight,270);
        placeNonPropertyTile(myPropertyFile.getProp("Tile33Name"),"",3,3,myVerticals,myScreenHeight,270);
        placePropertyTile(myPropertyFile.getProp("Tile34Name"),"",Color.GREEN,4,4,myVerticals,myScreenHeight,270);
        placePropertyTile(myPropertyFile.getProp("Tile35Name"),"",Color.BLACK,5,5,myVerticals,myScreenHeight,270);
        placeNonPropertyTile(myPropertyFile.getProp("Tile36Name"),"",6,6,myVerticals,myScreenHeight,270);
        placePropertyTile(myPropertyFile.getProp("Tile37Name"),"",Color.BLUE,7,7,myVerticals,myScreenHeight,270);
        placeNonPropertyTile(myPropertyFile.getProp("Tile38Name"),"",8,8,myVerticals,myScreenHeight,270);
        placePropertyTile(myPropertyFile.getProp("Tile39Name"),"",Color.BLUE,9,9,myVerticals,myScreenHeight,270);
    }


    public void placeDeck(String tileName,
                                  String tileDescription,
                                  double width, double length, double prop){
        var tile = new NormalDeckView(tileName,tileDescription,"");
        var height = length;
        tile.makeTileViewNode(new double[]{width,height});
        Node tileNode = tile.getNodeOfTileView();
        tileNode.setRotate(-45);
        myRoot.setTopAnchor(tileNode,myScreenHeight*prop);
        myRoot.setLeftAnchor(tileNode,myScreenWidth*prop);
        myRoot.getChildren().add(tileNode);
    }

    public void placePropertyTile(String tileName,
                                  String tileDescription,
                                  Paint tileColor,
                                  int xoffset,
                                  int yoffset,
                                  int totalTiles,
                                  double sideLength,double rotationAngle){
        var tile = new PropertyTileView(tileName,tileDescription,tileColor);
        var height = myTileHeight;
        var width = calculateTileWidth(sideLength,totalTiles);
        tile.makeTileViewNode(new double[]{width,height});
        Node tileNode = tile.getNodeOfTileView();
        if(rotationAngle==0) {
            myRoot.setTopAnchor(tileNode, myScreenHeight - height * yoffset);
            myRoot.setLeftAnchor(tileNode, myScreenWidth - width * xoffset - myTileHeight);
        } else if (rotationAngle==90){
            myRoot.setTopAnchor(tileNode, myScreenHeight-myTileHeight*1.5-width*(yoffset-0.5));
            myRoot.setLeftAnchor(tileNode, (height-width)/2);
        } else if (rotationAngle==180){
            myRoot.setTopAnchor(tileNode,0.0);
            myRoot.setLeftAnchor(tileNode,myTileHeight/2+width*xoffset);
        } else if (rotationAngle==270){
            myRoot.setTopAnchor(tileNode, width*(yoffset+0.5));
            myRoot.setRightAnchor(tileNode,width/2);
        }
        tileNode.setRotate(rotationAngle);

        myRoot.getChildren().add(tileNode);
    }

    public void placeNonPropertyTile(String tileName,
    String tileDescription,
    int xoffset,
    int yoffset,
    int totalTiles,
    double sideLength,double rotationAngle){
        var tile = new RectangularTileView(tileName,tileDescription,"");
        var height = myTileHeight;
        var width = calculateTileWidth(sideLength,totalTiles);
        tile.makeTileViewNode(new double[]{width,height});
        Node tileNode = tile.getNodeOfTileView();
        if(rotationAngle==0) {
            myRoot.setTopAnchor(tileNode, myScreenHeight - height * yoffset);
            myRoot.setLeftAnchor(tileNode, myScreenWidth - width * xoffset - myTileHeight);
        } else if (rotationAngle==90){
            myRoot.setTopAnchor(tileNode, myScreenHeight-myTileHeight*1.5-width*(yoffset-0.5));
            myRoot.setLeftAnchor(tileNode, (height-width)/2);
        } else if (rotationAngle==180){
            myRoot.setTopAnchor(tileNode,0.0);
            myRoot.setLeftAnchor(tileNode,myTileHeight/2+width*xoffset);
        } else if (rotationAngle==270){
            myRoot.setTopAnchor(tileNode, width*(yoffset+0.5));
            myRoot.setRightAnchor(tileNode,width/2);
        }
        tileNode.setRotate(rotationAngle);
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
        placeCornerTile(myPropertyFile.getProp("Tile0Name"),"Go","clear",1,1);
        placeCornerTile(myPropertyFile.getProp("Tile10Name"),"Go","clear",0,1);
        placeCornerTile(myPropertyFile.getProp("Tile20Name"),"Go","clear",0,0);
        placeCornerTile(myPropertyFile.getProp("Tile30Name"),"Go","clear",1,0);
    }
}
