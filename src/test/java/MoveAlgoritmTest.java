import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MoveAlgoritmTest {
    private MoveAlgoritm algoritmCtrl;
    private Tableau[] tableaus;
    private Foundation[] foundations;

    /**
     * Setup method
     */
    @Before
    public void setup() {
        tableaus = new Tableau[7];
        for(int i = 0 ; i < 7 ; i++){
            tableaus[i] = new Tableau(0);
        }
        foundations = new Foundation[4];
        for(int i = 0 ; i < 4 ; i++){
            foundations[i] = new Foundation();
        }
    }

    /**
     * Test if it sees possibility from moving card from waste after foundation card is moved to a tableau
     */
    @Test
    public void testFoundationToTableau101() {
        //Create a tableau with 10 of Hearts
        Card tableauCard = new Card(0, 10);
        tableaus[1].addCardToStack(tableauCard);

        //Create a foundation with 9 of Spades at the top
        foundations[2].addCard(new Card(1, 1));
        foundations[2].addCard(new Card(1, 2));
        foundations[2].addCard(new Card(1, 3));
        foundations[2].addCard(new Card(1, 4));
        foundations[2].addCard(new Card(1, 5));
        foundations[2].addCard(new Card(1, 6));
        foundations[2].addCard(new Card(1, 7));
        foundations[2].addCard(new Card(1, 8));
        Card foundationCard = new Card(1, 9);
        foundations[2].addCard(foundationCard);

        //Create a wastepile with 8 of Hearts on top
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);
        Card card = waste.revealCard();

        //Setup Algorithm class
        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());
        //Test
        assertEquals("Ryk " + foundationCard.toString() + " fra grundbunken ned på rækken med " + tableauCard, algoritmCtrl.testGrundbunkeToBuildStable());
    }

    /**
     * Test if it sees possibility from moving card from another tableau after foundation card is moved to a tableau.
     * Note: No cards have been taken from waste.
     */
    @Test
    public void testFoundationToTableau102() {
        //Create tableaus, one with 10 of Hearts and one with 8 of Hearts, with some cards on it.
        Card tableauDestinationCard = new Card(0, 10);
        tableaus[1].addCardToStack(tableauDestinationCard);
        tableaus[2].addCardToStack(new Card(0, 8));
        tableaus[2].addCardToStack(new Card(1, 7));
        tableaus[2].addCardToStack(new Card(0, 6));

        //Create a foundation with 9 of Spades at the top
        foundations[2].addCard(new Card(1, 1));
        foundations[2].addCard(new Card(1, 2));
        foundations[2].addCard(new Card(1, 3));
        foundations[2].addCard(new Card(1, 4));
        foundations[2].addCard(new Card(1, 5));
        foundations[2].addCard(new Card(1, 6));
        foundations[2].addCard(new Card(1, 7));
        foundations[2].addCard(new Card(1, 8));
        Card foundationCard = new Card(1, 9);
        foundations[2].addCard(foundationCard);

        //Create a wastepile placeholder
        Waste waste = new Waste(null, true);

        //Setup Algorithm class
        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        //Test
        assertEquals("Ryk " + foundationCard.toString() + " fra grundbunken ned på rækken med " + tableauDestinationCard, algoritmCtrl.testGrundbunkeToBuildStable());
    }

    @Test
    public void typeStreak(){
        //Create tableaus, one with 10 of Hearts and one with 8 of Hearts, with some cards on it.
        Card expected1 = new Card(0, 6);
        Card expected2 = new Card(1, 7);
        tableaus[1].addCardToStack(new Card(1, 9));
        tableaus[2].addCardToStack(new Card(3, 11));
        tableaus[2].addCardToStack(new Card(0, 10));
        tableaus[3].addCardToStack(expected1);
        tableaus[4].addCardToStack(new Card(3, 9));
        tableaus[5].addCardToStack(new Card(0, 8));
        tableaus[5].addCardToStack(expected2);

        //Create a wastepile placeholder
        Waste waste = new Waste(null, true);

        //Setup Algorithm class
        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        //Test
        assertEquals("Tag " + expected1.toString() + " og placer kortet på " + expected2.toString(), algoritmCtrl.typeStreak());
    }

    @Test
    public void typeStreakWithWaste(){
        //Create tableaus, one with 10 of Hearts and one with 8 of Hearts, with some cards on it.
        Card expected1 = new Card(0, 3);
        Card expected2 = new Card(3, 2);
        tableaus[1].addCardToStack(new Card(1, 9));
        tableaus[2].addCardToStack(new Card(3, 11));
        tableaus[2].addCardToStack(new Card(0, 10));
        tableaus[3].addCardToStack(new Card(3, 4));
        tableaus[3].addCardToStack(expected1);
        tableaus[4].addCardToStack(new Card(2, 9));
        tableaus[5].addCardToStack(new Card(0, 8));

        //Create a wastepile with 3 of Clubs on top
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(expected2);
        Waste waste = new Waste(wasteCards, true);
        waste.revealCard();

        //Setup Algorithm class
        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        //Test
        assertEquals("Tag " + expected2.toString() + " og placer kortet på " + expected1.toString(), algoritmCtrl.typeStreak());
    }

}