package frontend.views.player_stats;

import backend.assetholder.AbstractPlayer;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

import java.util.List;

public class PlayerFundsView implements StatsView {
    TextArea fundsDisplay;

    public PlayerFundsView(List<AbstractPlayer> playerList){
        fundsDisplay = new TextArea( );
        update(playerList);
        fundsDisplay.setMaxHeight( 150 );
        fundsDisplay.setMaxWidth( 200 );
        fundsDisplay.setStyle( "-fx-background-color: white" );
    }

    public void update(List<AbstractPlayer> playerList){
        String text = "Player Funds \n";
        for(AbstractPlayer p: playerList ){
            text = text + p.getMyPlayerName() + ": " + p.getMoney() + "\n";
        }
        fundsDisplay.setText( text );
    }


    public Node getNode() {
        return fundsDisplay;
    }
}
