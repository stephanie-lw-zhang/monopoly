package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.AbstractAssetHolder;
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.PropertyCard;
import BackEnd.Card.RailroadCard;
import Controller.Game;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class RailroadTile extends AbstractPropertyTile {

    private RailroadCard card;

    public RailroadTile(Bank bank, PropertyCard card, String tiletype, double tileprice) {
        super(bank, card, tiletype, tileprice);
        this.card = (RailroadCard)card;
    }

    public RailroadTile(Element n){
        super(n);
    }

    public double calculateRentPrice(Game game) {
        if (isMortgaged()) {
            return 0;
        }
        else {

            return card.lookupPrice(getCurrentInUpgradeOrder());

        }
    }

    @Override
    public void sellTo(AbstractAssetHolder buyer, double price, List<AbstractPropertyTile> sameSetProperties) {
        AbstractAssetHolder seller = this.getOwner();
        super.sellTo(buyer, price, sameSetProperties);
        updateUpgradeOrder( buyer, sameSetProperties );
        updateUpgradeOrder( seller, sameSetProperties );

    }

    private void updateUpgradeOrder(AbstractAssetHolder owner, List<AbstractPropertyTile> sameSetProperties) {
        int numRailroads = otherRailroadsOwnedBy(owner, sameSetProperties ).size();
        String newInUpgradeOrder = card.getSpecificFromNumeric( numRailroads );
        for(RailroadTile each: otherRailroadsOwnedBy(owner, sameSetProperties )){
            each.setCurrentInUpgradeOrder( newInUpgradeOrder );
        }
    }

    private List<RailroadTile> otherRailroadsOwnedBy (AbstractAssetHolder owner, List<AbstractPropertyTile> properties) {
        List<RailroadTile> railroadsPlayerOwns = new ArrayList<>();
        for (AbstractPropertyTile tile : properties) {
            if (tile instanceof RailroadTile && (tile.getOwner().equals(owner))) {
                //throw exception: YOU CANNOT UPGRADE WITHOUT A MONOPOLY ON COLOR
                railroadsPlayerOwns.add( (RailroadTile) tile );
            }
        }
        return railroadsPlayerOwns;
    }

}
