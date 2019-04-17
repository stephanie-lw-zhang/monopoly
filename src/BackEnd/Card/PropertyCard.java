package BackEnd.Card;

import BackEnd.AssetHolder.AbstractPlayer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyCard extends AbstractCard {

    private double propertyMortgageValue;
    private Map<String, Double> rentPriceLookupTable;
    private Map<String, Integer> specificToNumeric;
    private List<String> upgradeOrder;
    private String titleDeed;
    private String category;

    public PropertyCard(double propertyMortgageValue, Map<String, Double> priceLookupTable, List<String> upgradeOrder,
                        String titleDeed, String category, Map<String, Integer> specificToNumeric) {
        this.propertyMortgageValue = propertyMortgageValue;
        this.rentPriceLookupTable = priceLookupTable;
        this.upgradeOrder = upgradeOrder;
        this.titleDeed = titleDeed;
        this.category = category;
        this.specificToNumeric = specificToNumeric;

    }

    public PropertyCard(Node node){
        Element element = (Element) node;
        double propertyMortgageValue = Double.parseDouble(getTagValue("TileMortgageValue", element));
        String titleDeed = getTagValue("TitleDeed", element);
        String category = getTagValue("TileColor", element);
        Map<String, Double> buildingPriceLookupTable = new HashMap<>();
        List<String> upgradeOrder = new ArrayList<>();
        Map<String, Integer> specificToNumeric = new HashMap<>();

        NodeList upgrades = element.getElementsByTagName("Upgrades");
        for(int i = 0; i<upgrades.getLength();i++){
            //get tag values from properties file//do we need to split the array
            String property = getTagValue("Upgrade", (Element) upgrades.item(i));
            String[] entry = property.split(",");
            String upgrade = entry[0];
            int numeric = Integer.parseInt(entry[1]);
            Double rentPrice = Double.parseDouble(entry[3]);
            upgradeOrder.add(upgrade);
            buildingPriceLookupTable.put(upgrade, rentPrice);
            specificToNumeric.put(upgrade, numeric);
        }
        this.propertyMortgageValue = propertyMortgageValue;
        this.rentPriceLookupTable = buildingPriceLookupTable;
        this.upgradeOrder = upgradeOrder;
        this.titleDeed = titleDeed;
        this.category = category;
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

}