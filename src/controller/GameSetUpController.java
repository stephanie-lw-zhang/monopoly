package controller;

import backend.assetholder.AbstractAssetHolder;
import backend.assetholder.AbstractPlayer;
import backend.assetholder.HumanPlayer;
import backend.board.AbstractBoard;
import backend.board.StandardBoard;
import backend.card.AbstractCard;
import backend.card.action_cards.ActionCard;
import backend.card.action_cards.PayCard;
import backend.deck.DeckInterface;
import backend.deck.NormalDeck;
import backend.dice.SixDice;
import configuration.ImportPropertyFile;
import configuration.XMLData;
import frontend.screens.AbstractScreen;
import frontend.views.FormView;

import frontend.views.board.SquareBoardView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is a controller that manages all "set-up" before
 * the game starts (i.e. creation of myBoard, etc.)
 *
 * @author Edward
 * @author Sam
 */
public class GameSetUpController {

    private static final String CONFIG_FILE = "OriginalMonopoly.xml";


    private ImportPropertyFile myPropertyFile = new ImportPropertyFile("OriginalMonopoly.properties");
    private Node myNode;

    private GameController myGameController;
    private FormView myFormView;
    private AbstractScreen myScreen;
    private double screenWidth,screenHeight;
    private XMLData myData;
    private AbstractBoard myBoard;

    public GameSetUpController(double sWidth, double sHeight, AbstractScreen screen){
        screenWidth = sWidth;
        screenHeight = sHeight;
        myScreen = screen;
        myFormView = new FormView(this);
        myNode = myFormView.getNode();
        try {
            myData = new XMLData(CONFIG_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //makeSetUpScreen();
    }

    private void makeSetUpScreen(Map<TextField, ComboBox> playerToIcon) {
        myBoard = makeBoard(playerToIcon);
        myGameController = new GameController(
                screenWidth,screenHeight,
                this, myBoard, myData
        );
        myNode = myGameController.getGameNode();
    }

    public Node getNode() {
        return myNode;
    }

    public void startGame(Map<TextField, ComboBox> playerToIcon) {
        makeSetUpScreen(playerToIcon);
        myScreen.changeDisplayNode(myNode);
    }

    private AbstractBoard makeBoard(Map<TextField, ComboBox> playerToIcon) {
        AbstractBoard board = new StandardBoard(
                makePlayerList(playerToIcon), myData.getAdjacencyList(),
                myData.getPropertyCategoryMap(), myData.getFirstTile(),
                myData.getBank()
        );
        //reinitializeDecks(myData.getDecks(), );
        return board;
    }

    private List<AbstractPlayer> makePlayerList(Map<TextField, ComboBox> playerToIcon) {
        List<AbstractPlayer> playerList = new ArrayList<>();

        for (TextField pName : playerToIcon.keySet()) {
            String name = pName.getText();
            if (!name.equals(""))

                //use reflection to get class name and create a type of player dependent on user choice of playertype
                
                playerList.add(new HumanPlayer(
                        name,
                        (String) playerToIcon.get(pName).getValue(),
                        1500.00));
        }
        return playerList;
    }

//    private List<NormalDeck> reinitializeDecks(List<NormalDeck> decks, List<AbstractAssetHolder> playerList){
//        for(NormalDeck deck: decks){
//            for(ActionCard card: deck.getCards()){
//                //this is very hardcoded at the moment
//                if(card.getActionType().equalsIgnoreCase("Pay")){
//                    if(((PayCard) card).getPayeeString().equalsIgnoreCase("Everyone")){
//                        ((PayCard) card).setPayees(playerList);
//                    }
//                    //else if()
//                }
//                else if(card.getActionType().equalsIgnoreCase("PayBuildings")){
//                    if(((PayCard) card).getPayeeString().equalsIgnoreCase("Everyone")){
//                        //((PayCard) card).setPayers();
//                        ((PayCard) card).setPayees(playerList);
//                    }
//                }
//                else if(card.getActionType().equalsIgnoreCase("MoveAndPay")){
//
//                }
//            }
//        }
//    }

    private ImageView makeIcon(String iconPath) {
        Image image = new Image(iconPath + ".png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);

        return imageView;
    }

    public void backToParent() {
        myScreen.backToParent();
    }

    public void handleSave() {
    }

    public void handleLoad(){

    }
}
