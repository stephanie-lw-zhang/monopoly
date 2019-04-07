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

    public void addProperty(AbstractPropertyTile property){
        this.properties.add( property );
    }

    public void addAllProperties(List<AbstractPropertyTile> propertyList){
        this.properties.addAll( propertyList );
    }

    public List<AbstractPropertyTile> getProperties() {
        return properties;
    }

    public abstract void paysTo (AbstractAssetHolder receiver, Double debt);

}
