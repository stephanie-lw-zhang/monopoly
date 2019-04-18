package testing;

import backend.Dice.AbstractDice;
import backend.Dice.NDice;
import backend.Dice.SixDice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestAbstractDice {

    AbstractDice sixDice;
    AbstractDice nDice;

    @BeforeEach
    void setup() {
        sixDice = new SixDice();
        nDice = new NDice(12);
    }

    @Test
    void testRoll() {

    }
}
