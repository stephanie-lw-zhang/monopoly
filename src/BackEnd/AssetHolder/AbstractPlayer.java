package BackEnd.AssetHolder;

import BackEnd.Card.AbstractCard;
import BackEnd.Tile.PropertyTiles.AbstractPropertyTile;

import java.util.List;

public abstract class AbstractPlayer extends AbstractAssetHolder{
    private String icon;
    private int turnsInJail = -1;//-1 not in jail, 0 just got to jail, 1 = 1 turn in jail
    private Boolean bankrupt;
    private List<AbstractCard> cards;
    private Bank bank;
    private int roll;


    public AbstractPlayer(Double money, Bank bank) {
        super( money );
        this.bank = bank;
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

    public void declareBankruptcyTo(AbstractAssetHolder receiver){
        this.bankrupt = true;
        receiver.addAllProperties( this.getProperties() );
        this.getProperties().clear();
        this.setMoney( 0 );
    }

    public int getRoll(){
        return roll;
    }

    public void setRoll(int roll){
        this.roll = roll;
    }

    public void paysTo (AbstractAssetHolder receiver, Double debt){
        if(this.getMoney() > debt){
            receiver.setMoney( receiver.getMoney() + this.getMoney() );
            this.setMoney( 0.0 );
            debt = debt - this.getMoney();
            if (getTotalAssetValue() < debt){
                this.declareBankruptcyTo(receiver);
            } else{
                payBackDebt( receiver, debt );
            }
        } else{
            this.setMoney( this.getMoney()-debt );
            receiver.setMoney( receiver.getMoney() + debt );
        }
    }

    public void addProperty(AbstractPropertyTile property){
        this.getProperties().add( property );
    }

    private void payBackDebt(AbstractAssetHolder receiver, Double debt) {
        //right now you pay back debt by selling properties until you can pay back, but made separate method cuz this can be changed
        int i = 0;
        while(debt > 0){
            AbstractPropertyTile currentProperty = this.getProperties().get( i );
            //assume selling in order of buying property, but can change this to own choice
            debt -= currentProperty.sellToBankPrice();
            bank.addProperty( currentProperty );
            i++;
        }
    }

    private Double getTotalAssetValue() {
        Double totalAssetValue = 0.0;
        for(AbstractPropertyTile each: this.getProperties()){
            totalAssetValue += each.sellToBankPrice();
            //assume can only sell to bank, no trading
        }
        return totalAssetValue;
    }

    public Bank getBank() {
        return bank;
    }

}
