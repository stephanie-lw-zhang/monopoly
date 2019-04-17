package FrontEnd.Views.Board;

import BackEnd.Board.AbstractBoard;
import FrontEnd.Views.Board.BoardComponents.CornerTileView;
import FrontEnd.Views.Board.BoardComponents.NormalDeckView;
import FrontEnd.Views.Board.BoardComponents.PropertyTileView;
import FrontEnd.Views.Board.BoardComponents.RectangularTileView;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class RectangularBoardView extends AbstractBoardView{
    private AbstractBoard myModel;
    private AnchorPane myRoot;
    private double myScreenWidth, myScreenHeight;
    private int myHorizontals, myVerticals;
    private double myTileHeight;

    public RectangularBoardView(double screenWidth, double screenHeight, double tileHeight, int horizontalTiles, int verticalTiles){
        super(screenWidth,screenHeight);
        myTileHeight = tileHeight;
        myHorizontals = horizontalTiles;
        myVerticals = verticalTiles;
        makeBoard();
        makeBackground();
    }

    private void makeBackground() {
        BackgroundFill boardBackgroundFill = new BackgroundFill(Color.rgb(220,237,200), CornerRadii.EMPTY, Insets.EMPTY);
        Background boardBackGround = new Background(boardBackgroundFill);
        myRoot.setBackground(boardBackGround);
    }

    private double calculateTileWidth(double sideLength, double totalTiles){
        return (double) (sideLength-myTileHeight*2)/(totalTiles-2);
    }

    @Override
    public void setRoot() {
        myRoot = new AnchorPane();
    }

    @Override
    public void setScreenLimits(double screenWidth, double screenHeight) {
        myScreenHeight = screenHeight;
        myScreenWidth = screenWidth;
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

    @Override
    public Pane getPane() {
        return myRoot;
    }

    private void makeBottomRow() {
        placePropertyTile("Mediterranean Avenue","test", Color.BROWN,1,1,myHorizontals,myScreenWidth,0);
        placeNonPropertyTile("Community Chest","",2,1,myHorizontals,myScreenWidth,0);
        placePropertyTile("Baltic Avenue","test",Color.BROWN,3,1,myHorizontals,myScreenWidth,0);
        placeNonPropertyTile("Income Tax","",4,1,myHorizontals,myScreenWidth,0);
        placePropertyTile("Reading Railroad","",Color.BLACK ,5,1,myHorizontals,myScreenWidth,0);
        placePropertyTile("Oriental Avenue","test",Color.AZURE,6,1,myHorizontals,myScreenWidth,0);
        placeNonPropertyTile("Chance","",7,1,myHorizontals,myScreenWidth,0);
        placePropertyTile("Vermont Avenue","test",Color.AZURE,8,1,myHorizontals,myScreenWidth,0);
        placePropertyTile("Connecticut Avenue","test",Color.AZURE,9,1,myHorizontals,myScreenWidth,0);
    }

    private void makeLeftRow(){
        placePropertyTile("St. Charles Place","test",Color.DEEPPINK,9,1,myVerticals,myScreenHeight,90);
        placePropertyTile("Electric Company","",Color.BEIGE,9,2,myVerticals,myScreenHeight,90);
        placePropertyTile("States Avenue","test",Color.DEEPPINK,9,3 ,myVerticals,myScreenHeight,90);
        placePropertyTile("Virginia Avenue","test",Color.DEEPPINK,9,4,myVerticals,myScreenHeight,90);
        placePropertyTile("Pennsylvania Railroad","",Color.BLACK,9,5,myVerticals,myScreenHeight,90);
        placePropertyTile("St. James Place","",Color.ORANGE,9,6,myVerticals,myScreenHeight,90);
        placeNonPropertyTile("Community Chest","",9,7,myVerticals,myScreenHeight,90);
        placePropertyTile("Tennessee Avenue","",Color.ORANGE,9,8,myVerticals,myScreenHeight,90);
        placePropertyTile("New York Avenue","",Color.ORANGE,9,9,myVerticals,myScreenHeight,90);
    }

    private void makeTopRow() {
        placePropertyTile("Kentucky Avenue","test", Color.RED,1,1,myHorizontals,myScreenWidth,180);
        placeNonPropertyTile("Chance","",2,1,myHorizontals,myScreenWidth,180);
        placePropertyTile("Indiana Avenue","test",Color.RED,3,1,myHorizontals,myScreenWidth,180);
        placePropertyTile("Illinois Avenue","test",Color.RED,4,1,myHorizontals,myScreenWidth,180);
        placePropertyTile("B&O Railroad","",Color.BLACK,5,1,myHorizontals,myScreenWidth,180);
        placePropertyTile("Atlantic Avenue","test",Color.YELLOW,6,1,myHorizontals,myScreenWidth,180);
        placePropertyTile("Ventnor Avenue","test",Color.YELLOW,7,1,myHorizontals,myScreenWidth,180);
        placePropertyTile("Water Works","",Color.BEIGE,8,1,myHorizontals,myScreenWidth,180);
        placePropertyTile("Marvin Gardens","test",Color.YELLOW,9,1,myHorizontals,myScreenWidth,180);

    }

    private void makeRightRow(){
        placePropertyTile("Pacific Avenue","",Color.GREEN,1,1,myVerticals,myScreenHeight,270);
        placePropertyTile("North Carolina Avenue","",Color.GREEN,2,2,myVerticals,myScreenHeight,270);
        placeNonPropertyTile("Community Chest","",3,3,myVerticals,myScreenHeight,270);
        placePropertyTile("Pennsylvania Avenue","",Color.GREEN,4,4,myVerticals,myScreenHeight,270);
        placePropertyTile("Short Line","",Color.BLACK,5,5,myVerticals,myScreenHeight,270);
        placeNonPropertyTile("Community Chest","",6,6,myVerticals,myScreenHeight,270);
        placePropertyTile("Park Place","",Color.BLUE,7,7,myVerticals,myScreenHeight,270);
        placeNonPropertyTile("Luxury Tax","",8,8,myVerticals,myScreenHeight,270);
        placePropertyTile("Boardwalk","",Color.BLUE,9,9,myVerticals,myScreenHeight,270);
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
        tileNode.setRotate(rotationAngle);
        if(rotationAngle==0) {
            myRoot.setTopAnchor(tileNode, myScreenHeight - height * yoffset);
            myRoot.setLeftAnchor(tileNode, myScreenWidth - width * xoffset - myTileHeight);
        } else if (rotationAngle==90){
            myRoot.setTopAnchor(tileNode, myScreenHeight-myTileHeight*1.5-width*(yoffset-0.5));
            myRoot.setLeftAnchor(tileNode, (height-width)/2);
        } else if (rotationAngle==180){
            myRoot.setTopAnchor(tileNode,0.0);
            myRoot.setLeftAnchor(tileNode,myTileHeight+width*(xoffset-1));
        } else if (rotationAngle==270){
            myRoot.setTopAnchor(tileNode, myTileHeight+width*(yoffset-1)-(height-width)/2);
            myRoot.setLeftAnchor(tileNode,myScreenWidth-myTileHeight/2-width/2);
        }
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
        tileNode.setRotate(rotationAngle);

        if(rotationAngle==0) {
            myRoot.setTopAnchor(tileNode, myScreenHeight - height * yoffset);
            myRoot.setLeftAnchor(tileNode, myScreenWidth - width * xoffset - myTileHeight);
        } else if (rotationAngle==90){
            myRoot.setTopAnchor(tileNode, myScreenHeight-myTileHeight*1.5-width*(yoffset-0.5));
            myRoot.setLeftAnchor(tileNode, (height-width)/2);
        } else if (rotationAngle==180){
            myRoot.setTopAnchor(tileNode,0.0);
            myRoot.setLeftAnchor(tileNode,myTileHeight+width*(xoffset-1));
        } else if (rotationAngle==270){
            myRoot.setTopAnchor(tileNode, myTileHeight+width*(yoffset-1)-(height-width)/2);
            myRoot.setLeftAnchor(tileNode,myScreenWidth-myTileHeight/2-width/2);
        }
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
        System.out.println(tileName);
    }

    public void makeCorners(){
        placeCornerTile("Go","Go","clear",1,1);
        placeCornerTile("Jail","Go","clear",0,1);
        placeCornerTile("Parking","Go","clear",0,0);
        placeCornerTile("PoPo","Go","clear",1,0);
    }
}
