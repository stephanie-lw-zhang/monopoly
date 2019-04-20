package BackEnd.AssetHolder;

import BackEnd.Card.BuildingCard;
import BackEnd.Tile.AbstractPropertyTile;
import BackEnd.Tile.BuildingTile;

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
        recalculateTotalPropertiesLeftAfterWholeSale(property);
    }


    public void recalculateTotalPropertiesLeftAfterWholeSale(AbstractPropertyTile property) {
        if(property instanceof BuildingTile){
            //assume can't sell railroad or anything non building back to bank
            //assume bank can't sell properties with buildings on them
            //assume the current in upgrade order is what you want upgrade to
            BuildingCard card = (BuildingCard) property.getCard();
            String baseKey = card.getBasePropertyType(((BuildingTile) property).getCurrentInUpgradeOrder());
            Integer baseValue = card.getNumericValueOfPropertyType(((BuildingTile) property).getCurrentInUpgradeOrder());
            totalPropertiesLeft.put(baseKey, totalPropertiesLeft.get(baseKey) + baseValue);
        }
    }

    public void recalculateTotalPropertiesLeftOneBuildingUpdate(AbstractPropertyTile property) {
        if (property instanceof  BuildingTile) {
            BuildingCard card = (BuildingCard) property.getCard();
            String baseKey = card.getBasePropertyType(((BuildingTile) property).getCurrentInUpgradeOrder());
            Integer baseValue = card.getNumericValueOfPropertyType(((BuildingTile) property).getCurrentInUpgradeOrder());
            int currentIndex = card.getUpgradeOrderIndexOf(((BuildingTile) property).getCurrentInUpgradeOrder());
            if(currentIndex > 0){
                String previous = card.getUpgradeOrderAtIndex(currentIndex - 1);
                String previousBaseKey = card.getBasePropertyType(previous);
                Integer previousBaseValue = card.getNumericValueOfPropertyType(previous);
                totalPropertiesLeft.put(previousBaseKey, totalPropertiesLeft.get(previousBaseKey) + previousBaseValue);
            }
            totalPropertiesLeft.put(baseKey, totalPropertiesLeft.get(baseKey) - baseValue);
        }
    }

    public Boolean buildingsRemain(String building){
        return totalPropertiesLeft.get( building ) != 0;
        //assume building isn't faulty string (it exists in map)
    }

    //money is supposed to be unlimited in standard version
    @Override
    public void paysTo(AbstractAssetHolder receiver, Double debt) {
        receiver.setMoney( receiver.getMoney() + debt );
    }

}