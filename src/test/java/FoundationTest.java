import org.junit.Test;

import static org.junit.Assert.*;

public class FoundationTest {

    @Test
    public void testAddCard() {
        Foundation testFoundation = new Foundation();
        Card testCard1 = new Card(0, 1);
        Card testCard2 = new Card(0, 2);
        Card testCard3 = new Card(1, 2);
        Card testCard4 = new Card(0, 3);
        testFoundation.addCard(testCard2);
        assertEquals(0, testFoundation.countCards());
        testFoundation.addCard(testCard1);
        assertEquals(1, testFoundation.countCards());
        assertEquals(testCard1.toString(), testFoundation.peekCard().toString());
        testFoundation.addCard(testCard3);
        assertEquals(1, testFoundation.countCards());
        assertEquals(testCard1.toString(), testFoundation.peekCard().toString());
        testFoundation.addCard(testCard4);
        assertEquals(1, testFoundation.countCards());
        assertEquals(testCard1.toString(), testFoundation.peekCard().toString());
        testFoundation.addCard(testCard2);
        assertEquals(2, testFoundation.countCards());
        assertEquals(testCard2.toString(), testFoundation.peekCard().toString());
        testFoundation.addCard(testCard4);
        assertEquals(3, testFoundation.countCards());
        assertEquals(testCard4.toString(), testFoundation.peekCard().toString());

    }

    @Test
    public void testTakeCard() {
        Foundation testFoundation = new Foundation();
        Card testCard1 = new Card(0, 1);
        testFoundation.addCard(testCard1);
        assertEquals(testCard1.toString(), testFoundation.takeCard().toString());
    }

    @Test
    public void testIsComplete() {
        Foundation testFoundation = new Foundation();
        Card[] cards = new Card[13];
        for( int i = 0; i < 13; i++)
            cards[i] = new Card(0, i+1);
        for( Card c : cards )
            testFoundation.addCard(c);
        assertTrue(testFoundation.isComplete());
    }
}