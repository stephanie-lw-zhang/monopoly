package BackEnd.Card;

import java.util.List;
import java.util.Map;

public class BuildingCard extends PropertyCard {

    private Map<String,Double> priceNeededToUpgradeLookupTable;
    private Map<String,Double> sellToBankPriceLookupTable;
    private Map<String, String> specificToBase;
    private Map<String, Integer> specificToNumeric;


    public BuildingCard(Map<String, Double> buildingPriceLookupTable, double propertyMortgageValue, List<String> upgradeOrder,
                        Map<String,Double> buySingleBuildingPrice, Map<String,Double> sellToBankPriceLookupTable, Map<String,
                        String> specificToBase, double multiplierForSellingToBank, String titleDeed, String category,  Map<String, Integer> specificToNumeric){
        super(propertyMortgageValue, buildingPriceLookupTable, upgradeOrder, titleDeed, category);
        this.priceNeededToUpgradeLookupTable = buySingleBuildingPrice;
        this.sellToBankPriceLookupTable = sellToBankPriceLookupTable;
        this.specificToBase = specificToBase;
        this.specificToNumeric = specificToNumeric;

    }

    public double getOneBuildingSellToBankPrice(String currentInUpgradeOrder) {
        String base = getBasePropertyType(currentInUpgradeOrder);
        //in original multiplier would be 0.5
        return (sellToBankPriceLookupTable.get(base));
    }

    public double getPriceNeededToUpgradeLookupTable(String key) {
        return priceNeededToUpgradeLookupTable.get(key);
    }

    public String getBasePropertyType(String specificPropertyType) {
        return specificToBase.get(specificPropertyType);
    }

    public Integer getNumericValueOfPropertyType(String specificPropertyType) {
        return specificToNumeric.get(specificPropertyType);
    }

}
