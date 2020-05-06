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
     * Test what ace is prioritised to go into the foundation
     */
    @Test
    public void testCheckEs(){
        Card tableauCard = new Card(0,1); //ace of hearts
        Card tableauCard2 = new Card(1,1); //ace of spades
        tableaus[0].addCardToStack(new Card(1, 3)); //randome card
        tableaus[1].addCardToStack(new Card(1, 2)); //randome card
        tableaus[1].addCardToStack(tableauCard);
        tableaus[2].addCardToStack(new Card(0, 3));
        tableaus[3].addCardToStack(new Card(1, 4)); //randome card
        tableaus[4].addCardToStack(new Card(1, 6)); //randome card
        tableaus[5].addCardToStack(new Card(1, 3)); //randome card
        tableaus[5].addCardToStack(new Card(0, 2)); //randome card
        tableaus[5].addCardToStack(tableauCard2);
        tableaus[6].addCardToStack(new Card(1, 7)); //randome card


        //Create a wastepile with 8 of Hearts on top
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Ryk " + tableauCard2.toString() + " til Foundation", algoritmCtrl.checkEs());
    }

    /**
     * Test if ace with highest cards found from the bottom is chosen for checkEs
     */
    @Test
    public void testCheckEs2(){
        Card tableauCard = new Card(0,1); //ace of hearts
        Card tableauCard2 = new Card(1,1); //ace of spades
        tableaus[0].addCardToStack(new Card(1, 3)); //randome card
        tableaus[1].addCardToStack(new Card(0, 3)); //randome card
        tableaus[1].addCardToStack(new Card(1, 2)); //randome card
        tableaus[1].addCardToStack(tableauCard);
        tableaus[2].addCardToStack(new Card(0, 4));
        tableaus[3].addCardToStack(new Card(1, 6)); //randome card
        tableaus[4].addCardToStack(new Card(1, 7)); //randome card
        tableaus[5].addCardToStack(new Card(0, 2)); //randome card
        tableaus[5].addCardToStack(tableauCard2);
        tableaus[6].addCardToStack(new Card(1, 9)); //randome card


        //Create a dummy wastepile
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Ryk " + tableauCard.toString() + " til Foundation", algoritmCtrl.checkEs());
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
}