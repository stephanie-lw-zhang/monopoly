package frontend.views.player_stats;

import backend.assetholder.AbstractPlayer;
import javafx.scene.control.Control;

import java.util.List;

public interface StatsView {

    Control create(List<AbstractPlayer> playerList);

    void update(List<AbstractPlayer> playerList);
}
