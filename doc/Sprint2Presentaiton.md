# revisit the project's design goals: is it as flexible/open as you expected it to be and how have you fixed/closed the core implementation in a data driven way?
- We have done a large amount of refactoring on the back-end to make it as data-driven as possible. We now have rentPrice look up maps, as well as an upgradeOrder list, and several other maps that will help determine how many of each type of property structure there is. This way, we do not have to hard-code any of the specific cases that would result in different rent price values, and the bank can function with a limited number of houses and hotels. Thus, if a different game doesn’t have the same type of properties or its bank has a different starting number of building structures, then our game is flexible enough to accommodate these changes.
- We are using Properties and XML files for front-end configuration, as well as for configuration of values such as rent price, mortgage value, etc. This way, all of our tile classes and players interact in a fixed way, but the values of prices when a player lands on a property tile can easily change, and the types of tiles on the board can easily change as well. Additionally, we are thinking of renaming Actions to be OriginalMonopolyActions and turn to be OriginalMonopolyTurn so that if a different type of Monopoly game implements different rules, we can easily create new Actions and Turn classes to accommodate these rules and actions. 
# describe two APIs in detail (one from the first presentation and a new one):
- AbstractNonBuildingPropertyTile
    * Service: Abstract class for property tiles that are not buildings (like railroads, utilities, etc.). Specifically it calculates the rent price if a player lands on it and correctly sells the property to another player or the bank(the important thing here was to make sure the number of properties held was updated after selling it, because that impacts rent price).
    * Extension: calculating the rent price is an abstract method, so it can be specific to each tile.
    * How it has changed: We changed the specifics of the sell to method to be more accurate. Instead of just paying and exchanging the property, we had to make sure the rent price is updated since it depends on how many of each property you own (which changes after selling).
    * How does it support users: The changes we made didn’t change input or output, only specific implementation, so it shouldn’t break anyone else’s code. Also, we made our methods short and readable. Our method names are also straightforward and easy to understand.

```java
public AbstractNonBuildingPropertyTile(Bank bank, PropertyCard card, String tiletype, double tileprice) {
        super(bank, card, tiletype, tileprice);
    }

    public AbstractNonBuildingPropertyTile(Bank bank, PropertyCard card, Element n){
        super(bank, card, n);
    }


    public abstract double calculateRentPrice(int roll);

    @Override
    public void sellTo(AbstractAssetHolder buyer, double price, List<AbstractPropertyTile> sameSetProperties) {
        AbstractAssetHolder seller = this.getOwner();
        super.sellTo(buyer, price, sameSetProperties);
        updateUpgradeOrder( buyer, sameSetProperties );
        updateUpgradeOrder( seller, sameSetProperties );

    }

    private void updateUpgradeOrder(AbstractAssetHolder owner, List<AbstractPropertyTile> sameSetProperties) {
        int numProperties = sublistOfPropertiesOwnedBy(owner, sameSetProperties ).size();
        String newInUpgradeOrder = getCard().getSpecificFromNumeric( numProperties );
        for(AbstractNonBuildingPropertyTile each: sublistOfPropertiesOwnedBy(owner, sameSetProperties )){
            each.setCurrentInUpgradeOrder( newInUpgradeOrder );
        }
    }

    private List<AbstractNonBuildingPropertyTile> sublistOfPropertiesOwnedBy(AbstractAssetHolder owner, List<AbstractPropertyTile> properties) {
        List<AbstractNonBuildingPropertyTile> propertiesOwnedBy = new ArrayList<>();
        for (AbstractPropertyTile tile : properties) {
            if (tile instanceof RailroadTile && (tile.getOwner().equals(owner))) {
                //throw exception: YOU CANNOT UPGRADE WITHOUT A MONOPOLY ON COLOR
                propertiesOwnedBy.add( (RailroadTile) tile );
            }
        }
        return propertiesOwnedBy;
    }
```


- AbstractAssetHolder
    * Service: Contains common methods for asset holders, namely the bank and players (in original monopoly). 
    * Extension: Includes abstract methods paysTo and addProperty, because bank and players differ on how these need to be implemented. We also implemented our own “equal” comparator method. 
    * How it has changed: No change
    * How does it support users: It’s a short class with easy to understand methods.

```java
public abstract class AbstractAssetHolder{
    private List<AbstractPropertyTile> properties  = new ArrayList<>(  );
    private Double money;

    public AbstractAssetHolder(Double money) {
        this.money = money;
    }

    public double getMoney() {
        return this.money;
    }

    public void setMoney(double newValue) {
        this.money = newValue;
    }

    public abstract void addProperty(AbstractPropertyTile property);

    public void addAllProperties(List<AbstractPropertyTile> propertyList){
        for(AbstractPropertyTile property: propertyList){
            addProperty( property );
        }
    }

    public List<AbstractPropertyTile> getProperties() {
        return properties;
    }

    public abstract void paysTo (AbstractAssetHolder receiver, Double debt);

    @Override
    public boolean equals (Object o) {
        if (o == this) { return true; }
        if (!(o instanceof AbstractAssetHolder)) {
            return false;
        }
        AbstractAssetHolder player2 = (AbstractAssetHolder) o;
        return ((player2.getProperties().equals(this.getProperties())) && (player2.getMoney() == this.getMoney()));
    }
}
```


# show two use cases implemented in Java code in detail that show off how to use the APIs you described
- Use case with AbstractNonBuildingPropertyTile: Landing on a utility tile and needing to pay rent
    * Player lands on a utility tile, if the tile is owned, the player pays the owner of the tile the rent price (calculated from calculateRentPrice). 
- Use case with AbstractAssetHolder: Player sells houses back to the bank
    * The player removes the property from their properties list, the bank adds that property to its properties list, and recalculates how many houses can be built. 
    ```java
    @Override
    public void addProperty(AbstractPropertyTile property) {
        this.getProperties().add( property );
        recalculateTotalPropertiesLeftAfterWholeSale(property);
    }
    
     public void recalculateTotalPropertiesLeftOneBuildingUpdate(AbstractPropertyTile property) {
        if (property instanceof  BuildingTile) {
            BuildingCard card = (BuildingCard) property.getCard();
            String baseKey = card.getBasePropertyType(((BuildingTile) property).getCurrentInUpgradeOrder());
            Integer baseValue = card.getNumericValueOfPropertyType(((BuildingTile) property).getCurrentInUpgradeOrder());
            int currentIndex = card.getUpgradeOrderIndexOf(((BuildingTile) property).getCurrentInUpgradeOrder());
            if(currentIndex > 0){
                String previous = card.getUpgradeOrderAtIndex(currentIndex - 1);
                String previousBaseKey = card.getBasePropertyType(previous);
                Integer previousBaseValue = card.getNumericValueOfPropertyType(previous);
                totalPropertiesLeft.put(previousBaseKey, totalPropertiesLeft.get(previousBaseKey) + previousBaseValue);
            }
            totalPropertiesLeft.put(baseKey, totalPropertiesLeft.get(baseKey) - baseValue);
        }
    }
    ```
    
# describe one element of the design that has changed based on your deeper understanding of the project, how were those changes discussed, and what trade-offs ultimately led to the changes 
- We have changed our applyLandedOnAction in the Tile classes. Before, we directly were executing the actions in the tile, but now we are returning a list of Actions (which are Enums) to the Controller, so that it can tell the front end to allow the user to push certain buttons whenever a certain tile is landed on. For example, if a property tile is landed on that is owned by the bank, the applyLandedOnAction in the AbstractPropertyTile class will return a list of enums with BUY and AUCTION as the enums in the list. This is called in the controller, which will send it to the front-end. In the controller, we also have a onAction() method that goes through each case action and calls backend methods to implement these actions. 
- These changes were discussed in the past two days, when we realized that it may not be good design to have the applyLandedOnAction method return nothing. To compensate for this, we were planning on having a Tile Controller class. However, we decided to have all applyLandedOnAction methods return a list of actions so that all tiles will be able to specify some type of action if necessary, instead of some tiles being able to specify and others not being able to. 
- If we did not return lists of Enums for the applyLandedOnAction methods, one trade-off would be that the readability of our tile classes and controller classes would decrease, since something methods would call a lot of methods while others would just return nothing. Another trade-off was that the controller would have to create classes for tiles as well and  implement specific logic for these different tiles, which we thought would not be good design. A trade-off of returning lists of Enums was that we had to implement an Enums class, and we weren’t sure if these actions were constant enough to be made into Enums. However, we decided that these actions will not vary within the original Monopoly game, and we can create multiple Enums classes of actions for different games, which would make this flexible between games. Thus, we decided to use Enums in the tiles and return them to the controller. 