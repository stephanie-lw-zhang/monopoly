package frontend.views.player_stats;

import backend.assetholder.AbstractPlayer;
import javafx.scene.control.Control;

import java.util.List;

public interface StatsView {

    void update(List<AbstractPlayer> playerList);
}
