package frontend.views.player_stats;

import backend.assetholder.AbstractPlayer;
import backend.tile.AbstractPropertyTile;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

import java.util.List;

public class PlayerPropertiesView extends AbstractPlayerTabView implements StatsView{

    public PlayerPropertiesView(List<AbstractPlayer> playerList){
        super(playerList);
    }

    @Override
    public void writeText(AbstractPlayer player, Tab tab){
        TextArea properties = new TextArea();
        String text = "Properties \n";
        for(AbstractPropertyTile prop: player.getProperties()){
            if(prop.isMortgaged()){
                text += prop.getName() + " (Mortgaged)\n";
            } else {
                text += prop.getName() + "\n";
            }
        }
        properties.setText( text );
        tab.setContent( properties );
    }

}
