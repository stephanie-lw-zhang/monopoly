
package testing.backendtests;

class PlayerTest {
/**
    Bank bank;
    AbstractPlayer autoPlayer;
    AbstractPlayer humanPlayer;

    @BeforeEach
    void setUp(){
        bank = new Bank(10000.0);
        autoPlayer = new AutomatedPlayer(1500.0, bank);
        humanPlayer = new HumanPlayer(1500.0, bank);
    }

    @Test
    void addTurnsInJail(){
        autoPlayer.addTurnInJail();
        assertEquals(0, autoPlayer.getTurnsInJail());
    }

    @Test
    void getOutOfJail(){
        autoPlayer.addTurnInJail();
        autoPlayer.getOutOfJail();
        assertEquals(-1, autoPlayer.getTurnsInJail());
    }

    @Test
    void testBankruptcyBoolean(){
        autoPlayer.declareBankruptcyTo(bank);
        assertTrue(autoPlayer.isBankrupt());
    }

    @Test
    void testMoneyAfterBankruptcy(){
        autoPlayer.declareBankruptcyTo(bank);
        assertEquals(autoPlayer.getMoney(), 0);
    }

    @Test
    void testPropertiesAfterBankruptcy(){
        autoPlayer.declareBankruptcyTo(bank);
        assertTrue(autoPlayer.getProperties().isEmpty());
    }

    @Test
    void testPayTakesFunds(){
        autoPlayer.payFullAmountTo(humanPlayer, 200.0);
        assertEquals(1300.0, autoPlayer.getMoney());
    }

    @Test
    void testPayReceiverGetsFunds(){
        autoPlayer.payFullAmountTo(humanPlayer, 200.0);
        assertEquals(1700.0, humanPlayer.getMoney());
    }

    @Test
    void testAddingProperties(){
        BuildingTile tile =new BuildingTile(bank, new BuildingCard("Property", 0.0,0.0, java.awt.Color.BLUE,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0), "Property", 200, Color.BLUE);
        autoPlayer.addProperty(tile);
        assertTrue(autoPlayer.getProperties().contains(tile));
    }
*/
}

//package backend.assetholder;
//
//import backend.card.property_cards.BuildingCard;
//import backend.card.property_cards.PropertyCard;
//import backend.tile.BuildingTile;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.awt.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class PlayerTest {
//
//    Bank bank;
//    AbstractPlayer autoPlayer;
//    AbstractPlayer humanPlayer;
//
//    @BeforeEach
//    void setUp(){
//        bank = new Bank(10000.0);
//        autoPlayer = new AutomatedPlayer(1500.0, bank);
//        humanPlayer = new HumanPlayer(1500.0, bank);
//    }
//
//    @Test
//    void addTurnsInJail(){
//        autoPlayer.addTurnInJail();
//        assertEquals(0, autoPlayer.getTurnsInJail());
//    }
//
//    @Test
//    void getOutOfJail(){
//        autoPlayer.addTurnInJail();
//        autoPlayer.getOutOfJail();
//        assertEquals(-1, autoPlayer.getTurnsInJail());
//    }
//
//    @Test
//    void testBankruptcyBoolean(){
//        autoPlayer.declareBankruptcyTo(bank);
//        assertTrue(autoPlayer.isBankrupt());
//    }
//
//    @Test
//    void testMoneyAfterBankruptcy(){
//        autoPlayer.declareBankruptcyTo(bank);
//        assertEquals(autoPlayer.getMoney(), 0);
//    }
//
//    @Test
//    void testPropertiesAfterBankruptcy(){
//        autoPlayer.declareBankruptcyTo(bank);
//        assertTrue(autoPlayer.getProperties().isEmpty());
//    }
//
//    @Test
//    void testPayTakesFunds(){
//        autoPlayer.payFullAmountTo(humanPlayer, 200.0);
//        assertEquals(1300.0, autoPlayer.getMoney());
//    }
//
//    @Test
//    void testPayReceiverGetsFunds(){
//        autoPlayer.payFullAmountTo(humanPlayer, 200.0);
//        assertEquals(1700.0, humanPlayer.getMoney());
//    }
//
//    @Test
//    void testAddingProperties(){
//        BuildingTile tile =new BuildingTile(bank, new BuildingCard("Property", 0.0,0.0, java.awt.Color.BLUE,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0), "Property", 200, Color.BLUE);
//        autoPlayer.addProperty(tile);
//        assertTrue(autoPlayer.getProperties().contains(tile));
//    }
//
//}