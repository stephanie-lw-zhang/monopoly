package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.AbstractAssetHolder;
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
    public void sellTo(AbstractAssetHolder assetHolder, double price, List<AbstractPropertyTile> sameSetProperties) {
        super.sellTo(assetHolder, price, sameSetProperties);
        if(buyerOtherRailroads( sameSetProperties ).size() > 1){
            PropertyCard card = (PropertyCard) buyerOtherRailroads( sameSetProperties ).get( 0 ).getCard();
            String next = card.nextInUpgradeOrder( getCurrentInUpgradeOrder() );
            this.setCurrentInUpgradeOrder( next );
            for(RailroadTile each: buyerOtherRailroads( sameSetProperties )){
                each.setCurrentInUpgradeOrder( next );
            }
        }
    }

    private List<RailroadTile> buyerOtherRailroads (List<AbstractPropertyTile> properties) {
        List<RailroadTile> railroadsPlayerOwns = new ArrayList<>();
        for (AbstractPropertyTile tile : properties) {
            if (tile instanceof RailroadTile && (tile.getOwner().equals(this.getOwner()))) {
                //throw exception: YOU CANNOT UPGRADE WITHOUT A MONOPOLY ON COLOR
                railroadsPlayerOwns.add( (RailroadTile) tile );
            }
        }
        return railroadsPlayerOwns;
    }

}
