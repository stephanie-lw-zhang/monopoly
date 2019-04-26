package frontend.views.player_options;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import configuration.XMLData;
import controller.Turn;
import exceptions.CancelledActionException;
import exceptions.PropertyNotFoundException;
import frontend.views.FundsView;
import frontend.views.LogView;
import frontend.views.game.AbstractGameView;

import frontend.views.player_stats.PlayerCardsView;
import frontend.views.player_stats.PlayerFundsView;
import frontend.views.player_stats.PlayerPropertiesView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Control;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents some abstract VBox node that offers
 * options and controls to users
 *
 * @author Edward
 * @author Sam
 * @author Stephanie
 */
public class BPaneOptionsView extends AbstractOptionsView {

    private BorderPane myOptionsViewNode;
    private AbstractGameView    myGameView;
    private Map<String,Control> myControls;
    private VBox playerOptionsModal;
    private DiceView myDiceView;
    private LogView myLogView;
    private XMLData myData;
    private PlayerPropertiesView myPlayerPropertiesView;
    private AbstractBoard myBoard;
    private PlayerFundsView myPlayerFundsView;
    private PlayerCardsView myPlayerCardsView;

    /**
     * VBoxOptionsView main constructor
     * @param gameView
     */
    public BPaneOptionsView(AbstractGameView gameView, AbstractBoard board, XMLData data){
        super(gameView,board);
        myGameView = gameView;
        myOptionsViewNode= new BorderPane();
        myControls = new HashMap<>();
        myData = data;
        myBoard = board;
        makeNonActionViews();
        makeAssetViews();
    }

    private void makeAssetViews() {
        myPlayerPropertiesView = new PlayerPropertiesView(myBoard.getMyPlayerList());
        myPlayerFundsView = new PlayerFundsView(myBoard.getMyPlayerList());
        myPlayerCardsView = new PlayerCardsView(myBoard.getMyPlayerList());
        VBox aBox = new VBox();
        aBox.getChildren().addAll(myPlayerFundsView.getNode(),myPlayerPropertiesView.getNode(),myPlayerCardsView.getNode());
        myOptionsViewNode.setLeft(aBox);
    }

    private void makeNonActionViews() {
        myLogView = new LogView(myData);
        VBox NonActionBox = new VBox();
        NonActionBox.getChildren().addAll(myLogView.getNode());
        myOptionsViewNode.setTop(NonActionBox);
    }

    /**
     * Creates all necessary user controls/buttons from the
     * given actionMap
     * @param actionMap     mapping of necessary button handlers to set
     */
    @Override
    public void createButtons(Map<String, EventHandler<ActionEvent>> actionMap) {
        playerOptionsModal = new VBox();
        playerOptionsModal.setSpacing(10);
        playerOptionsModal.getChildren().add(makeRollDisplay());
        for(String action:actionMap.keySet()){
            makeButton(action,actionMap.get(action),playerOptionsModal);
        }
        playerOptionsModal.getChildren().add(makeMoveCheatKey());
        myOptionsViewNode.setCenter(playerOptionsModal);
    }

    private Node makeRollDisplay() {
        myDiceView = new DiceView(2,6);
//                myGame.getBoard().getNumDie(),
//                myGame.getMyDice().getNumStates());

        return myDiceView;
    }

    private Node makeMoveCheatKey() {
        TextField movesField = new TextField();
        movesField.setPrefHeight(30);
        movesField.setMaxWidth(60);
        movesField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String toReplace, String newStr) {
                if (! newStr.matches("\\d*"))
                    movesField.setText(newStr.replaceAll("[^\\d]", ""));
            }
        });
//        getMoveCheatNumMoves(movesField);
        return movesField;
    }

    private void makeButton(String action, EventHandler<ActionEvent> handler, VBox box) {
        var buttonView = new Button();
        buttonView.setText(action);
        buttonView.setOnAction(handler);
        box.getChildren().add(buttonView);
        myControls.put(action, buttonView);
    }

    /**
     * Disables the user control (grey out)
     * @param control
     */
    @Override
    public void disableControl(String control) {
        Control selected = myControls.get(control);
        selected.setDisable(true);
    }

    /**
     * Enables a grey-out user control
     * @param control
     */
    @Override
    public void enableControl(String control) {
        Control selected = myControls.get(control);
        selected.setDisable(false);
    }

    /**
     * Getter of the Node of the VBoxOptionsView
     * @return Node         the internal VBox
     */
    @Override
    public Node getOptionsViewNode() {
        return myOptionsViewNode;
    }

    @Override
    public void updateDice(Turn turn) {
        myDiceView.onUpdate(turn);
        myLogView.gameLog.setText(myDiceView.getMyPopupText());
    }

    @Override
    public void updateAssetDisplay(List<AbstractPlayer> myPlayerList) {
        myPlayerPropertiesView.update(myPlayerList);
        myPlayerFundsView.update(myPlayerList);
        myPlayerCardsView.update(myPlayerList);
    }

//    public int getMoveCheatNumMoves(TextField movesField) {
//        return Integer.parseInt(movesField.getText());
//    }
}
