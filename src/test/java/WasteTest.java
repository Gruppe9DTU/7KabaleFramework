import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class WasteTest {

    @Test
    public void testRevealCard() {
        Deck testdeck = new Deck();
        testdeck.generateDeck();
        List<Card> unknowncards = testdeck.getDeck();
        Waste testWaste = new Waste(unknowncards);
        assertEquals("King of Clubs", testWaste.revealCard().toString());
        for (int i = 0; i < 51; i++) {
            testWaste.revealCard();
        }
        assertEquals("King of Clubs", testWaste.getKnownCards().get(0).toString());
        assertEquals("King of Clubs", testWaste.revealCard().toString());
        assertEquals("Queen of Clubs", testWaste.revealCard().toString());
        for (int i = 0; i < 50; i++) {
            testWaste.revealCard();
        }
        assertEquals("King of Clubs", testWaste.revealCard().toString());
    }

    @Test
    public void testTakeCard() {
        Deck testdeck = new Deck();
        testdeck.generateDeck();
        List<Card> unknowncards = testdeck.getDeck();
        Waste testWaste = new Waste(unknowncards);
        testWaste.revealCard();
        assertEquals("King of Clubs", testWaste.takeCard().toString());
        assertEquals(0, testWaste.getKnownCards().size());
    }
}