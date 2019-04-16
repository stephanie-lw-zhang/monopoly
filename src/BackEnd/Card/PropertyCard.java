package BackEnd.Card;

import BackEnd.AssetHolder.AbstractPlayer;

import java.util.List;
import java.util.Map;

public class PropertyCard extends AbstractCard {

    private double propertyMortgageValue;
    private Map<String, Double> rentPriceLookupTable;
    private List<String> upgradeOrder;
    private String titleDeed;
    private String category;

    public PropertyCard(double propertyMortgageValue, Map<String, Double> priceLookupTable, List<String> upgradeOrder,
                        String titleDeed, String category) {
        this.propertyMortgageValue = propertyMortgageValue;
        this.rentPriceLookupTable = priceLookupTable;
        this.upgradeOrder = upgradeOrder;
        this.titleDeed = titleDeed;
        this.category = category;
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