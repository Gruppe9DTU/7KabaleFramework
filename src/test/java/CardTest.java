import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {

    private Card testCard = new Card(0, 1);

    @Test
    public void testReadSuit() {
        testCard.setSuit(0);

        assertEquals("Hearts", testCard.readSuit());

        testCard.setSuit(1);

        assertEquals("Spades", testCard.readSuit());

        testCard.setSuit(2);

        assertEquals("Diamonds", testCard.readSuit());

        testCard.setSuit(3);

        assertEquals("Clubs", testCard.readSuit());
    }

    @Test
    public void testToString() {
        testCard.setSuit(0);
        testCard.setValue(1);
        assertEquals("Ace of Hearts", testCard.toString());
        testCard.setValue(11);
        assertEquals("Jack of Hearts", testCard.toString());
        testCard.setValue(12);
        assertEquals("Queen of Hearts", testCard.toString());
        testCard.setValue(13);
        assertEquals("King of Hearts", testCard.toString());
        testCard.setValue(4);
        assertEquals("4 of Hearts", testCard.toString());
    }

}