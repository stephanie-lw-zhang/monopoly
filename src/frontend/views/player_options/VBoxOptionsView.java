package frontend.views.player_options;

import frontend.views.game.AbstractGameView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class VBoxOptionsView extends AbstractOptionsView {
    private VBox myOptionsViewNode;
    private AbstractGameView myGameView;
    private Map<String,Control> myControls = new HashMap<>();

    public VBoxOptionsView(AbstractGameView gameView){
        super(gameView);
        myGameView = gameView;
        makeNode();
    }

    private void makeNode() {
        VBox playerOptionsModal = new VBox();
        playerOptionsModal.setSpacing(10);
        myOptionsViewNode=playerOptionsModal;
    }

    @Override
    public void createButtons(Map<String, EventHandler<ActionEvent>> actionMap) {
        for(String action:actionMap.keySet()){
            makeButton(action,actionMap.get(action));
        }
    }

    private void makeButton(String action, EventHandler<ActionEvent> handler) {
        var buttonView = new Button();
        buttonView.setText(action);
        buttonView.setOnAction(handler);
        myOptionsViewNode.getChildren().add(buttonView);
        myControls.put(action, buttonView);
    }

    @Override
    public void disableControl(String control) {
        Control selected = myControls.get(control);
        selected.setDisable(false);
    }

    @Override
    public void enableControl(String control) {
        Control selected = myControls.get(control);
        selected.setDisable(true);
    }

    @Override
    public Node getOptionsViewNode() {
        return myOptionsViewNode;
    }
}
