package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;
import BackEnd.Card.RailroadCard;

public class RailroadTile extends AbstractPropertyTile {

    private RailroadCard card;

    public RailroadTile(Bank bank, AbstractCard card, String tiletype, double tileprice) {
        super(bank, card, tiletype, tileprice);
        this.card = (RailroadCard)card;
    }

    public double priceToPay() {
        int railroadsOwned = 0;
        for(AbstractPropertyTile property: this.getOwner().getProperties()){
            if(property instanceof RailroadTile){
                railroadsOwned += 1;
            }
        }
        if (railroadsOwned == 1) {
            return card.getRailroadRent1Owned();
        }
        else if (railroadsOwned == 2) {
            return card.getRailroadRent2Owned();
        }
        else if (railroadsOwned == 3) {
            return card.getRailroadRent3Owned();
        }
        else if (railroadsOwned == 4) {
            return card.getRailroadrent4Owned();
        }
        else {
            return 0;
        }
    }
}
