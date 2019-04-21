package backend.tile;

import backend.assetholder.AbstractAssetHolder;
import backend.assetholder.AbstractPlayer;
import backend.assetholder.Bank;
import backend.card.PropertyCard;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractPropertyTile extends Tile {

    private String tiletype;
    //private double tileprice;
    private boolean mortgaged;
    private Bank bank;
    private AbstractAssetHolder owner;
    private PropertyCard card;
    private String currentInUpgradeOrder;
    private int index;

    public AbstractPropertyTile(Bank bank, PropertyCard card, String tiletype, double tileprice, int index) {
        this.owner = bank;
        this.bank = bank;
        //throw exception if card is not propertycard type
        this.card = card;
        this.tiletype = tiletype;
//        this.tileprice = tileprice;
        this.mortgaged = false;
        currentInUpgradeOrder = this.card.getUpgradeOrderAtIndex(0);
        this.index =index;
    }

    public AbstractPropertyTile(Bank bank, Element n){
        this.owner = bank;
        this.bank = bank;
        card = new PropertyCard(n.getElementsByTagName("Card").item(0));
        tiletype = getTagValue("TileType", n);
      //  tileprice = Double.parseDouble(getTagValue("TilePrice", n));
        setTileIndex(Integer.parseInt(getTagValue("TileNumber", n)));
        this.mortgaged = false;
        currentInUpgradeOrder = this.card.getUpgradeOrderAtIndex(0);

//        tileprice = Double.parseDouble(getTagValue("TilePrice", n));
    }

    //fix this
    @Override
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        List<String> possibleActions = new ArrayList<>(  );

//        //controller will send player option to buy property? interact with front-end
        if (getOwner().equals( bank )) {
            possibleActions.add("buy");
            possibleActions.add("auction");
        }
        else if (!player.equals(getOwner())) {
            possibleActions.add("payRent");
<<<<<<< HEAD
<<<<<<< HEAD
//            player.paysFullAmountTo(getOwner(), calculateRentPrice());
=======
//            player.payFullAmountTo(getOwner(), calculateRentPrice());
>>>>>>> 14f2f2b37ae82b8302118ba2ecba0554310e8564
=======
//            player.payFullAmountTo(getOwner(), calculateRentPrice());
>>>>>>> 9a9c68a39301a66f4dfefd6560b6893148453277
        }
        return possibleActions;
    }

    //ONLY SOME PROPERTIES CAN BE SOLD BACK TO BANK
//    public abstract double sellBuildingToBankPrice();
//        if (!isMortgaged()) {
////            return tileprice/2;
//            getCard().
//        }
//        else {
//            //throw exception: CANNOT SELL_TO_BANK MORTGAGED PROPERTY BACK TO BANK
//        }
//        return 0;
//    }

    public void switchOwner(AbstractAssetHolder player) {
        this.owner = player;
    }

    public PropertyCard getCard() {
        return card;
    }

    public AbstractAssetHolder getOwner() {
        return owner;
    }

    public boolean isMortgaged() {
        return mortgaged;
    }

    /**
     * IN CONTROLLER: First check if (isBuyable) --> if true, sellTo(player,tileprice)
     * else --> getAgreedPrice (from front end), then call sellTo(player, agreedPrice)
     * --> then need to check if (want to lift mortgage immediately), then call unmortgageProperty()
     * --> else, call soldMortgagedPropertyLaterUnmortgages()
     */
    public void sellTo(AbstractAssetHolder assetHolder, double price, List<AbstractPropertyTile> sameSetProperties) {
        assetHolder.addProperty(this);
<<<<<<< HEAD
<<<<<<< HEAD
        assetHolder.paysFullAmountTo( owner, price );
=======
        assetHolder.payFullAmountTo( owner, price );
>>>>>>> 14f2f2b37ae82b8302118ba2ecba0554310e8564
=======
        assetHolder.payFullAmountTo( owner, price );
>>>>>>> 9a9c68a39301a66f4dfefd6560b6893148453277
        owner.getProperties().remove(this);
        switchOwner(assetHolder);
    }

    public abstract double calculateRentPrice(int roll);

    public Bank getBank() {
        return bank;
    }

    public void mortgageProperty() {
        //need to turn over card on front end
        if (!isMortgaged()) {
<<<<<<< HEAD
<<<<<<< HEAD
                bank.paysFullAmountTo(owner, ((PropertyCard) card).getMortgageValue() );
=======
                bank.payFullAmountTo(owner, ((PropertyCard) card).getMortgageValue() );
>>>>>>> 14f2f2b37ae82b8302118ba2ecba0554310e8564
=======
                bank.payFullAmountTo(owner, ((PropertyCard) card).getMortgageValue() );
>>>>>>> 9a9c68a39301a66f4dfefd6560b6893148453277
                this.mortgaged = true;
        }
        else {
            //throw exception: HOUSE IS ALREADY MORTGAGED
        }
    }

    public void unmortgageProperty() {
        if (isMortgaged()) {
<<<<<<< HEAD
<<<<<<< HEAD
                owner.paysFullAmountTo(bank, ((PropertyCard) card).getMortgageValue() * 1.1);
=======
                owner.payFullAmountTo(bank, ((PropertyCard) card).getMortgageValue() * 1.1);
>>>>>>> 14f2f2b37ae82b8302118ba2ecba0554310e8564
=======
                owner.payFullAmountTo(bank, ((PropertyCard) card).getMortgageValue() * 1.1);
>>>>>>> 9a9c68a39301a66f4dfefd6560b6893148453277
                this.mortgaged = false;
        }
        else {
            //throw exception: HOUSE IS NOT MORTGAGED
        }
    }

    // If the mortgage if not lifted at once, you must pay the Bank
    // 10% interest when you buy the property and if you lift the mortgage
    // later you must pay the Bank an additional 10% interest as well as the amount of the mortgage.
    public void soldMortgagedPropertyLaterUnmortgages() {
        if (isMortgaged()) {
<<<<<<< HEAD
<<<<<<< HEAD
            owner.paysFullAmountTo(bank, ((PropertyCard) card).getMortgageValue() * 0.1);
=======
            owner.payFullAmountTo(bank, ((PropertyCard) card).getMortgageValue() * 0.1);
>>>>>>> 14f2f2b37ae82b8302118ba2ecba0554310e8564
=======
            owner.payFullAmountTo(bank, ((PropertyCard) card).getMortgageValue() * 0.1);
>>>>>>> 9a9c68a39301a66f4dfefd6560b6893148453277
        }
        else {
            //throw exception: HOUSE IS NOT MORTGAGED
        }
    }

    public boolean isBuyableFromBank(){
        return owner.equals(bank);
    }

    /**
     * front-end will ask players to all input a value at once (simple implementation)
     * this method will take the max
     * https://stackoverflow.com/questions/5911174/finding-key-associated-with-max-value-in-a-java-map
     */
    public Map.Entry<AbstractPlayer, Double> determineAuctionResults(Map<AbstractPlayer,Double> auctionBidValues) {
        //assume first person in list to tie will win if there is a tie
        Map.Entry<AbstractPlayer, Double> maxEntry = null;
        for (Map.Entry<AbstractPlayer, Double> entry : auctionBidValues.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        return maxEntry;
    }

    public double getWinningBid(Map<AbstractPlayer,Double> auctionBidValues){
        return determineAuctionResults(auctionBidValues).getValue();
    }

    public AbstractPlayer getAuctionWinner(Map<AbstractPlayer,Double> auctionBidValues){
        return determineAuctionResults(auctionBidValues).getKey();
    }

    public String getCurrentInUpgradeOrder() {
        return currentInUpgradeOrder;
    }

    public void setCurrentInUpgradeOrder(String newOrder) {
        currentInUpgradeOrder = newOrder;
    }

    public void recalculateTotalPropertiesLeftOneBuildingUpdate(Map<String,Integer> totalPropertiesLeft){
        //do nothing
    }

    public void recalculateTotalPropertiesLeftAfterWholeSale(Map<String,Integer> totalPropertiesLeft){
        //do nothing
    }

    public String getTitleDeed() {
        return card.getTitleDeed();
    }


    // maybe make an abstractTile class instead of an tile
    //private String getTagValue(String tag, Element element) {
    //    NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
    //    Node node = nodeList.item(0);
    //    return node.getNodeValue();
    //}

    //    public boolean isRentNeeded(AbstractPlayer player) {
//        return (!player.equals(getOwner()) && !mortgaged);
//    }

    //    public void buyPropertyFromPlayer(AbstractPlayer player, double price) {
<<<<<<< HEAD
<<<<<<< HEAD
//        player.paysFullAmountTo(owner,price);
=======
//        player.payFullAmountTo(owner,price);
>>>>>>> 14f2f2b37ae82b8302118ba2ecba0554310e8564
=======
//        player.payFullAmountTo(owner,price);
>>>>>>> 9a9c68a39301a66f4dfefd6560b6893148453277
//        switchOwner(player);
//        player.addProperty(this);
//    }

    //    public void buyPropertyFromBank(AbstractPlayer player) {
//        //in controller, we first check if player can buy property --> sends to front-end
////        if(player.getMoney() < tileprice) {
////            //THROW EXCEPTION: CANNOT BUY PROPERTY WITH CURRENT AMOUNT OF MONEY
////            //maybe options to sell other properties or mortgage or something from front end?
////        }
//        if (owner.equals(bank)) {
//            player.addProperty(this);
<<<<<<< HEAD
<<<<<<< HEAD
//            player.paysFullAmountTo( bank, tileprice );
=======
//            player.payFullAmountTo( bank, tileprice );
>>>>>>> 14f2f2b37ae82b8302118ba2ecba0554310e8564
=======
//            player.payFullAmountTo( bank, tileprice );
>>>>>>> 9a9c68a39301a66f4dfefd6560b6893148453277
//            switchOwner(player);
//        }
//        else {
//            //throw exception: CAN'T BUY BECAUSE BANK DOESN'T OWN
//        }
//    }


    // If you are the new owner, you may lift the mortgage at once if you wish by paying
    // off the mortgage plus 10% interest to the Bank.
//       public void soldMortgagedPropertyImmediatelyUnmortgages(AbstractPlayer player, double price) {
//        if (isMortgaged()) {
//            unmortgageProperty();
//        }
//        else {
//            //throw exception: HOUSE IS NOT MORTGAGED
//        }
//    }
}
