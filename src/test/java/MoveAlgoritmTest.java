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

        tableaus[0].addCardToStack(new Card(1, 3)); //random card

        tableaus[1].addCardToStack(new Card(1, 2)); //random card
        tableaus[1].addCardToStack(tableauCard);

        tableaus[2].addCardToStack(new Card(0, 3));
        tableaus[3].addCardToStack(new Card(1, 4)); //random card
        tableaus[4].addCardToStack(new Card(1, 6)); //random card

        tableaus[5].addCardToStack(new Card(1, 3)); //random card
        tableaus[5].addCardToStack(new Card(0, 2)); //random card
        tableaus[5].addCardToStack(tableauCard2);

        tableaus[6].addCardToStack(new Card(1, 7)); //random card


        //Create a wastepile with 8 of Hearts on top
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Ryk " + tableauCard2.toString() + " til Foundation", algoritmCtrl.getBestMove());
    }

    /**
     * Test if ace with highest cards found from the bottom is chosen for checkEs
     */
    @Test
    public void testCheckEs2(){

        Card tableauCard = new Card(0,1); //ace of hearts
        Card tableauCard2 = new Card(1,1); //ace of spades

        tableaus[0].addCardToStack(new Card(1, 3)); //random card

        tableaus[1].addCardToStack(new Card(0, 3)); //random card
        tableaus[1].addCardToStack(new Card(1, 2)); //random card
        tableaus[1].addCardToStack(tableauCard);

        tableaus[2].addCardToStack(new Card(0, 4));
        tableaus[3].addCardToStack(new Card(1, 6)); //random card
        tableaus[4].addCardToStack(new Card(1, 7)); //random card

        tableaus[5].addCardToStack(new Card(0, 2)); //random card
        tableaus[5].addCardToStack(tableauCard2);

        tableaus[6].addCardToStack(new Card(1, 9)); //random card


        //Create a dummy wastepile
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Ryk " + tableauCard.toString() + " til Foundation", algoritmCtrl.getBestMove());
    }

    /**
     * Test if king can be moved to empty space
     */
    @Test
    public void testKingCheck(){

        Card tableauCard = new Card(0,13); //King of hearts

        tableaus[0].addCardToStack(new Card(1, 3)); //random card

        tableaus[1].addCardToStack(tableauCard);

        tableaus[2].addCardToStack(new Card(0, 4));
        tableaus[3].addCardToStack(new Card(1, 6)); //random card
        tableaus[4].addCardToStack(new Card(1, 7)); //random card

        //Create a dummy wastepile
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Move " + tableauCard.toString() + " to empty space", algoritmCtrl.kingCheck());

    }

    /**
     * Test if correct king is chosen to take empty space
     */
    @Test
    public void testKingCheck2(){

        Card tableauCard = new Card(0,13); //King of hearts
        Card tableauCard2 = new Card(1,13); //King of spades

        tableaus[0].addCardToStack(new Card(0, 12)); //Queen of hearts
        tableaus[1].addCardToStack(tableauCard);
        tableaus[2].addCardToStack(new Card(0, 11)); //jack of hearts
        tableaus[3].addCardToStack(new Card(1, 12)); //Queen of spades
        tableaus[4].addCardToStack(tableauCard2);

        //Create a dummy wastepile
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Best king to move is " + tableauCard.toString(), algoritmCtrl.kingCheck());

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
        assertEquals("Ryk " + foundationCard.toString() + " fra grundbunken ned på rækken med " + tableauCard, algoritmCtrl.grundbunkeToBuildStable());
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
        assertEquals("Ryk " + foundationCard.toString() + " fra grundbunken ned på rækken med " + tableauDestinationCard, algoritmCtrl.grundbunkeToBuildStable());
    }

    /**
     * Move a card from tableau to foundation, without opening a space
     */
    @Test
    public void testMoveToFoundation101() {
        tableaus[1].addCardToStack(new Card(1, 12));
        Card tableauCard = new Card(0,11);
        tableaus[1].addCardToStack(tableauCard);

        for(int i = 1 ; i < 10 ; i++) {
            foundations[1].addCard(new Card(0,i));
        }
        Card foundationCard = new Card(0,10);
        foundations[1].addCard(foundationCard);

        Waste waste = new Waste(null, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Move " + tableauCard.toString() + " to it's respective foundation", algoritmCtrl.moveToFoundation());
    }

    /**
     * Don't move card from tableau if opening an empty space, and it doesn't open up new moves
     */
    @Test
    public void testMoveToFoundation102() {
        Card tableauCard = new Card(0,11);
        tableaus[1].addCardToStack(tableauCard);

        for(int i = 1 ; i < 10 ; i++) {
            foundations[1].addCard(new Card(0,i));
        }
        Card foundationCard = new Card(0,10);
        foundations[1].addCard(foundationCard);

        Waste waste = new Waste(null, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("", algoritmCtrl.moveToFoundation());
    }

    /**
     * Move card from tableau and leave empty space, if King is present to take over without leaving an empty space
     */
    @Test
    public void testMoveToFoundation103() {
        Card tableauCard = new Card(0,11);
        tableaus[1].addCardToStack(tableauCard);
        tableaus[2] = new Tableau(3);
        tableaus[2].addCardToStack(new Card(3, 13));

        for(int i = 1 ; i < 10 ; i++) {
            foundations[1].addCard(new Card(0,i));
        }
        Card foundationCard = new Card(0,10);
        foundations[1].addCard(foundationCard);

        Waste waste = new Waste(null, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Move " + tableauCard.toString() + " to it's respective foundation", algoritmCtrl.moveToFoundation());
    }

    /**
     * Don't move card from tableau if leaving empty space and no King is able to take over without leaving empty spaces
     */
    @Test
    public void testMoveToFoundation104() {
        Card tableauCard = new Card(0,11);
        tableaus[1].addCardToStack(tableauCard);
        tableaus[2].addCardToStack(new Card(3, 13));

        for(int i = 1 ; i < 10 ; i++) {
            foundations[1].addCard(new Card(0,i));
        }
        Card foundationCard = new Card(0,10);
        foundations[1].addCard(foundationCard);

        Waste waste = new Waste(null, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("", algoritmCtrl.moveToFoundation());
    }

    /**
     * Move card from tableau and leave empty space, if next card for foundation is available
     */
    @Test
    public void testMoveToFoundation105() {
        Card tableauCard = new Card(0, 11);
        tableaus[1].addCardToStack(tableauCard);
        tableaus[2].addCardToStack(new Card(0, 12));

        for(int i = 1 ; i < 10 ; i++) {
            foundations[1].addCard(new Card(0,i));
        }
        Card foundationCard = new Card(0,10);
        foundations[1].addCard(foundationCard);

        Waste waste = new Waste(null, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Move " + tableauCard.toString() + " to it's respective foundation", algoritmCtrl.moveToFoundation());
    }

    /**
     * Move card from waste if possible
     */
    @Test
    public void testMoveToFoundation106() {
        for(int i = 1 ; i < 10 ; i++) {
            foundations[1].addCard(new Card(0,i));
        }
        Card foundationCard = new Card(0,10);
        foundations[1].addCard(foundationCard);
        List<Card> wastePile = new ArrayList<Card>();
        Card wasteCard = new Card(0,11);
        wastePile.add(wasteCard);
        Waste waste = new Waste(wastePile, true);
        waste.revealCard();

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Move " + wasteCard.toString() + " to it's respective foundation", algoritmCtrl.moveToFoundation());
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