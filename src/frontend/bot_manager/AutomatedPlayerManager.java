package frontend.bot_manager;

import backend.assetholder.AutomatedPlayer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import frontend.screens.TestingScreen;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import org.testfx.util.WaitForAsyncUtils;

public class AutomatedPlayerManager {
    private  Button ROLL_BUTTON;
    private  Button END_TURN_BUTTON;
    private  Button TRADE_BUTTON;
    private  Button MORTGAGE_BUTTON;
    private  Button MOVE_BUTTON;
    private  Button COLLECT_BUTTON;
    private  Button PAY_BAIL_BUTTON;
    private  Button FORFEIT_BUTTON;
    private  Button MOVE_HANDLER_BUTTON;
    private  Button UNMORTGAGE_BUTTON;
    private  Button SELL_TO_BANK;
    private  Button UPGRADE;
    private  VBox   buttons;

    public AutomatedPlayerManager(Screen screen) {

//        buttons=TestingScreen.getVBox(playerOptionsModal);
//         ROLL_BUTTON = TestingScreen.getVBox();

    }

    public void autoPlayerTurn(Node n) {
        //find roll button
        //myButton = lookup().queryButton("roll");
        //go to roll button
        //click roll button
        //execute roll
        //buy/mortgage/sell/auction/trade logic
        //end turn
        //
    }


    // extra utility methods for different UI components
    protected void clickOn (Button b) {
        simulateAction(b, () -> b.fire());
    }

    private void simulateAction (Node n, Runnable action) {
        // simulate robot motion
        //moveTo(n);  - FIX THIS
        // fire event using given action on the given node
        Platform.runLater(action);
        // make it "later" so the requested event has time to run
        WaitForAsyncUtils.waitForFxEvents();
    }
}
