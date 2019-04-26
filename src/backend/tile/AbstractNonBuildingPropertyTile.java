package backend.tile;

import backend.assetholder.AbstractAssetHolder;
import backend.assetholder.Bank;
import exceptions.OutOfBuildingStructureException;
import exceptions.IllegalActionOnImprovedPropertyException;
import exceptions.IllegalInputTypeException;
import org.w3c.dom.Element;
import exceptions.IllegalInputTypeException;

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
        try {
            super.sellTo(buyer, price, sameSetProperties);
        } catch(Exception e){
            e.printStackTrace();
        }
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
