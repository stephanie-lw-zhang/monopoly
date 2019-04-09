package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.AbstractAssetHolder;
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;
import BackEnd.Controller.Game;
import BackEnd.Card.PropertyCard;
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
//        //controller will send player option to buy property? interact with front-end
//        if (getOwner() instanceof Bank) {
//            if (true) {
//                buyProperty(player);
//            }
////            else {
////                auctionProperty();
////            }
//        }
//        else if (!player.equals(getOwner())) {
//            player.paysTo(getOwner(), calculateRentPrice());
//        }
        return;
    }


    @Override
    public void applyPassedAction(AbstractPlayer player) {
        return;
    }

    public double sellToBankPrice() {
        if (!isMortgaged()) {
            return tileprice/2;
        }
        else {
            //throw exception: CANNOT SELL MORTGAGED PROPERTY BACK TO BANK
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
        //in controller, we first check if player can buy property --> sends to front-end
//        if(player.getMoney() < tileprice) {
//            //THROW EXCEPTION: CANNOT BUY PROPERTY WITH CURRENT AMOUNT OF MONEY
//            //maybe options to sell other properties or mortgage or something from front end?
//        }
        player.addProperty(this);
        player.paysTo( owner, tileprice );
        switchOwner(player);
    }

    public abstract double calculateRentPrice(Game game);

    public Bank getBank() {
        return bank;
    }

    public void mortgageProperty() {
        //need to turn over card on front end
        if (!isMortgaged()) {
            if(card instanceof PropertyCard) {
                bank.paysTo(owner, ((PropertyCard) card).getMortgageValue() );
                this.mortgaged = true;
            }
        }
        else {
            //throw exception: HOUSE IS ALREADY MORTGAGED
        }
    }

    public void unmortgageProperty() {
        if (isMortgaged()) {
            if (card instanceof PropertyCard) {
                owner.paysTo(bank, ((PropertyCard) card).getMortgageValue() * 1.1);
                this.mortgaged = false;
            }
        }
        else {
            //throw exception: HOUSE IS NOT MORTGAGED
        }
    }

    // If you are the new owner, you may lift the mortgage at once if you wish by paying
    // off the mortgage plus 10% interest to the Bank.
    // TO DO: If the mortgage if not lifted at once, you must pay the Bank
    // 10% interest when you buy the property and if you lift the mortgage later you must pay the Bank an additional 10% interest as well as the amount of the mortgage.
    public void sellMortgagedPropertyToAnotherPlayer(AbstractPlayer player, double price) {
        if (isMortgaged()) {
            player.paysTo(owner,price);
            player.paysTo(bank,((PropertyCard)card).getMortgageValue() * 1.1);
            switchOwner(player);
        }
        else {
            //throw exception: HOUSE IS NOT MORTGAGED
        }
    }

    public boolean isBuyable(){
        return owner.equals(bank);
    }

    public boolean isRentNeeded(AbstractPlayer player) {
        return (!player.equals(getOwner()) && !mortgaged);
    }

//    public void auctionProperty() {
//        //interact with front-end ?
//        double maxMoney = 0;
//        if ()
//    }

}
