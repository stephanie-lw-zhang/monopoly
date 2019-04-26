//package controller;
//
//import backend.assetholder.AbstractPlayer;
//import backend.assetholder.HumanPlayer;
//import backend.board.AbstractBoard;
//import backend.board.StandardBoard;
//import configuration.XMLData;
//import frontend.screens.AbstractScreen;
//import frontend.views.FormView;
//import frontend.views.RulesView;
//import javafx.scene.Node;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.TextField;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import org.xml.sax.SAXException;
//
//import javax.xml.parsers.ParserConfigurationException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class CustomController {
//    private GameController myGameController;
//    private RulesView myRulesView;
//    private AbstractScreen myScreen;
//    private double screenWidth,screenHeight;
//    private XMLData myData;
//    private AbstractBoard myBoard;
//    private Node myNode;
//
//
//    public CustomController(double sWidth, double sHeight, AbstractScreen screen) throws ParserConfigurationException, SAXException, IOException {
//        screenWidth = sWidth;
//        screenHeight = sHeight;
//        myScreen = screen;
//        myRulesView = new RulesView(this);
//        myNode = myRulesView.getNode();
//        myData = new XMLData(myRulesView.createCustomXML().getNodeName());
//    }
//
//
//    private void makeSetUpScreen(Map<TextField, ComboBox> playerToIcon) {
//        myBoard = makeBoard(playerToIcon);
////        myGameController = new GameController(screenWidth,screenHeight,
////                this,
////                myBoard,myData
////        );
//        myNode = myGameController.getGameNode();
//    }
//
//    public Node getNode() {
//        return myNode;
//    }
//
//    public void startGame(Map<TextField, ComboBox> playerToIcon) {
//        makeSetUpScreen(playerToIcon);
//        myScreen.changeDisplayNode(myNode);
//    }
//
////    private AbstractBoard makeBoard(Map<TextField, ComboBox> playerToIcon) {
////        return new StandardBoard(
////                makePlayerList(playerToIcon), myData.getAdjacencyList(),
////                myData.getPropertyCategoryMap(), myData.getFirstTile(),
////                myData.getBank()
////        );
////    }
////
////
//
//    private ImageView makeIcon(String iconPath) {
//        Image image = new Image(iconPath + ".png");
//        ImageView imageView = new ImageView(image);
//        imageView.setFitHeight(25);
//        imageView.setFitWidth(25);
//
//        return imageView;
//    }
//
//}
