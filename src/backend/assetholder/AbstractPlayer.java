package backend.assetholder;

import backend.card.AbstractCard;
import backend.tile.AbstractPropertyTile;

import frontend.views.IconView;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Objects;

/**
 * Abstract class representing a Player in the game
 *
 * @author Sam [constructor change]
 */
abstract public class AbstractPlayer extends AbstractAssetHolder {

    private List<AbstractCard> cards;
    private String             myPlayerName;
    private IconView           myIcon;
    private Boolean            isBankrupt;
    private int                turnsInJail; //-1 not in jail, 0 just got to jail, 1 = 1 turn in jail

    /**
     * AbstractPlayer main constructor
     * @param name
     * @param icon
     * @param money
     */
    public AbstractPlayer(String name, ImageView icon, Double money) {
        super( money );
        myPlayerName = name;
        myIcon = new IconView(icon);
        isBankrupt = false;
        turnsInJail = -1;
    }

    public void declareBankruptcy(Bank bank){
        this.isBankrupt = true;
        bank.addAllProperties( this.getProperties() );
        this.getProperties().clear();
        this.setMoney( 0 );
    }

    @Override
    public void payFullAmountTo (AbstractAssetHolder receiver, Double debt){
//        if(this.getMoney() > debt){
//            receiver.setMoney( receiver.getMoney() + this.getMoney() );
//            this.setMoney( 0.0 );
//            debt = debt - this.getMoney();
//            if (getTotalAssetValue() < debt){
//                this.declareBankruptcyTo(receiver);
//            } else{
//                payBackDebt( receiver, debt );
//            }
//        } else{
            this.setMoney( this.getMoney()-debt );
            receiver.setMoney( receiver.getMoney() + debt );
//        }
    }

    public void addProperty(AbstractPropertyTile property){
        this.getProperties().add( property );
    }

//    public void sellPropertiesToBankUntilGoalReached(AbstractAssetHolder receiver, Double goal) {
//        //right now you pay back debt by selling properties until you can pay back, but made separate method cuz this can be changed
//        int i = 0;
//        while(goal > 0){
//            AbstractPropertyTile currentProperty = this.getProperties().get( i );
//            //assume selling in order of buying property, but can change this to own choice
//            goal -= currentProperty.sellBuildingToBankPrice();
//            bank.addProperty( currentProperty );
//            i++;
//        }
//    }

//    private Double getTotalAssetValue() {
//        Double totalAssetValue = 0.0;
//        for(AbstractPropertyTile each: this.getProperties()){
//            totalAssetValue += each.sellBuildingToBankPrice();
//            //assume can only sell to bank, no trading
//        }
//        return totalAssetValue;
//    }

    /**
     * Checks if the player owns all of the properties given
     * @param properties
     * @return boolean      whether player owns all Tiles
     */
    public boolean checkIfOwnsAllOf(List<AbstractPropertyTile> properties) {
        return ownsSublistOfPropertiesIn( properties ).size() == properties.size();
    }

    /**
     * Custom equals method for an AbstractPlayer
     * @param o
     * @return boolean      whether player equals another
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractPlayer)) return false;
        if (!super.equals(o)) return false;
        AbstractPlayer that = (AbstractPlayer) o;

        return getMyIcon().equals(that.getMyIcon()) &&
                getMyPlayerName().equals(that.getMyPlayerName());
    }

    /**
     * Custom hashCoding for an AbstractPlayer based on
     * its unique icon and name
     * @return int      hashcode of AbstractPlayer
     */
    @Override
    public int hashCode() {
        return Objects.hash(getMyIcon(), getMyPlayerName());
    }

    /**
     * Getter for the AbstractPlayer object's name
     * @return String       player name
     */
    public String getMyPlayerName() { return myPlayerName; }

    /**
     * Getter for the AbstractPlayer icon
     * @return IconView     the icon of the Player
     */
    public IconView getMyIcon() { return myIcon; }

    /**
     * Getter for the number of turns in jail
     * @return int      turns in jail
     */
    public int getTurnsInJail() { return turnsInJail; }

    /**
     * Called when going to jail to set value to 0
     */
    public void addTurnInJail() { turnsInJail += 1; }

    /**
     * Returns whether or not player is in jail
     * @return boolean      whether player in jail
     */
    public boolean isInJail(){
        return turnsInJail != -1;
    }

    /**
     * Changes turnsInJail invariant for player not in jail
     */
    public void getOutOfJail(){
        turnsInJail = -1;
    }

    /**
     * Returns whether player is bankrupt or not
     * @return boolean      whether player bankrupt
     */
    public Boolean isBankrupt(){
        return isBankrupt;
    }

    /**
     * Adds card to player's list of cards
     * @param card
     */
    public void addCard(AbstractCard card){
        cards.add( card );
    }

    /**
     * Gets player's list of cards
     * @return
     */
    public List<AbstractCard> getCards(){
        return cards;
    }

}
