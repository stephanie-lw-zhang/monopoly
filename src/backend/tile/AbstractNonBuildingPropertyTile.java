package backend.tile;

import backend.assetholder.AbstractAssetHolder;
import backend.assetholder.Bank;
import backend.card.PropertyCard;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNonBuildingPropertyTile extends AbstractPropertyTile {


    public AbstractNonBuildingPropertyTile(Bank bank, PropertyCard card, String tiletype, double tileprice, int index) {
        super(bank, card, tiletype, tileprice, index);
    }

    public AbstractNonBuildingPropertyTile(Bank bank, Element n){
        super(bank, n);
    }

//    public AbstractNonBuildingPropertyTile(Element n){
//        super(n);
//    }


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

}
