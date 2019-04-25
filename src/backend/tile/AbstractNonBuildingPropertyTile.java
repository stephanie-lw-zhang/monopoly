package backend.tile;

import backend.assetholder.AbstractAssetHolder;
import backend.assetholder.Bank;
import exceptions.IllegalInputTypeException;
import exceptions.OutOfBuildingStructureException;
import exceptions.IllegalActionOnImprovedPropertyException;
import org.w3c.dom.Element;

import java.util.List;

public abstract class AbstractNonBuildingPropertyTile extends AbstractPropertyTile {

    public AbstractNonBuildingPropertyTile(Bank bank, Element n){
        super(bank, n);
    }

    public abstract double calculateRentPrice(int roll);

    //try to avoid throwing exceptions here?
    @Override
    public void sellTo(AbstractAssetHolder buyer, double price, List<AbstractPropertyTile> sameSetProperties) throws IllegalActionOnImprovedPropertyException, OutOfBuildingStructureException, IllegalInputTypeException {
        AbstractAssetHolder seller = this.getOwner();
        super.sellTo(buyer, price, sameSetProperties);
        updateUpgradeOrder( buyer, sameSetProperties );
        updateUpgradeOrder( seller, sameSetProperties );
    }

    private void updateUpgradeOrder(AbstractAssetHolder owner, List<AbstractPropertyTile> sameSetProperties) {
        //assume parameter is inputted correctly
        int numProperties = owner.ownsSublistOfPropertiesIn(sameSetProperties ).size();
        String newInUpgradeOrder = getCard().getSpecificFromNumeric( numProperties );
        for(AbstractPropertyTile each: owner.ownsSublistOfPropertiesIn(sameSetProperties )){
            each.setCurrentInUpgradeOrder( newInUpgradeOrder );
        }
    }

}
