package frontend.views.player_stats;

import backend.assetholder.AbstractPlayer;
import javafx.scene.control.TextArea;

import java.util.List;

public class PlayerFundsView {
    TextArea fundsDisplay;

    private TextArea createPlayerFundsDisplay(List<AbstractPlayer> playerList){
        fundsDisplay = new TextArea( );
        updatePlayerFundsDisplay(playerList);
        fundsDisplay.setMaxHeight( 150 );
        fundsDisplay.setMaxWidth( 200 );
        fundsDisplay.setStyle( "-fx-background-color: white" );
        return fundsDisplay;
    }

    private void updatePlayerFundsDisplay(List<AbstractPlayer> playerList){
        String text = "Player Funds \n";
        for(AbstractPlayer p: playerList ){
            text = text + p.getMyPlayerName() + ": " + p.getMoney() + "\n";
        }
        fundsDisplay.setText( text );
    }
}
