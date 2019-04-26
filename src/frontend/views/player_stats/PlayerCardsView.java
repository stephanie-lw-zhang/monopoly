package frontend.views.player_stats;

import backend.assetholder.AbstractPlayer;
import backend.card.action_cards.HoldableCard;
import backend.tile.AbstractPropertyTile;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

public class PlayerCardsView extends AbstractPlayerTabView {
    @Override
    public void writeText(AbstractPlayer player, Tab tab) {
        TextArea cards = new TextArea();
        String text = "";
        for(HoldableCard card: player.getCards()){
            text += card.getName() + " \n";
        }
        cards.setText( text );
        tab.setContent( cards );
    }
}

