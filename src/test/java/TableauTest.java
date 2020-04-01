import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TableauTest {

    @Test
    public void testGetVisibleCards() {
        Deck game = new Deck();
        game.generateDeck();
        List<Card> deck = game.getDeck();
        Tableau stack = new Tableau(6);
        stack.addCardToStack(deck.get(0));
        Card[] expected = {new Card(0,1)};
        assertEquals(expected[0].toString(), stack.getVisibleCards()[0].toString());
    }

    @Test
    public void testRevealCard() {
        Tableau stack = new Tableau(6);
        Card card = new Card(0, 1);
        stack.revealCard(card);
        assertEquals(5, stack.countHiddenCards());
        assertEquals("Ace of Hearts", stack.getVisibleCards()[0].toString());
    }

    @Test
    public void testIsEmpty() {
        Tableau stack = new Tableau(0);
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testAddCardToStack() {
        Tableau stack = new Tableau(3);
        Card testcard1 = new Card(1, 2);
        Card testcard2 = new Card(0, 1);
        Card testcard3 = new Card(0, 3);
        Card testcard4 = new Card(1, 1);
        stack.addCardToStack(testcard1);
        assertEquals("2 of Spades", stack.getVisibleCards()[0].toString());
        stack.addCardToStack(testcard3);
        assertEquals(1, stack.getVisibleCards().length);
        assertEquals("2 of Spades", stack.getVisibleCards()[0].toString());
        stack.addCardToStack(testcard4);
        assertEquals(1, stack.getVisibleCards().length);
        assertEquals("2 of Spades", stack.getVisibleCards()[0].toString());
        stack.addCardToStack(testcard2);
        assertEquals(2, stack.getVisibleCards().length);
        assertEquals("2 of Spades", stack.getVisibleCards()[0].toString());
        assertEquals("Ace of Hearts", stack.getVisibleCards()[1].toString());

    }
}