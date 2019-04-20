package backend.card;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildingCard extends PropertyCard {

    private Map<String,Double> priceNeededToUpgradeLookupTable;
    private Map<String,Double> sellToBankPriceLookupTable;
    private Map<String, String> specificToBase;


    public BuildingCard(Map<String, Double> buildingPriceLookupTable, double propertyMortgageValue, List<String> upgradeOrder,
                        Map<String,Double> buySingleBuildingPrice, Map<String,Double> sellToBankPriceLookupTable, Map<String,
            String> specificToBase, String titleDeed, String category, Map<String, Integer> specificToNumeric, double tilePrice){
        super(propertyMortgageValue, buildingPriceLookupTable, upgradeOrder, titleDeed, category, specificToNumeric,tilePrice);
        this.priceNeededToUpgradeLookupTable = buySingleBuildingPrice;
        this.sellToBankPriceLookupTable = sellToBankPriceLookupTable;
        this.specificToBase = specificToBase;
    }

    public BuildingCard(Node node){
        super(node);
        Element element = (Element) node;

        Map<String,Double> buySingleBuildingPrice = new HashMap<>();
        Map<String,Double> sellToBankPriceLookupTable = new HashMap<>();
        Map<String, String> specificToBase = new HashMap<>();

        NodeList upgrades = element.getElementsByTagName("Upgrades");
        for(int i = 0; i<upgrades.getLength();i++){
            //get tag values from properties file//do we need to split the array
            String property = getTagValue("Upgrade", (Element) upgrades.item(i));
            String[] entry = property.split(",");
            String upgrade = entry[0];
            String base = entry[2];
            Double upgradePrice = Double.parseDouble(entry[4]);
            Double bankPrice = Double.parseDouble(entry[5]);
            buySingleBuildingPrice.put(upgrade, upgradePrice);
            specificToBase.put(upgrade,base);
            sellToBankPriceLookupTable.put(upgrade, bankPrice);
        }
        this.priceNeededToUpgradeLookupTable = buySingleBuildingPrice;
        this.sellToBankPriceLookupTable = sellToBankPriceLookupTable;
        this.specificToBase = specificToBase;
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



}

