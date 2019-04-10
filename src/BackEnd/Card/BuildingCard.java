package BackEnd.Card;

import java.util.List;
import java.util.Map;

public class BuildingCard extends PropertyCard {

    private Map<String,Double> priceNeededToUpgradeLookupTable;
    private Map<String,Double> sellToBankPriceLookupTable;

    public BuildingCard(Map<String, Double> buildingPriceLookupTable, double propertyMortgageValue, List<String> upgradeOrder, Map<String,Double> buySingleBuildingPrice, Map<String,Double> sellToBankPriceLookupTable, Map<String, String> specificToBase, Map<String, Integer> specificToNumeric){
        super(propertyMortgageValue, buildingPriceLookupTable, upgradeOrder, specificToBase, specificToNumeric);
        this.priceNeededToUpgradeLookupTable = buySingleBuildingPrice;
        this.sellToBankPriceLookupTable = sellToBankPriceLookupTable;
    }

    public double sellToBankPriceLookupTable(String key) {
        return sellToBankPriceLookupTable.get(key);
    }

    public double buySingleBuildingPriceLookupTable(String key) {
        return priceNeededToUpgradeLookupTable.get(key);
    }

}
