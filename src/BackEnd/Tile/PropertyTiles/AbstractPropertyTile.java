package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.AbstractAssetHolder;
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;
import BackEnd.Tile.TileInterface;

public abstract class AbstractPropertyTile implements TileInterface {

    private String tiletype;
    private double tileprice;
    private boolean mortgaged;
    private Bank bank;
    private AbstractAssetHolder owner;
    private AbstractCard card;

    public AbstractPropertyTile(Bank bank, AbstractCard card, String tiletype, double tileprice) {
        this.owner = bank;
        this.bank = bank;
        this.card = card;
        this.tiletype = tiletype;
        this.tileprice = tileprice;
        this.mortgaged = false;
    }

    //fix this
    @Override
    public void applyLandedOnAction(AbstractPlayer player) {
        //controller will send player option to buy property? interact with front-end
        if (getOwner() instanceof Bank) {
            if (true) {
                buyProperty(player);
            }
//            else {
//                auctionProperty();
//            }
        }
        else if (!player.equals(getOwner())) {
            player.paysTo(getOwner(),priceToPay());
        }
    }

//    @Override
//    public void applyPassedAction(AbstractPlayer player) {
//        return;
//    }

    public double sellToBankPrice() {
        if (!isMortgaged()) {
            return tileprice/2;
        }
        else {
            //throw exception?
        }
        return 0;
    }

    public void switchOwner(AbstractAssetHolder player) {
        this.owner = player;
    }

    public AbstractCard getCard() {
        return card;
    }

    public AbstractAssetHolder getOwner() {
        return owner;
    }

    public boolean isMortgaged() {
        return mortgaged;
    }

    public void buyProperty(AbstractPlayer player) {
        if(player.getMoney() < tileprice) {
            //THROW EXCEPTION
        }
        else{
            player.addProperty(this);
            player.paysTo( owner, tileprice );
            switchOwner(player);
        }
    }

    public abstract double priceToPay();

    public Bank getBank() {
        return bank;
    }

//    public void auctionProperty() {
//        //interact with front-end ?
//        double maxMoney = 0;
//        if ()
//    }
}
