package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.AbstractAssetHolder;
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;
import BackEnd.Tile.TileInterface;

public abstract class AbstractPropertyTile implements TileInterface {

    private String tiletype;
    private double tileprice;
    private boolean mortaged;
    private AbstractAssetHolder owner;
    private AbstractCard card;

    public AbstractPropertyTile(Bank bank, AbstractCard card, String tiletype, double tileprice) {
        this.owner = bank;
        this.card = card;
        this.tiletype = tiletype;
        this.tileprice = tileprice;
        this.mortaged = false;
    }

    @Override
    public void applyPassedAction(AbstractPlayer player) {
        return;
    }

    public abstract double sellToBankPrice();

    public void switchOwner(AbstractAssetHolder player) {
        this.owner = player;
    }

    public AbstractCard getCard() {
        return card;
    }

    public AbstractAssetHolder getOwner() {
        return owner;
    }

    public boolean isMortaged() {
        return mortaged;
    }

    public void buyProperty(AbstractPlayer player) {
        player.addProperty(this);
        switchOwner(player);
    }

    public double getTileprice(){
        return tileprice;
        //make immutable??
    }

//    public void auctionProperty() {
//        //interact with front-end ?
//        double maxMoney = 0;
//        if ()
//    }
}
