import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MoveAlgoritmTest {
    private MoveAlgoritm algoritmCtrl;
    /**
     * Test if it sees possibility from moving card from waste after foundation card is moved to a tableau
     */
    @Test
    public void testFoundationToTableau101() {
        //Create a foundation with 10 of Hearts
        Tableau[] tableaus = new Tableau[1];
        tableaus[0] = new Tableau(0);
        Card tableauCard = new Card(0, 10);
        tableaus[0].addCardToStack(tableauCard);

        Foundation[] foundations = new Foundation[1];
        foundations[0] = new Foundation();
        foundations[0].addCard(new Card(1, 1));
        foundations[0].addCard(new Card(1, 2));
        foundations[0].addCard(new Card(1, 3));
        foundations[0].addCard(new Card(1, 4));
        foundations[0].addCard(new Card(1, 5));
        foundations[0].addCard(new Card(1, 6));
        foundations[0].addCard(new Card(1, 7));
        foundations[0].addCard(new Card(1, 8));
        Card foundationCard = new Card(1, 9);
        foundations[0].addCard(foundationCard);

        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);
        Card card = waste.revealCard();
        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());
        //Test
        assertEquals("Ryk " + foundationCard.toString() + " fra grundbunken ned på rækken med " + tableauCard, algoritmCtrl.testGrundbunkeToBuildStable());
    }

    /**
     * Test if it sees possibility from moving card from another tableau after foundation card is moved to a tableau
     */
    @Test
    public void testFoundationToTableau102() {
        //Create two tableaus, one with 10 of Hearts and one with 8 of Hearts
        Tableau[] tableaus = new Tableau[1];
        tableaus[0] = new Tableau(0);
        Card tableauDestinationCard = new Card(0, 10);
        tableaus[0].addCardToStack(tableauDestinationCard);
    }
}