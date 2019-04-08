package BackEnd.Card;

import java.awt.*;

public class BuildingCard extends PropertyCard {
    //make constants??
    private Color propertyColor;
    private double propertyRentWithColorSet;
    private double propertyRent1House;
    private double propertyRent2House;
    private double propertyRent3House;
    private double propertyRent4House;
    private double propertyRentHotel;
    private double propertyHousePrice;
    private double propertyHotelPrice;


    public BuildingCard(String propertyType, double propertyRent, double propertyMortgageValue,
                        Color propertyColor, double propertyRentWithColorSet, double propertyRent1House,
                        double propertyRent2House, double propertyRent3House, double propertyRent4House,
                        double propertyRentHotel, double propertyHousePrice, double propertyHotelPrice) {
        super(propertyType, propertyRent, propertyMortgageValue);
        this.propertyColor = propertyColor;
        this.propertyRentWithColorSet = propertyRentWithColorSet;
        this.propertyRent1House = propertyRent1House;
        this.propertyRent2House = propertyRent2House;
        this.propertyRent3House = propertyRent3House;
        this.propertyRent4House = propertyRent4House;
        this.propertyRentHotel = propertyRentHotel;
        this.propertyHousePrice = propertyHousePrice;
        this.propertyHotelPrice = propertyHotelPrice;
    }

    public double getPropertyHotelPrice() {
        return propertyHotelPrice;
    }

    public double getPropertyHousePrice() {
        return propertyHousePrice;
    }

    public double getPropertyRentHotel() {
        return propertyRentHotel;
    }

    public double getPropertyRent4House() {
        return propertyRent4House;
    }

    public double getPropertyRent3House() {
        return propertyRent3House;
    }

    public double getPropertyRent2House() {
        return propertyRent2House;
    }

    public double getPropertyRent1House() {
        return propertyRent1House;
    }

    public double getPropertyRentWithColorSet() {
        return propertyRentWithColorSet;
    }
}
