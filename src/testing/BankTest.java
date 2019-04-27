package testing;

import backend.assetholder.AbstractPlayer;
import backend.assetholder.Bank;
import backend.assetholder.HumanPlayer;
import backend.board.AbstractBoard;
import backend.board.StandardBoard;
import backend.tile.BuildingTile;
import configuration.XMLData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankTest {

    private Bank bank;
    private BuildingTile building;
    private List<AbstractPlayer> playerList;
    private AbstractBoard board;

    @BeforeEach
    void setUp(){
        XMLData data = new XMLData("TestMonopoly.xml");
        bank = data.getBank();
        playerList = new ArrayList<>();
        playerList.add(new HumanPlayer("TestPlayer", "Icon1", 1000.0));
        board = new StandardBoard(playerList, data);
        building = (BuildingTile) data.getTiles().get(0);
    }
/**
    @Test
    void addProperty() {
        bank.addProperty(building);
        assertEquals(32, bank.buildingsRemain("House"));
    }

    @Test
    void subtractOneHouse() {
        bank.subtractOneHouse();
        assertEquals(31, bank.getNumHousesLeft());
    }

    @Test
    void subtractOneHotel() {
        bank.subtractOneHotel();
        assertEquals(11, bank.getNumHotelsLeft());
    }

    @Test
    void addHouses() {
        bank.addHouses(3);
        assertEquals(35, bank.getNumHousesLeft());
    }

    @Test
    void addHotels() {
        bank.addHotels(3);
        assertEquals(15, bank.getNumHotelsLeft());
    }

    @Test
    void payFullAmountTo() {
        bank.payFullAmountTo(player, 300.0);
        assertEquals(300.0, player.getMoney());
    }
    */
}
