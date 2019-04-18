package BackEnd.Tile;

import BackEnd.AssetHolder.AbstractAssetHolder;
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.PropertyCard;

import Controller.Actions;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractPropertyTile extends Tile {

    private String tiletype;
//    private double tileprice;
    private boolean mortgaged;
    private Bank bank;
    private AbstractAssetHolder owner;
    private PropertyCard card;
    private String currentInUpgradeOrder;

    public AbstractPropertyTile(Bank bank, PropertyCard card, String tiletype, double tileprice) {
        this.owner = bank;
        this.bank = bank;
        //throw exception if card is not propertycard type
        this.card = card;
        this.tiletype = tiletype;
//        this.tileprice = tileprice;
        this.mortgaged = false;
        currentInUpgradeOrder = this.card.getUpgradeOrderAtIndex(0);
    }

    public AbstractPropertyTile(Element n, Bank bank){
        //TODO finish this implementation
        this.bank = bank;
        tiletype = getTagValue("TileName", n);
//        tileprice = Double.parseDouble(getTagValue("TilePrice", n));
    }

    //fix this
    @Override
    public List<Actions> applyLandedOnAction(AbstractPlayer player) {
        List<Actions> possibleActions = new ArrayList<>(  );

//        //controller will send player option to buy property? interact with front-end
        if (getOwner() instanceof Bank) {
            possibleActions.add(Actions.BUY);
            possibleActions.add(Actions.AUCTION);
        }
        else if (!player.equals(getOwner())) {
            possibleActions.add(Actions.PAY_RENT);
//            player.paysTo(getOwner(), calculateRentPrice());
        }
        return possibleActions;
    }

    //ONLY SOME PROPERTIES CAN BE SOLD BACK TO BANK
//    public abstract double sellToBankPrice();
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
        assetHolder.paysTo( owner, price );
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

    // If the mortgage if not lifted at once, you must pay the Bank
    // 10% interest when you buy the property and if you lift the mortgage
    // later you must pay the Bank an additional 10% interest as well as the amount of the mortgage.
    public void soldMortgagedPropertyLaterUnmortgages() {
        if (isMortgaged()) {
            owner.paysTo(bank, ((PropertyCard) card).getMortgageValue() * 0.1);
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

    // maybe make an abstractTile class instead of an Tile
    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    //    public boolean isRentNeeded(AbstractPlayer player) {
//        return (!player.equals(getOwner()) && !mortgaged);
//    }

    //    public void buyPropertyFromPlayer(AbstractPlayer player, double price) {
//        player.paysTo(owner,price);
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
//            player.paysTo( bank, tileprice );
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