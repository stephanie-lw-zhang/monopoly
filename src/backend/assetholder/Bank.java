package backend.assetholder;

import backend.tile.AbstractPropertyTile;

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
        //when would this ever happen? when not downgrading evenly
        property.recalculateTotalPropertiesLeftAfterWholeSale( totalPropertiesLeft );
    }

    public void recalculateTotalPropertiesLeftOneBuildingUpdate(AbstractPropertyTile property) {
        property.recalculateTotalPropertiesLeftOneBuildingUpdate(totalPropertiesLeft );
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