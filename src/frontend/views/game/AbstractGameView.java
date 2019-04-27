package frontend.views.game;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.tile.Tile;
import configuration.XMLData;
import controller.Turn;
import frontend.views.board.AbstractBoardView;

import frontend.views.player_options.AbstractOptionsView;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;
import javafx.scene.Scene;
import javafx.scene.Node;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents the abstract class of the View component
 * of a Game
 */
abstract public class AbstractGameView {

    private Scene             myScene;
    private AbstractBoardView myBoardView;
    private BorderPane        myPane;
    private AbstractOptionsView myOptions;

    /**
     * AbstractGameView main constructor
     * @param screenWidth
     * @param screenHeight
     */
    public AbstractGameView(double screenWidth, double screenHeight, XMLData data, AbstractBoard board){
        setBoundsForEntireGame(screenWidth,screenHeight);
        divideScreen();
    }

    abstract public Node getGameViewNode();
    abstract public void setBoundsForEntireGame(double screenWidth, double screenHeight);
    abstract public void divideScreen();
    abstract public void addBoardView();
    abstract public void addPlayerOptionsView();
    abstract public void setTurnActions(List<String> turnActions);
    abstract public String showInputTextDialog(String title, String header, String content);

    /**
     * Displays popup of actions
     * @param info
     */
    public void displayActionInfo(String info) {
        Alert formAlert = new Alert(Alert.AlertType.INFORMATION);
        formAlert.setContentText(info);
        formAlert.showAndWait();
    }

    public String displayDropDownAndReturnResult(String title, String prompt, ObservableList<String> options){
        ChoiceDialog players = new ChoiceDialog( options.get( 0 ), options );
        players.setHeaderText( title );
        players.setContentText(prompt);
        //"Select the player who wants to mortgage a property: "
        players.showAndWait();
        return (String) players.getSelectedItem();
    }

    public String displayOptionsPopup(List<String> options, String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        List<ButtonType> buttonOptions = new ArrayList<>();
        for (String option : options) {
            buttonOptions.add(new ButtonType(option));
        }

        alert.getButtonTypes().setAll(buttonOptions);
        Optional<ButtonType> result = alert.showAndWait();

        return result.orElse(null).getText();
    }

    abstract public void createOptions(Map<String, EventHandler<ActionEvent>> handlerMap);
    abstract public void updateDice(Turn turn);
    public abstract void updateAssetDisplay(List<AbstractPlayer> myPlayerList);

    public abstract void disableButton(String str);

    public abstract void enableButton(String str);

    public abstract void updateCurrPlayerDisplay(AbstractPlayer currPlayer);
    public abstract void updateIconDisplay(AbstractPlayer currPlayer, int nMoves);
    public abstract void updateIconDisplay(AbstractPlayer currPlayer, Tile tile);
    public abstract void updateLogDisplay(String s);
}