package backend.assetholder;

import backend.tile.AbstractPropertyTile;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAssetHolder{
    private List<AbstractPropertyTile> properties  = new ArrayList<>();
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

    @Override
    public boolean equals (Object o) {
        if (o == this) { return true; }
        if (!(o instanceof AbstractAssetHolder)) {
            return false;
        }
        AbstractAssetHolder player2 = (AbstractAssetHolder) o;
        return ((player2.getProperties().equals(this.getProperties())) && (player2.getMoney() == this.getMoney()));
    }
}
