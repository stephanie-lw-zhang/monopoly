package configuration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class GetMonopolyTest {

    XMLData data;

    @BeforeEach
    void setup2() throws Exception {
        data = new XMLData("OriginalMonopoly.xml");
    }

    @Test
    void testGetMonopolyName() {
        String expected = "Original Monopoly";
        String actual = data.getMonopolyType();
        assertEquals(expected, actual);
    }


}