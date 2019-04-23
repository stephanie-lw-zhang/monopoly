package backend.card;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Map;

public class RailroadCard extends backend.card.PropertyCard {


    public RailroadCard(double propertyMortgageValue,  Map<String, Double> priceLookupTable, List<String> upgradeOrder, Map<String, Integer> specificToNumeric, String titleDeed, String category, double tilePrice) {
        super(propertyMortgageValue, priceLookupTable, upgradeOrder, titleDeed,category, specificToNumeric, tilePrice);
    }

    public RailroadCard(Node n){
        super(n);
    }

}
