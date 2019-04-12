package BackEnd.Card;

import java.util.List;
import java.util.Map;

public class RailroadCard extends PropertyCard {

    public RailroadCard(double propertyMortgageValue,  Map<String, Double> priceLookupTable, List<String> upgradeOrder, Map<String,Double> sellToBankLookupTable, Map<String, String> specificToBase, Map<String, Integer> specificToNumeric) {
        super(propertyMortgageValue, priceLookupTable, upgradeOrder, specificToBase, specificToNumeric);
    }

}
