package frontend.views.player_stats;

import backend.assetholder.AbstractPlayer;
import backend.exceptions.PlayerDoesNotExistException;
import backend.tile.AbstractPropertyTile;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

public class PlayerPropertiesView {
    private TabPane allPlayerProperties;

    private TabPane createPlayerPropertiesDisplay(List<AbstractPlayer> playerList){
        allPlayerProperties = new TabPane( );
        for(AbstractPlayer p: playerList){
            Tab tab = new Tab(p.getMyPlayerName());
            tab.setId( p.getMyPlayerName() );
            writeInPlayerProperties( p, tab );
            allPlayerProperties.getTabs().add( tab );
        }
        allPlayerProperties.setMaxHeight( 200 );
        allPlayerProperties.setMaxWidth( 200 );
        allPlayerProperties.setTabClosingPolicy( TabPane.TabClosingPolicy.UNAVAILABLE);
        return allPlayerProperties;
    }

    private AbstractPlayer getPlayerFromName(List<AbstractPlayer> playerList, String name){
        for(AbstractPlayer p: playerList){
            if (p.getMyPlayerName().equalsIgnoreCase( name )){
                return p;
            }
        }
        return null;
    }

    private void updatePlayerPropertiesDisplay(List<AbstractPlayer> playerList) {
            for(Tab tab: allPlayerProperties.getTabs()){
                AbstractPlayer player = getPlayerFromName( playerList, tab.getText() );
                writeInPlayerProperties(player, tab);
            }
    }

    private void writeInPlayerProperties(AbstractPlayer player, Tab tab){
        TextArea properties = new TextArea();
        String text = "";
        for(AbstractPropertyTile prop: player.getProperties()){
            text = text + prop.getName() + "\n";
        }
        properties.setText( text );
        tab.setContent( properties );
    }
}
