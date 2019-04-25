package frontend.views.board;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.card.property_cards.PropertyCard;
import backend.tile.AbstractPropertyTile;
import backend.tile.Tile;

import frontend.views.board.boardcomponents.*;
import frontend.views.IconView;

import javafx.application.Platform;
import javafx.geometry.Insets;

import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.layout.*;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the generalized View of a rectangular Monopoly Board
 * consisting of TileViews and Icons.
 *
 * @author Edward
 * @author Sam
 */
public class RectangularBoardView extends AbstractBoardView {

    private AnchorPane             myRoot;
    private double                 myTileHeight;
    private double                 myScreenWidth, myScreenHeight;
    private int                    myHorizontals, myVerticals;

    private Map<IconView, Integer> iconToIndexMap;
    private List<AbstractPlayer>   myPlayerList;
    private List<AbstractTileView> myTiles;
    private List<IconView>         myIconList;
    private AbstractBoard          myBoard;
    private int                    myNumMoves;
    private Map<Tile, AbstractTileView> tileToTileView;

    /**
     * RectangularBoardView main constructor
     * @param board
     * @param screenWidth
     * @param screenHeight
     * @param tileHeight
     * @param horizontalTiles
     * @param verticalTiles
     */
    public RectangularBoardView(AbstractBoard board, double screenWidth, double screenHeight, double tileHeight, int horizontalTiles, int verticalTiles){
        super(screenWidth,screenHeight);
        myBoard = board;
        myTiles = new ArrayList<>();
        myTileHeight = tileHeight;
        myHorizontals = horizontalTiles;
        myVerticals = verticalTiles;

        makeBoard();
        makeBackground();
        myPlayerList = myBoard.getMyPlayerList();
        setIconList();
        setIconToIndexMap();
        // initializePlayerIcons();
    }

    private void setIconToIndexMap() {
        iconToIndexMap = new HashMap<>();
        for (IconView i : myIconList)
            iconToIndexMap.put(i, 0);
    }

    private void setIconList() {
        myIconList = new ArrayList<>();
        for (AbstractPlayer p : myPlayerList) {
            p.getMyIcon().setHeight(myTileHeight / 2);
            p.getMyIcon().setWidth( myTileHeight / 2);

            myIconList.add(p.getMyIcon());
        }
    }

    private void initializePlayerIcons() {
         for (IconView icon : myIconList)
            myTiles.get(0).moveTo(icon.getMyNode());
    }

    private void makeBackground() {
        BackgroundFill boardBackgroundFill = new BackgroundFill(Color.rgb(220,237,200), CornerRadii.EMPTY, Insets.EMPTY);
        Background boardBackGround = new Background(boardBackgroundFill);
        myRoot.setBackground(boardBackGround);

        /* keeps board background color in line with view itself */
        myRoot.maxWidthProperty().bind(myRoot.widthProperty());
        myRoot.maxHeightProperty().bind(myRoot.heightProperty());
    }

    private double calculateTileWidth(double sideLength, double totalTiles){
        return (sideLength-myTileHeight*2)/(totalTiles-2);
    }

    /**
     * Lays out the RectangularBoardView TileViews
     */
    public void makeBoard() {
        makeCorners();
        makeBottomRow();
        makeLeftRow();
        makeTopRow();
        makeRightRow();
        placeDeck("Community Chest","",100,50,0.6);
        placeDeck("Chance","",100,50,0.25);
    }

    /**
     * This method dictates the movement of a particular
     * player icon on the board for the given number of moves
     * @param icon      the IconView of the player to move
     * @param nMoves    the number of moves the icon moves
     */
    public void move(IconView icon, int nMoves) {
        myNumMoves = nMoves; // update invariant myNumMoves
        Thread updateThread = new Thread(() -> {
            while(myNumMoves>0) {
                try {
                    Thread.sleep(300);
                    Platform.runLater(() -> this.movePieceDemo(icon));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        updateThread.setDaemon(true);
        updateThread.start();
    }

    private void movePieceDemo(IconView icon) {
        if(myNumMoves>0) {
            if (iconToIndexMap.get(icon) >= myTiles.size()) {
                iconToIndexMap.put(icon, 0);
            }
            myTiles.get(iconToIndexMap.get(icon)).moveTo(icon.getMyNode());
            iconToIndexMap.put(icon, iconToIndexMap.get(icon) + 1);
            myNumMoves--;
        }
    }

    public void move(IconView icon, Tile tile){
        //TODO: move to tile after creating tile To Tile View
    }

    /**
     * Setter of the RectangularBoardView object's AnchorPane
     */
    @Override
    public void setRoot() {
        myRoot = new AnchorPane();
    }

    /**
     * Defines the given Screen dimensions
     * @param screenWidth
     * @param screenHeight
     */
    @Override
    public void setScreenLimits(double screenWidth, double screenHeight) {
        myScreenHeight = screenHeight;
        myScreenWidth = screenWidth;
    }

    /**
     * Returns the AnchorPane of the RectangularBoardView
     * @return Pane, the AnchorPane
     */
    @Override
    public Node getBoardViewNode() {
        return myRoot;
    }

    private void makeBottomRow() {
        for(int i = 1; i<10;i++){
            Tile tile= myBoard.getTilesIndex(i);
            String s = tile.getTileType();
            //change TileType to make BuildingTile, RailroadTile, and UtilityTile all
            if(s.equalsIgnoreCase("BuildingTile")){
                PropertyCard card = ((AbstractPropertyTile) tile).getCard();
                try {
                    tileToTileView.put( tile, placePropertyTile(card.getTitleDeed(), "", (Color)Color.class.getField(card.getCategory().toUpperCase()).get(null), i, 1, myHorizontals, myScreenWidth, 0));
                } catch (IllegalAccessException e) {
                    e.printStackTrace(); // change this !!!
                } catch (NoSuchFieldException e) {
                    e.printStackTrace(); // change this !!!
                }
            }
            //else if(s.equalsIgnoreCase("RailroadTile")||s.equalsIgnoreCase("UtilityTile")){
            //    placePropertyTile(tile.getTileType(), "", Color.GREEN, i, 1, myHorizontals, myScreenWidth,  0);
            //}
            else {
                tileToTileView.put(tile, placeNonPropertyTile(tile.getTileType(), "", i, 1, myHorizontals, myScreenWidth, 0));
                //this needs to be changed, might need to give names to non-property tiles
                //AbstractCard card = tile;
                //placeNonPropertyTile();
            }
        }
    }

    private void makeLeftRow(){
        int x = 0;
        for(int i = 11; i<20;i++) {
            x++;
            Tile tile = myBoard.getTilesIndex(i);
            String s = tile.getTileType();
            //change TileType to make BuildingTile, RailroadTile, and UtilityTile all
            if (s.equalsIgnoreCase("BuildingTile")) {//||s.equalsIgnoreCase("RailroadTile")||s.equalsIgnoreCase("UtilityTile")){
                PropertyCard card = ((AbstractPropertyTile) tile).getCard();
                try {
                    tileToTileView.put(tile, placePropertyTile(card.getTitleDeed(), "", (Color) Color.class.getField(card.getCategory().toUpperCase()).get(null), 9, x, myHorizontals, myScreenWidth, 90));
                } catch (IllegalAccessException e) {
                    e.printStackTrace(); // change this !!!
                } catch (NoSuchFieldException e) {
                    e.printStackTrace(); // change this !!!
                }
            //} else if (s.equalsIgnoreCase("RailroadTile") || s.equalsIgnoreCase("UtilityTile")) {
            //    placePropertyTile(tile.getTileType(), "", Color.GREEN, 9, i-10, myHorizontals, myScreenWidth, 90);
            } else {
                tileToTileView.put(tile, placeNonPropertyTile(tile.getTileType(), "", 9, i-10, myHorizontals, myScreenWidth, 90));
                //this needs to be changed, might need to give names to non-property tiles
                //AbstractCard card = tile;
                //placeNonPropertyTile();
            }
        }
    }

    private void makeTopRow() {
        int x = 0;
        for(int i = 21; i<30;i++) {
            x++;
            Tile tile = myBoard.getTilesIndex(i);
            String s = tile.getTileType();
            //change TileType to make BuildingTile, RailroadTile, and UtilityTile all
            if (s.equalsIgnoreCase("BuildingTile")) {//||s.equalsIgnoreCase("RailroadTile")||s.equalsIgnoreCase("UtilityTile")){
                PropertyCard card = ((AbstractPropertyTile) tile).getCard();
                try {
                    tileToTileView.put(tile, placePropertyTile(card.getTitleDeed(), "", (Color) Color.class.getField(card.getCategory().toUpperCase()).get(null), x, 1, myHorizontals, myScreenWidth, 180));
                } catch (IllegalAccessException e) {
                    e.printStackTrace(); // change this !!!
                } catch (NoSuchFieldException e) {
                    e.printStackTrace(); // change this !!!
                }
            }
//            else if (s.equalsIgnoreCase("RailroadTile") || s.equalsIgnoreCase("UtilityTile")) {
//                placePropertyTile(tile.getTileType(), "", Color.GREEN, x, 1, myHorizontals, myScreenWidth, 180);
//            }
            else {
                tileToTileView.put(tile, placeNonPropertyTile(tile.getTileType(), "", x, 1, myHorizontals, myScreenWidth, 180));
            }
        }
    }

    private void makeRightRow(){
        int x = 0;
        for(int i = 31; i<40;i++) {
            x++;
            Tile tile = myBoard.getTilesIndex(i);
            String s = tile.getTileType();
            //change TileType to make BuildingTile, RailroadTile, and UtilityTile all
            if (s.equalsIgnoreCase("BuildingTile")) {//||s.equalsIgnoreCase("RailroadTile")||s.equalsIgnoreCase("UtilityTile")){
                PropertyCard card = ((AbstractPropertyTile) tile).getCard();
                try {
                    tileToTileView.put( tile, placePropertyTile(card.getTitleDeed(), "", (Color) Color.class.getField(card.getCategory().toUpperCase()).get(null), x, x, myHorizontals, myScreenWidth, 270));
                } catch (IllegalAccessException e) {
                    e.printStackTrace(); // change this !!!
                } catch (NoSuchFieldException e) {
                    e.printStackTrace(); // change this !!!
                }
                //} else if (s.equalsIgnoreCase("RailroadTile") || s.equalsIgnoreCase("UtilityTile")) {
                //    placePropertyTile(tile.getTileType(), "", Color.GREEN, 9, i-10, myHorizontals, myScreenWidth, 90);
            } else {
                tileToTileView.put(tile, placeNonPropertyTile(tile.getTileType(), "", x, x, myHorizontals, myScreenWidth, 270));
                //this needs to be changed, might need to give names to non-property tiles
                //AbstractCard card = tile;
                //placeNonPropertyTile();
            }
        }
    }


    public void placeDeck(String tileName,
                          String tileDescription,
                          double width, double length, double prop){
        var tile = new NormalDeckView(tileName,tileDescription);
        var height = length;
        tile.makeTileViewNode(new double[]{width,height});
        Node tileNode = tile.getNodeOfTileView();
        tileNode.setRotate(-45);
        myRoot.setTopAnchor(tileNode,myScreenHeight*prop);
        myRoot.setLeftAnchor(tileNode,myScreenWidth*prop);
        myRoot.getChildren().add(tileNode);
    }

    public PropertyTileView placePropertyTile(String tileName,
                                  String tileDescription,
                                  Paint tileColor,
                                  int xoffset,
                                  int yoffset,
                                  int totalTiles,
                                  double sideLength,double rotationAngle){
        var tile = new PropertyTileView(tileName, tileDescription,tileColor);
//        myBoard.get
        var height = myTileHeight;
        var width = calculateTileWidth(sideLength,totalTiles);
        tile.makeTileViewNode(new double[]{width,height});
        Node tileNode = tile.getNodeOfTileView();

        //System.out.print("Prop tile Set");
        // tileNode.setOnMouseClicked(e -> {showTileClickedAlert(details);});
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
        return tile;
    }

    public RectangularTileView placeNonPropertyTile(String tileName, //ImportPropertyFile details,
                                     String tileDescription,
                                     int xoffset,
                                     int yoffset,
                                     int totalTiles,
                                     double sideLength,double rotationAngle){
        var tile = new RectangularTileView(tileName, tileDescription);
        var height = myTileHeight;
        var width = calculateTileWidth(sideLength,totalTiles);
        tile.makeTileViewNode(new double[]{width,height});
        Node tileNode = tile.getNodeOfTileView();
//        System.out.print("Nonprop tile Set");

        // tileNode.setOnMouseClicked(e -> {showTileClickedAlert(details);});
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
        return tile;
    }

    public CornerTileView placeCornerTile(String tileName,
                                String tileDescription,
                                String tileColor,
                                double xDiff,
                                double yDiff){
        var width = myTileHeight;
        var height = myTileHeight;
        var tile = new CornerTileView(tileName, tileDescription, tileColor);
        tile.makeTileViewNode(new double[]{width,height});
        Node tileNode = tile.getNodeOfTileView();
        myRoot.setTopAnchor(tileNode, (myScreenHeight-height)*yDiff);
        myRoot.setLeftAnchor(tileNode, (myScreenWidth-width)*xDiff);
//        ImportPropertyFile deets = new ImportPropertyFile(details);

        // tileNode.setOnMouseClicked(e -> {showTileClickedAlert(deets);});
        myRoot.getChildren().add(tileNode);
        return tile;
    }

    public void makeCorners(){
        //System.out.print(myPropertyFile.getProp("TileOName"));
        //System.out.print(myPropertyFile.getProp("TileOFile®"))
        Tile tileZero = myBoard.getTilesIndex(0);
        Tile tileTen = myBoard.getTilesIndex(10);
        Tile tileTwenty = myBoard.getTilesIndex(20);
        Tile tileThirty = myBoard.getTilesIndex(30);

        tileToTileView.put(tileZero, placeCornerTile(tileZero.getTileType(), tileZero.getTileType(), "clear", 1, 1));
        tileToTileView.put(tileTen, placeCornerTile(tileTen.getTileType(), tileTen.getTileType(), "clear", 0, 1));
        tileToTileView.put(tileTwenty, placeCornerTile(tileTwenty.getTileType(), tileTwenty.getTileType(), "clear", 0, 0));
        tileToTileView.put(tileThirty, placeCornerTile(tileThirty.getTileType(), tileThirty.getTileType(), "clear", 1, 0));
    }

    //TODO: FOR MATT
/**
 * TO DiSPLAY TILE POPUPS ON CLICK OF TILE
 *
 * FIND THIS LINE OF CODE IN PLACEPROP and PLACENONPROP methods
 */
    private void showTileClickedAlert(AbstractPropertyTile tile) {
        //System.out.print("BANG!");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tile.getName());
        alert.setHeaderText("tile Info:");
        alert.setContentText(
                //loop through the key set of the look-up table so this isn't so hard coded !!!!
                "tile Price = " + tile.getCard().getTilePrice() + "\n"+
                        "tile Rent = " + tile.getCard().getRentPriceLookupTable().get("NoHouse") +"\n" +
                        "tile Rent with Color Set = " + tile.getCard().getRentPriceLookupTable().get("TileRentWithColorSet") +"\n" +
                        "tile Rent with 1 House = " + tile.getCard().getRentPriceLookupTable().get("TileRent1House") +"\n" +
                        "tile Rent with 2 Houses = " + tile.getCard().getRentPriceLookupTable().get("TileRent2House") +"\n" +
                        "tile Rent with 3 Houses = " + tile.getCard().getRentPriceLookupTable().get("TileRent3House") +"\n" +
                        "tile Rent with 4 Houses = " + tile.getCard().getRentPriceLookupTable().get("TileRent4House") +"\n" +
                        "tile Rent with Hotel = " + tile.getCard().getRentPriceLookupTable().get("TileRentHotel") +"\n" +
                        "tile Mortgage Value = " + tile.getCard().getRentPriceLookupTable().get("TileMortgageValue") +"\n" +
                        "tile House Price = " + tile.getCard().getRentPriceLookupTable().get("TileHousePrice") +"\n" +
                        "tile Hotel Price = " + tile.getCard().getRentPriceLookupTable().get("TileHotelPrice") +"\n");
        alert.showAndWait();
    }
}