package BackEnd.AssetHolder;

import BackEnd.Tile.PropertyTiles.AbstractPropertyTile;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAssetHolder{
    private List<AbstractPropertyTile> properties  = new ArrayList<AbstractPropertyTile>(  );
    private Double money;

    public AbstractAssetHolder(Double money) {
        this.money = money;
    }

    public double getMoney() {
        return this.money;
    }

    public void setMoney(double newValue) {
        this.money = newValue;
    }

    public abstract void addProperty(AbstractPropertyTile property);

    public void addAllProperties(List<AbstractPropertyTile> propertyList){
        for(AbstractPropertyTile property: propertyList){
            addProperty( property );
        }
    }

    public List<AbstractPropertyTile> getProperties() {
        return properties;
    }

    public abstract void paysTo (AbstractAssetHolder receiver, Double debt);

}
