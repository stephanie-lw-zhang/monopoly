package BackEnd.AssetHolder;

import BackEnd.Tile.PropertyTiles.AbstractPropertyTile;

import java.util.List;

public class Bank extends AbstractAssetHolder {

    public Bank(Double money) {
        super(money);
    }

    //money is supposed to be unlimited in standard version
    @Override
    public void paysTo(AbstractAssetHolder receiver, Double debt) {
        receiver.setMoney( receiver.getMoney() + debt );
    }

}
