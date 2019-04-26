package frontend.bot_manager;

import backend.assetholder.AutomatedPlayer;
import javafx.scene.control.Button;
import frontend.screens.TestingScreen;
import javafx.scene.layout.VBox;

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

    public AutomatedPlayerManager(VBox buttonHolder) {
        
         ROLL_BUTTON = new Button("ROLL");
         END_TURN_BUTTON = new Button("END TURN");
         TRADE_BUTTON = new Button("TRADE");
         MORTGAGE_BUTTON = new Button("MORTGAGE");
         MOVE_BUTTON = new Button("MOVE");
         COLLECT_BUTTON = new Button("COLLECT");
         PAY_BAIL_BUTTON = new Button("Pay Bail");
         FORFEIT_BUTTON = new Button("Forfeit");
         MOVE_HANDLER_BUTTON = new Button("Move handler");
         UNMORTGAGE_BUTTON = new Button("Unmortgage");
         SELL_TO_BANK = new Button("SELL TO BANK");
         UPGRADE = new Button("UPGRADE");
    }
}
