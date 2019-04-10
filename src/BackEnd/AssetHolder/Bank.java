package BackEnd.AssetHolder;

import BackEnd.Card.BuildingCard;
import BackEnd.Tile.PropertyTiles.AbstractPropertyTile;
import BackEnd.Tile.PropertyTiles.BuildingTile;

import java.util.Map;

public class Bank extends AbstractAssetHolder {
    Map<String,Integer> totalPropertiesLeft;

    public Bank(Double money, Map<String,Integer> totalPropertiesLeft) {
        super(money);
        this.totalPropertiesLeft = totalPropertiesLeft;
    }

    @Override
    public void addProperty(AbstractPropertyTile property) {
        this.getProperties().add( property );
        recalculateTotalPropertiesLeft(property);
    }

    //
    public void recalculateTotalPropertiesLeft(AbstractPropertyTile property) {
        if(property instanceof BuildingTile){
            //SWITCH TO BUILDINGTILE
            //assume can't sell railroad or anything non building back to bank
            //assume bank can't sell properties with buildings on them
            BuildingCard card = (BuildingCard) property.getCard();
            String baseKey = card.getBasePropertyType(((BuildingTile) property).getCurrentInUpgradeOrder());
            Integer baseValue = card.getNumericValueofPropertyType(((BuildingTile) property).getCurrentInUpgradeOrder());
                totalPropertiesLeft.put(baseKey, totalPropertiesLeft.get(baseKey)+baseValue);
        }
    }

    public void subtractOneHouse(){
        numHousesLeft -= 1;
    }

    public void subtractOneHotel(){
        numHotelsLeft -= 1;
    }

    public void addHouses(int x) { numHousesLeft += x; }

    public void addHotels(int x) { numHotelsLeft += x; }

    public int getNumHousesLeft(){
        return numHousesLeft;
    }

    public int getNumHotelsLeft(){
        return numHotelsLeft;
    }

//    public void setNumHousesLeft(int number){
//        numHousesLeft = number;
//    }
//
//    public void setNumHotelsLeft(int number){
//        numHotelsLeft = number;
//    }



    //money is supposed to be unlimited in standard version
    @Override
    public void paysTo(AbstractAssetHolder receiver, Double debt) {
        receiver.setMoney( receiver.getMoney() + debt );
    }

}
