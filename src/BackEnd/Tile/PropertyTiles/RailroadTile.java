package BackEnd.Tile.PropertyTiles;

import BackEnd.AssetHolder.AbstractAssetHolder;
import BackEnd.AssetHolder.Bank;
import BackEnd.Card.AbstractCard;
import BackEnd.Card.PropertyCard;
import BackEnd.Card.RailroadCard;
import Controller.Game;

import java.util.ArrayList;
import java.util.List;

public class RailroadTile extends AbstractPropertyTile {

    private RailroadCard card;

    public RailroadTile(Bank bank, PropertyCard card, String tiletype, double tileprice) {
        super(bank, card, tiletype, tileprice);
        this.card = (RailroadCard)card;
    }

    public double calculateRentPrice(Game game) {
        if (isMortgaged()) {
            return 0;
        }
        else {
            return card.lookupPrice(getCurrentInUpgradeOrder());

//            int railroadsOwned = 0;
//            for(AbstractPropertyTile property: this.getOwner().getProperties()){
//                if(property instanceof RailroadTile){
//                    railroadsOwned += 1;
//                }
//            }
//            if (railroadsOwned == 1) {
//                return card.();
//            }
//            else if (railroadsOwned == 2) {
//                return card.getRailroadRent2Owned();
//            }
//            else if (railroadsOwned == 3) {
//                return card.getRailroadRent3Owned();
//            }
//            else if (railroadsOwned == 4) {
//                return card.getRailroadrent4Owned();
//            }
//            else {
//                return 0;
//            }

        }
    }

    @Override
    public void sellTo(AbstractAssetHolder assetHolder, double price, List<AbstractPropertyTile> sameSetProperties) {
        super.sellTo(assetHolder, price, sameSetProperties);
        List<RailroadTile> railroadTiles = checkNumberOfRailroadsPlayerOwns(sameSetProperties);

    }

    public List<RailroadTile> checkNumberOfRailroadsPlayerOwns(List<AbstractPropertyTile> properties) {
        List<RailroadTile> railroadsPlayerOwns = new ArrayList<>();
        for (AbstractPropertyTile tile : properties) {
            if (tile instanceof RailroadTile && (tile.getOwner().equals(this.getOwner()))) {
                //throw exception: YOU CANNOT UPGRADE WITHOUT A MONOPOLY ON COLOR
                railroadsPlayerOwns.add((RailroadTile)tile);
            }
        }
        return railroadsPlayerOwns;
    }

}
