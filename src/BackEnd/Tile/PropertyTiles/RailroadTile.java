package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;
import BackEnd.Card.RailroadCard;
import Controller.Game;
import org.w3c.dom.Element;

public class RailroadTile extends AbstractPropertyTile {

    private RailroadCard card;
    private String currentInUpgradeOrder;


    public RailroadTile(Bank bank, AbstractCard card, String tiletype, double tileprice) {
        super(bank, card, tiletype, tileprice);
        this.card = (RailroadCard)card;
        currentInUpgradeOrder = this.card.getUpgradeOrderAtIndex(0);
    }

    public RailroadTile(Element n){
        super(n);
    }

    public double calculateRentPrice(Game game) {
        if (isMortgaged()) {
            return 0;
        }
        else {
            return card.lookupPrice(currentInUpgradeOrder);
        }
    }
}
