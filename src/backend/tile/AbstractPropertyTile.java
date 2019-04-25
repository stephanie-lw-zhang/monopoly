package backend.tile;

import backend.assetholder.AbstractAssetHolder;
import backend.assetholder.AbstractPlayer;
import backend.assetholder.Bank;
import backend.card.PropertyCard;

import exception.IllegalActionOnImprovedPropertyException;
import exception.MortgagePropertyException;
import exception.IllegalInputTypeException;
import exception.OutOfBuildingStructureException;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractPropertyTile extends Tile {

    private String tileType;
    private boolean mortgaged;
    private Bank bank;
    private AbstractAssetHolder owner;
    private PropertyCard card;
    private String currentInUpgradeOrder;
    private int index;

    public AbstractPropertyTile(Bank bank, PropertyCard card, String tileType, int index) {
        this.owner = bank;
        this.bank = bank;
        //throw exception if card is not propertycard type
        this.card = card;
        this.tileType = tileType;
        this.mortgaged = false;
        currentInUpgradeOrder = this.card.getUpgradeOrderAtIndex(0);
        this.index =index;
    }

    public AbstractPropertyTile(Bank bank, Element n){
        this.owner = bank;
        this.bank = bank;
//        card = getCard();
        card = new PropertyCard(n.getElementsByTagName("Card").item(0));
        setTileType(getTagValue("TileType", n));
        setTileIndex(Integer.parseInt(getTagValue("TileNumber", n)));
        this.mortgaged = false;
        currentInUpgradeOrder = this.card.getUpgradeOrderAtIndex(0);
    }

    //fix this
    @Override
    public List<String> applyLandedOnAction(AbstractPlayer player) {
        List<String> possibleActions = new ArrayList<>(  );
        if (isBuyableFromBank()) {
            possibleActions.add("BUY");
            possibleActions.add("AUCTION");
        }
        else if (!player.equals(getOwner())) {
            possibleActions.add("PAY RENT");
        }
        return possibleActions;
    }

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
    public void sellTo(AbstractAssetHolder assetHolder, double price, List<AbstractPropertyTile> sameSetProperties) throws IllegalActionOnImprovedPropertyException, IllegalInputTypeException, OutOfBuildingStructureException {
        assetHolder.addProperty(this);
        assetHolder.payFullAmountTo( owner, price );
        owner.getProperties().remove(this);
        switchOwner(assetHolder);
    }

    public abstract double calculateRentPrice(int roll);

    public Bank getBank() {
        return bank;
    }

    public void mortgageProperty() throws MortgagePropertyException, IllegalActionOnImprovedPropertyException {
        if (!isMortgaged()) {
                bank.payFullAmountTo(owner, card.getMortgageValue() );
                this.mortgaged = true;
        }
        else {
            throw new MortgagePropertyException("Property is already mortgaged!");
        }
    }

    public void unmortgageProperty() throws MortgagePropertyException {
        if (isMortgaged()) {
            //TODO: NEED CONFIG
            owner.payFullAmountTo(bank, card.getMortgageValue() * 1.1);
            this.mortgaged = false;
        }
        else {
            throw new MortgagePropertyException("Property is not mortgaged!");
        }
    }

    // If the mortgage if not lifted at once, you must pay the Bank
    // 10% interest when you buy the property and if you lift the mortgage
    // later you must pay the Bank an additional 10% interest as well as the amount of the mortgage.
    public void soldMortgagedPropertyLaterUnmortgages() throws MortgagePropertyException {
        if (isMortgaged()) {
            //TODO: NEED CONFIG
            owner.payFullAmountTo(bank, getCard().getMortgageValue() * 0.1);
        }
        else {
            throw new MortgagePropertyException("Property is not mortgaged!");
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

    public void recalculateTotalPropertiesLeftOneBuildingUpdate(Map<String,Integer> totalPropertiesLeft, boolean upgrade){
        //do nothing
    }

    public void recalculateTotalPropertiesLeftAfterWholeSale(Map<String,Integer> totalPropertiesLeft){
        //do nothing
    }

    public String getTitleDeed() {
        return card.getTitleDeed();
    }

    public double getTilePrice() {
        return card.getTilePrice();
    }

    public void setCard(PropertyCard c) {
        card = c;
    }

    public void setMortgaged(boolean b) {
        this.mortgaged = b;
    }

    @Override
    public boolean isPropertyTile() {
        return true;
    }

    //    public boolean isRentNeeded(AbstractPlayer player) {
//        return (!player.equals(getOwner()) && !mortgaged);
//    }

    //    public void buyPropertyFromBank(AbstractPlayer player) {
//        //in controller, we first check if player can buy property --> sends to front-end
////        if(player.getMoney() < tileprice) {
////            //THROW EXCEPTION: CANNOT BUY PROPERTY WITH CURRENT AMOUNT OF MONEY
////            //maybe options to sell other properties or mortgage or something from front end?
////        }
//        if (owner.equals(bank)) {
//            player.addProperty(this);

//
//            player.payFullAmountTo( bank, tileprice );
//            switchOwner(player);
//        }
//        else {
//            //throw exception: CAN'T BUY BECAUSE BANK DOESN'T OWN
//        }
//    }
}
