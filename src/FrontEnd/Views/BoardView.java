package FrontEnd.Views;

import BackEnd.Board.AbstractBoard;
import Configuration.ImportPropertyFile;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import javax.sound.midi.ControllerEventListener;


public class BoardView {
    private AbstractBoard myModel;
    private AnchorPane myRoot;
    private double myScreenWidth, myScreenHeight;
    private int myHorizontals, myVerticals;
    private double myTileHeight;

    private ImportPropertyFile myPropertyFile;
    private ImportPropertyFile details;

    public BoardView(double screenWidth, double screenHeight, double tileHeight, int horizontalTiles, int verticalTiles, ImportPropertyFile propertyFile){
        myRoot = new AnchorPane();
        myRoot.setMaxWidth(screenWidth);
        myRoot.setMaxHeight(screenHeight);
        myScreenHeight = screenHeight;
        myScreenWidth = screenWidth;
        myTileHeight = tileHeight;
        myHorizontals = horizontalTiles;
        myVerticals = verticalTiles;
        myPropertyFile=propertyFile;
        System.out.print(myPropertyFile);

        //System.out.print(myPropertyFile.getProp("TileOName"));
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
        tileNode.setOnMouseClicked(e -> {showTileClickedAlert(details);});
        myRoot.getChildren().add(tileNode);
    }

    private void showTileClickedAlert(ImportPropertyFile details) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(details.getProp("TileName"));
        alert.setContentText("Example Property Tile");
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
        tileNode.setOnMouseClicked(e -> {showTileClickedAlert(details);});
        myRoot.getChildren().add(tileNode);
    }

    public void placeCornerTile(String tileName,
                                ImportPropertyFile details,
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
        tileNode.setOnMouseClicked(e -> {showTileClickedAlert(details);});
        myRoot.getChildren().add(tileNode);


    }

    public void makeCorners(){
        System.out.print(myPropertyFile.getProp("TileOName"));
        System.out.print(myPropertyFile.getProp("TileOFile®"));
        placeCornerTile(myPropertyFile.getProp("Tile0Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile0File")),"Go","clear",1,1);
        placeCornerTile(myPropertyFile.getProp("Tile10Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile10File")),"Go","clear",0,1);
        placeCornerTile(myPropertyFile.getProp("Tile20Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile20File")),"Go","clear",0,0);
        placeCornerTile(myPropertyFile.getProp("Tile30Name"), new ImportPropertyFile(myPropertyFile.getProp("Tile30File")),"Go","clear",1,0);
    }
}
