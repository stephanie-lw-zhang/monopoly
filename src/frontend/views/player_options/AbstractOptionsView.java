package frontend.views.player_options;

import frontend.views.game.AbstractGameView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

import java.util.Map;

abstract public class AbstractOptionsView {

    public AbstractOptionsView(AbstractGameView gameView){}
    abstract public void createButtons(Map<String, EventHandler<ActionEvent>> actionMap);
    abstract public void disableControl(String control);
    abstract public void enableControl(String control);
    abstract public Node getOptionsViewNode();
}
