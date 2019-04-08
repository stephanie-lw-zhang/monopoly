package BackEnd.AssetHolder;

import BackEnd.Tile.PropertyTiles.AbstractPropertyTile;
import BackEnd.Tile.PropertyTiles.BuildingTile;
import BackEnd.Tile.PropertyTiles.RailroadTile;

import java.util.List;

public class Bank extends AbstractAssetHolder {
    private int numHousesLeft = 32;
    private int numHotelsLeft = 12;

    public Bank(Double money) {
        super(money);
    }

    @Override
    public void addProperty(AbstractPropertyTile property) {
        this.getProperties().add( property );
        if(property instanceof BuildingTile){
            //SWITCH TO BUILDINGTILE
            numHousesLeft += ((BuildingTile) property).getNumberOfHouses();
            numHotelsLeft += ((BuildingTile) property).getNumberOfHotels();
        }
    }

    public void subtractOneHouse(){
        numHousesLeft -= 1;
    }

    public void subtractOneHotel(){
        numHotelsLeft -= 1;
    }

    public void addHouses(int x) { numHousesLeft += x; }

    public void addHotels(int x) { numHotelsLeft += x; }

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



    //money is supposed to be unlimited in standard version
    @Override
    public void paysTo(AbstractAssetHolder receiver, Double debt) {
        receiver.setMoney( receiver.getMoney() + debt );
    }

}
