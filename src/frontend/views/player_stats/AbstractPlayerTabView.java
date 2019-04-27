package frontend.views.player_stats;

import backend.assetholder.AbstractPlayer;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.List;

public abstract class AbstractPlayerTabView implements StatsView {
    private TabPane tabPane;

    public AbstractPlayerTabView(List<AbstractPlayer> playerList){
        tabPane = new TabPane();
        for(AbstractPlayer p: playerList){
            Tab tab = new Tab(p.getMyPlayerName());
            tab.setId( p.getMyPlayerName() );
            writeText( p, tab );
            tabPane.getTabs().add( tab );
        }
        tabPane.setMaxHeight( 200 );
        tabPane.setMaxWidth( 200 );
        tabPane.setTabClosingPolicy( TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    private AbstractPlayer getPlayerFromName(List<AbstractPlayer> playerList, String name){
        for(AbstractPlayer p: playerList){
            if (p.getMyPlayerName().equalsIgnoreCase( name )){
                return p;
            }
        }
        return null;
    }

    public void update(List<AbstractPlayer> playerList, AbstractPlayer forfeiter) {
        if(forfeiter != null){
            for(Tab tab: getTabs()){
                if(tab.getText().equalsIgnoreCase( forfeiter.getMyPlayerName() )){
                    getTabs().remove(tab);
                }
            }
        } else{
            for(Tab tab: tabPane.getTabs()){
                AbstractPlayer player = getPlayerFromName( playerList, tab.getText() );
                writeText(player, tab);
            }
        }

    }

    public abstract void writeText(AbstractPlayer player, Tab tab);

    public List<Tab> getTabs(){
        return tabPane.getTabs();
    }

    public Node getNode(){
        return tabPane;
    }
}
