package frontend.screens;

import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Extends AbstractScreen. Represents the instructions screen rendered for any user help
 *
 * @author Sam
 */
public class InstructionsScreen extends AbstractScreen {
    public InstructionsScreen(double sWidth, double sHeight, Stage stage, AbstractScreen parent) {
        super (sWidth, sHeight, stage,parent);
    }

    @Override
    public void changeDisplayNode(Node n) {

    }

}
