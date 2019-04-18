package testing;

import backend.dice.AbstractDice;
import backend.dice.NDice;
import backend.dice.SixDice;
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
