package backend.card;

import backend.assetholder.AbstractPlayer;

import java.util.List;
import java.util.Map;

public class PropertyCard extends AbstractCard {

    private double propertyMortgageValue;
    private Map<String, Double> rentPriceLookupTable;
    private Map<String, Integer> specificToNumeric;
    private List<String> upgradeOrder;
    private String titleDeed;
    private String category;
    private Double tilePrice;

    public PropertyCard(double propertyMortgageValue, Map<String, Double> priceLookupTable, List<String> upgradeOrder,
                        String titleDeed, String category, Map<String, Integer> specificToNumeric, Double tilePrice) {
        this.propertyMortgageValue = propertyMortgageValue;
        this.rentPriceLookupTable = priceLookupTable;
        this.upgradeOrder = upgradeOrder;
        this.titleDeed = titleDeed;
        this.category = category;
        this.specificToNumeric = specificToNumeric;
        this.tilePrice = tilePrice;

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

    public Integer getNumericValueOfPropertyType(String specificPropertyType) {
        return specificToNumeric.get(specificPropertyType);
    }

    public String getSpecificFromNumeric (int numeric) {
        //THROW EXCEPTION FOR WHEN NUMERIC DOESN'T EXIST
        for(String key: specificToNumeric.keySet()){
            if(specificToNumeric.get( key ) == numeric){
                return key;
            }
        }
        return "";
    }

    public double getTilePrice(){
        return this.tilePrice;
    }

    public String getCategory(){
        return this.category;
    }

}