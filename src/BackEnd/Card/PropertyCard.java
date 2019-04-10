package BackEnd.Card;

import BackEnd.AssetHolder.AbstractPlayer;

public class PropertyCard extends AbstractCard {

    private String propertyType;
    private double propertyRent;
    private double propertyMortgageValue;

    public PropertyCard(String propertyType, double propertyRent, double propertyMortgageValue) {
        this.propertyType = propertyType;
        this.propertyRent = propertyRent;
        this.propertyMortgageValue = propertyMortgageValue;
    }

    public double getMortgageValue(){
        return propertyMortgageValue;
    }

    @Override
    public void applyTo(AbstractPlayer player) {
        return;
    }
}
