package BackEnd.Card;

import BackEnd.AssetHolder.AbstractPlayer;

import java.util.List;
import java.util.Map;

public class PropertyCard extends AbstractCard {

    private double propertyMortgageValue;
    private Map<String, Double> rentPriceLookupTable;
    private List<String> upgradeOrder;
    private Map<String, String> specificToBase;
    private Map<String, Integer> specificToNumeric;

    public PropertyCard(double propertyMortgageValue, Map<String, Double> priceLookupTable, List<String> upgradeOrder, Map<String, String> specificToBase, Map<String, Integer> specificToNumeric) {
        this.propertyMortgageValue = propertyMortgageValue;
        this.rentPriceLookupTable = priceLookupTable;
        this.upgradeOrder = upgradeOrder;
        this.specificToBase = specificToBase;
        this.specificToNumeric = specificToNumeric;
    }

    public double getMortgageValue(){
        return propertyMortgageValue;
    }

    public double lookupPrice (String key){
        return rentPriceLookupTable.get(key);
    }

    public String nextInUpgradeOrder(String current) {
        try {
            return upgradeOrder.get(getUpgradeOrderIndexOf(current)+1);
        }
        catch (IndexOutOfBoundsException i) {
            //throw exception
            return "";
        }
    }

    public String previousInUpgradeOrder(String current) {
        try {
            return upgradeOrder.get(getUpgradeOrderIndexOf(current)-1);
        }
        catch (IndexOutOfBoundsException i) {
            //throw exception
            return "";
        }
    }

    public String getBasePropertyType(String specificPropertyType) {
        return specificToBase.get(specificPropertyType);
    }

    public Integer getNumericValueofPropertyType(String specificPropertyType) {
        return specificToNumeric.get(specificPropertyType);
    }

    public String getUpgradeOrderAtIndex(int index){
        return upgradeOrder.get(index);
    }

    public int getUpgradeOrderIndexOf(String current) {
        return upgradeOrder.indexOf(current);
    }

    @Override
    public void applyTo(AbstractPlayer player) {
        return;
    }
}
