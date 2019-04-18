package FrontEnd.Views.Board;

import BackEnd.Board.AbstractBoard;
import FrontEnd.Views.Board.BoardComponents.*;
import FrontEnd.Views.IconView;
import javafx.application.Platform;
import javafx.geometry.Insets;
import Configuration.ImportPropertyFile;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import javax.sound.midi.ControllerEventListener;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class RectangularBoardView extends AbstractBoardView{
    private AbstractBoard myModel;
    private AnchorPane myRoot;
    private double myScreenWidth, myScreenHeight;
    private int myHorizontals, myVerticals;
    private double myTileHeight;
    private ImportPropertyFile myPropertyFile;
    private ImportPropertyFile details;
    private List<AbstractTileView> myTiles = new ArrayList<>();
    private int myIndex=0;
    private IconView myIcon;
    private int myTimes;

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

    public RectangularBoardView(double screenWidth, double screenHeight, double tileHeight, int horizontalTiles, int verticalTiles, ImportPropertyFile propertyFile){
        super(screenWidth,screenHeight);
        myTileHeight = tileHeight;
        myHorizontals = horizontalTiles;
        myVerticals = verticalTiles;
        myPropertyFile=propertyFile;
        //System.out.print(myPropertyFile);

        //System.out.print(myPropertyFile.getProp("TileOName"));
        makeBoard();
        makeBackground();
//        myTiles.sort(new Comparator<AbstractTileView>() {
//            @Override
//            public int compare(AbstractTileView o1, AbstractTileView o2) {
//                return o1.getMyTileName().compareTo(o2.getMyTileName());
//            }
//        });
        myIcon = new IconView(new Image(this.getClass().getClassLoader().getResourceAsStream("boot.png")));
        myIcon.setHeight(myTileHeight/2);
        myIcon.setWidth(myTileHeight/2);

//        Thread updateThread = new Thread(() -> {
//            while (true) {
//                try {
//                    Thread.sleep(1000);
//                    Platform.runLater(() -> this.movePieceDemo(test));
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//        updateThread.setDaemon(true);
//        updateThread.start();
        //myModel = board;

    }

    private void movePieceDemo(IconView test) {
        if(myTimes>0) {
            if (myIndex >= myTiles.size()) {
                myIndex = 0;
            }
            myTiles.get(myIndex).moveTo(test.getMyNode());
            myIndex++;
            myTimes--;
        }
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
        placePropertyTile(myPropertyFile.getProp("Tile1Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile1File")), "test", Color.BROWN,1,1,myHorizontals,myScreenWidth,0);
        placeNonPropertyTile(myPropertyFile.getProp("Tile2Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile2File")), "",2,1,myHorizontals,myScreenWidth,0);
        placePropertyTile(myPropertyFile.getProp("Tile3Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile3File")), "test",Color.BROWN,3,1,myHorizontals,myScreenWidth,0);
        placeNonPropertyTile(myPropertyFile.getProp("Tile4Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile4File")), "",4,1,myHorizontals,myScreenWidth,0);
        placePropertyTile(myPropertyFile.getProp("Tile5Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile5File")), "",Color.BLACK ,5,1,myHorizontals,myScreenWidth,0);
        placePropertyTile(myPropertyFile.getProp("Tile6Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile6File")), "test",Color.AZURE,6,1,myHorizontals,myScreenWidth,0);
        placeNonPropertyTile(myPropertyFile.getProp("Tile7Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile7File")), "",7,1,myHorizontals,myScreenWidth,0);
        placePropertyTile(myPropertyFile.getProp("Tile8Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile8File")), "test",Color.AZURE,8,1,myHorizontals,myScreenWidth,0);
        placePropertyTile(myPropertyFile.getProp("Tile9Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile9File")), "test",Color.AZURE,9,1,myHorizontals,myScreenWidth,0);
    }

    private void makeLeftRow(){
        placePropertyTile(myPropertyFile.getProp("Tile11Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile11File")), "test",Color.DEEPPINK,9,1,myVerticals,myScreenHeight,90);
        placePropertyTile(myPropertyFile.getProp("Tile12Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile12File")), "",Color.BEIGE,9,2,myVerticals,myScreenHeight,90);
        placePropertyTile(myPropertyFile.getProp("Tile13Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile13File")), "test",Color.DEEPPINK,9,3 ,myVerticals,myScreenHeight,90);
        placePropertyTile(myPropertyFile.getProp("Tile14Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile14File")), "test",Color.DEEPPINK,9,4,myVerticals,myScreenHeight,90);
        placePropertyTile(myPropertyFile.getProp("Tile15Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile15File")), "",Color.BLACK,9,5,myVerticals,myScreenHeight,90);
        placePropertyTile(myPropertyFile.getProp("Tile16Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile16File")), "",Color.ORANGE,9,6,myVerticals,myScreenHeight,90);
        placeNonPropertyTile(myPropertyFile.getProp("Tile17Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile17File")), "",9,7,myVerticals,myScreenHeight,90);
        placePropertyTile(myPropertyFile.getProp("Tile18Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile18File")), "",Color.ORANGE,9,8,myVerticals,myScreenHeight,90);
        placePropertyTile(myPropertyFile.getProp("Tile19Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile19File")), "",Color.ORANGE,9,9,myVerticals,myScreenHeight,90);
    }

    private void makeTopRow() {
        placePropertyTile(myPropertyFile.getProp("Tile21Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile21File")),"test", Color.RED,1,1,myHorizontals,myScreenWidth,180);
        placeNonPropertyTile(myPropertyFile.getProp("Tile22Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile22File")), "",2,1,myHorizontals,myScreenWidth,180);
        placePropertyTile(myPropertyFile.getProp("Tile23Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile23File")), "test",Color.RED,3,1,myHorizontals,myScreenWidth,180);
        placePropertyTile(myPropertyFile.getProp("Tile24Name"),new ImportPropertyFile(myPropertyFile.getProp("Tile24File")),"test",Color.RED,4,1,myHorizontals,myScreenWidth,180);
        placePropertyTile(myPropertyFile.getProp("Tile25Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile25File")),"",Color.BLACK,5,1,myHorizontals,myScreenWidth,180);
        placePropertyTile(myPropertyFile.getProp("Tile26Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile26File")),"test",Color.YELLOW,6,1,myHorizontals,myScreenWidth,180);
        placePropertyTile(myPropertyFile.getProp("Tile27Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile27File")), "test",Color.YELLOW,7,1,myHorizontals,myScreenWidth,180);
        placePropertyTile(myPropertyFile.getProp("Tile28Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile28File")), "",Color.BEIGE,8,1,myHorizontals,myScreenWidth,180);
        placePropertyTile(myPropertyFile.getProp("Tile29Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile29File")),"test",Color.YELLOW,9,1,myHorizontals,myScreenWidth,180);

    }

    private void makeRightRow(){
        placePropertyTile(myPropertyFile.getProp("Tile31Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile31File")),"",Color.GREEN,1,1,myVerticals,myScreenHeight,270);
        placePropertyTile(myPropertyFile.getProp("Tile32Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile32File")), "",Color.GREEN,2,2,myVerticals,myScreenHeight,270);
        placeNonPropertyTile(myPropertyFile.getProp("Tile33Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile33File")), "",3,3,myVerticals,myScreenHeight,270);
        placePropertyTile(myPropertyFile.getProp("Tile34Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile34File")), "",Color.GREEN,4,4,myVerticals,myScreenHeight,270);
        placePropertyTile(myPropertyFile.getProp("Tile35Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile35File")), "",Color.BLACK,5,5,myVerticals,myScreenHeight,270);
        placeNonPropertyTile(myPropertyFile.getProp("Tile36Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile36File")), "",6,6,myVerticals,myScreenHeight,270);
        placePropertyTile(myPropertyFile.getProp("Tile37Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile37File")),"",Color.BLUE,7,7,myVerticals,myScreenHeight,270);
        placeNonPropertyTile(myPropertyFile.getProp("Tile38Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile38File")), "",8,8,myVerticals,myScreenHeight,270);
        placePropertyTile(myPropertyFile.getProp("Tile39Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile39File")), "",Color.BLUE,9,9,myVerticals,myScreenHeight,270);
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

    public void placePropertyTile(String tileName, ImportPropertyFile details,
                                  String tileDescription,
                                  Paint tileColor,
                                  int xoffset,
                                  int yoffset,
                                  int totalTiles,
                                  double sideLength,double rotationAngle){
        var tile = new PropertyTileView(tileName, details, tileDescription,tileColor);
        var height = myTileHeight;
        var width = calculateTileWidth(sideLength,totalTiles);
        tile.makeTileViewNode(new double[]{width,height});
        Node tileNode = tile.getNodeOfTileView();

        System.out.print("Prop Tile Set");
        tileNode.setOnMouseClicked(e -> {showTileClickedAlert(details);});
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
        tileNode.setRotate(rotationAngle);
        myRoot.getChildren().add(tileNode);
        myTiles.add(tile);
    }

    private void showTileClickedAlert(ImportPropertyFile details) {
        System.out.print("BANG!");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(details.getProp("TileName"));
        alert.setHeaderText("Tile Info:");
        alert.setContentText(
                "Tile Price = " + details.getProp("TilePrice")+"\n"+
                        "Tile Rent = " + details.getProp("TileRent") +"\n" +
                        "Tile Rent with Color Set = " + details.getProp("TileRentWithColorSet") +"\n" +
                        "Tile Rent with 1 House = " + details.getProp("TileRent1House") +"\n" +
                        "Tile Rent with 2 Houses = " + details.getProp("TileRent2House") +"\n" +
                        "Tile Rent with 3 Houses = " + details.getProp("TileRent3House") +"\n" +
                        "Tile Rent with 4 Houses = " + details.getProp("TileRent4House") +"\n" +
                        "Tile Rent with Hotel = " + details.getProp("TileRentHotel") +"\n" +
                        "Tile Mortgage Value = " + details.getProp("TileMortgageValue") +"\n" +
                        "Tile House Price = " + details.getProp("TileHousePrice") +"\n" +
                        "Tile Hotel Price = " + details.getProp("TileHotelPrice") +"\n");
        alert.showAndWait();
    }

    public void placeNonPropertyTile(String tileName, ImportPropertyFile details,
                                     String tileDescription,
                                     int xoffset,
                                     int yoffset,
                                     int totalTiles,
                                     double sideLength,double rotationAngle){
        var tile = new RectangularTileView(tileName, details, tileDescription,"");
        var height = myTileHeight;
        var width = calculateTileWidth(sideLength,totalTiles);
        tile.makeTileViewNode(new double[]{width,height});
        Node tileNode = tile.getNodeOfTileView();
        System.out.print("Nonprop Tile Set");
        tileNode.setOnMouseClicked(e -> {showTileClickedAlert(details);});
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
        tileNode.setRotate(rotationAngle);
        myRoot.getChildren().add(tileNode);
        myTiles.add(tile);
    }

    public void placeCornerTile(String tileName,
                                String details,
                                String tileDescription,
                                String tileColor,
                                double xDiff,
                                double yDiff){
        var width = myTileHeight;
        var height = myTileHeight;
        var tile = new CornerTileView(tileName, details, tileDescription, tileColor);
        tile.makeTileViewNode(new double[]{width,height});
        Node tileNode = tile.getNodeOfTileView();
        myRoot.setTopAnchor(tileNode, (myScreenHeight-height)*yDiff);
        myRoot.setLeftAnchor(tileNode, (myScreenWidth-width)*xDiff);
        //System.out.print(details);
        ImportPropertyFile deets = new ImportPropertyFile(details);
        //System.out.print(deets);
        System.out.print("Corner Tile Set");
        tileNode.setOnMouseClicked(e -> {showTileClickedAlert(deets);});
        myRoot.getChildren().add(tileNode);
    }

    public void makeCorners(){
        //System.out.print(myPropertyFile.getProp("TileOName"));
        //System.out.print(myPropertyFile.getProp("TileOFile®"))
        placeCornerTile(myPropertyFile.getProp("Tile0Name"), myPropertyFile.getProp("Tile0File"),"Go","clear",1,1);
        placeCornerTile(myPropertyFile.getProp("Tile10Name"), myPropertyFile.getProp("Tile10File"),"Go","clear",0,1);
        placeCornerTile(myPropertyFile.getProp("Tile20Name"), myPropertyFile.getProp("Tile20File"),"Go","clear",0,0);
        placeCornerTile(myPropertyFile.getProp("Tile30Name"), myPropertyFile.getProp("Tile30File"),"Go","clear",1,0);

    }

    public void move(int i) {
        myTimes = i;
        Thread updateThread = new Thread(() -> {
            while(myTimes>0) {
                try {
                    Thread.sleep(300);
                    Platform.runLater(() -> this.movePieceDemo(myIcon));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        updateThread.setDaemon(true);
        updateThread.start();
    }

    private void moveHelper(int i) {

    }
}