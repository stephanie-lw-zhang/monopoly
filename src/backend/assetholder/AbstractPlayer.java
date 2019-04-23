package backend.assetholder;

import backend.card.AbstractCard;
import backend.tile.AbstractPropertyTile;

import java.util.List;
import java.util.Objects;

/**
 *
 *
 * @author Sam [constructor change]
 */
public abstract class AbstractPlayer extends AbstractAssetHolder{
    private String icon;
    private int turnsInJail = -1;//-1 not in jail, 0 just got to jail, 1 = 1 turn in jail
    private Boolean bankrupt = false;
    private List<AbstractCard> cards;
    // private int roll;

    private String myPlayerName;

    public AbstractPlayer(String name, Double money) {
        super( money );
        myPlayerName = name;
    }


    public String getIcon() {
        return icon;
    }

    public int getTurnsInJail() {
        return turnsInJail;
    }

    public void addTurnInJail() {
        turnsInJail += 1;
        //must call this when going to jail to set value to 0
    }

    public boolean inJail(){
        return turnsInJail != -1;
    }

    public void getOutOfJail(){
        turnsInJail = -1;
    }

    public Boolean isBankrupt(){
        return bankrupt;
    }

    public void addCard(AbstractCard card){
        cards.add( card );
    }

    public List<AbstractCard> getCards(){
        return cards;
    }
    //is this exposing guts??

//    public void declareBankruptcyTo(AbstractAssetHolder receiver){
//        this.bankrupt = true;
//        receiver.addAllProperties( this.getProperties() );
//        this.getProperties().clear();
//        this.setMoney( 0 );
//    }

    public void declareBankruptcy(Bank bank){
        this.bankrupt = true;
        bank.addAllProperties( this.getProperties() );
        this.getProperties().clear();
        this.setMoney( 0 );
    }

//    public int getRoll(){
//        return roll;
//    }
//
//    public void setRoll(int roll){
//        this.roll = roll;
//    }

    //assumption, can only pay if you have full amount
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

    public boolean checkIfOwnsAllOf(List<AbstractPropertyTile> properties) {
        return ownsSublistOfPropertiesIn( properties ).size() == properties.size();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractPlayer)) return false;
        if (!super.equals(o)) return false;
        AbstractPlayer that = (AbstractPlayer) o;
//         TODO: UNCOMMENT WHEN ICONS ARE DONE
//        return getIcon().equals(that.getIcon()) &&
//                getMyPlayerName().equals(that.getMyPlayerName());
        return getMyPlayerName().equals(that.getMyPlayerName());
    }

    @Override
    public int hashCode() {
        // TODO: UNCOMMENT WHEN ICONS ARE DONE
//        return Objects.hash(getIcon(), getMyPlayerName());
        return Objects.hash(getMyPlayerName());
    }

    public String getMyPlayerName() { return myPlayerName; }

}
