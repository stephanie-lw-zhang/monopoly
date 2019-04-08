package BackEnd.AssetHolder;

import BackEnd.Tile.PropertyTiles.AbstractPropertyTile;
import BackEnd.Tile.PropertyTiles.RailroadTile;

import java.util.List;

public class Bank extends AbstractAssetHolder {
    private int numHousesLeft = 32;
    private int numHotelsLeft = 12;

    public Bank(Double money) {
        super(money);
    }

    public int getNumHousesLeft(){
        return numHousesLeft;
    }

    public int getNumHotelsLeft(){
        return numHotelsLeft;
    }

//    public void setNumHousesLeft(int number){
//        numHousesLeft = number;
//    }
//
//    public void setNumHotelsLeft(int number){
//        numHotelsLeft = number;
//    }

    public void updateNumHousesAndHotels(){
        numHousesLeft = 32; //total number, change from magic number
        numHotelsLeft = 12; //total number, change from magic number
        for(AbstractPropertyTile property: this.getProperties()){
            if(property instanceof AbstractPropertyTile){
                //SWITCH TO BUILDINGTILE
                numHousesLeft -= property.getNumberOfHouses();
                numHotelsLeft -= property.getNumberOfHouses();
            }
        }
    }


    //money is supposed to be unlimited in standard version
    @Override
    public void paysTo(AbstractAssetHolder receiver, Double debt) {
        receiver.setMoney( receiver.getMoney() + debt );
    }

}
