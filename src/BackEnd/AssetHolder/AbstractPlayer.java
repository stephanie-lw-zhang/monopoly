package BackEnd.AssetHolder;

import BackEnd.Card.AbstractCard;
import BackEnd.Tile.PropertyTiles.AbstractPropertyTile;

import java.util.List;

public abstract class AbstractPlayer extends AbstractAssetHolder{
    private String icon;
    private boolean isInJail;
    private Boolean bankrupt;
    private List<AbstractCard> cards;

    public AbstractPlayer(Double money) {
        super( money );
    }


    public String getIcon() {
        return icon;
    }

    public boolean getIsInJail() {
        return isInJail;
    }

    public void setIsInJail(boolean jail) {
        isInJail = jail;
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


    public void paysTo (AbstractAssetHolder receiver, Double debt){
        if(this.getMoney() > debt){
            receiver.setMoney( receiver.getMoney() + this.getMoney() );
            this.setMoney( 0.0 );
            debt = debt - this.getMoney();
            Double totalAssetValue = 0.0;
            for(AbstractPropertyTile each: this.getProperties()){
                totalAssetValue += each.sellToBankPrice();
                //assume can only sell to bank, no trading
            }
            if (totalAssetValue < debt){
                this.declareBankruptcyTo(receiver);
            } else{
                int i = 0;
                while(debt > 0){
                    AbstractPropertyTile currentProperty = this.getProperties().get( i );
                    //assume selling in order of buying property, but can change this to own choice
                    debt -= currentProperty.sellToBankPrice();

                    receiver.addProperty( currentProperty );
                    i++;
                }
            }
        } else{
            this.setMoney( this.getMoney()-debt );
            receiver.setMoney( receiver.getMoney() + debt );
        }
    }


}
