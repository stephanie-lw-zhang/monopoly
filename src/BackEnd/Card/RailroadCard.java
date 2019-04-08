package BackEnd.Card;

public class RailroadCard extends PropertyCard {
    private double railroadRent1Owned;
    private double railroadRent2Owned;
    private double railroadRent3Owned;
    private double railroadrent4Owned;

    public RailroadCard(String propertyType, double propertyRent, double propertyMortgageValue,
                        double railroadRent1Owned, double railroadRent2Owned, double railroadRent3Owned, double railroadRent4Owned) {
        super(propertyType, propertyRent, propertyMortgageValue);
        this.railroadRent1Owned = railroadRent1Owned;
        this.railroadRent2Owned = railroadRent2Owned;
        this.railroadRent3Owned = railroadRent3Owned;
        this.railroadrent4Owned = railroadRent4Owned;
    }

    public double getRailroadRent1Owned() {
        return railroadRent1Owned;
    }

    public double getRailroadRent2Owned() {
        return railroadRent2Owned;
    }

    public double getRailroadRent3Owned() {
        return railroadRent3Owned;
    }

    public double getRailroadrent4Owned() {
        return railroadrent4Owned;
    }
}
