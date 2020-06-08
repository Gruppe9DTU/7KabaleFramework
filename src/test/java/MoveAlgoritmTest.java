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
     * Test when it has kings, but no empty spaces
     */
    @Test
    public void testKingCheck101() {
        tableaus[0].addCardToStack(new Card(0, 13));
        tableaus[1].addCardToStack(new Card(0, 13));
        tableaus[2].addCardToStack(new Card(0, 13));
        tableaus[3].addCardToStack(new Card(0, 13));
        tableaus[4].addCardToStack(new Card(0, 13));
        tableaus[5].addCardToStack(new Card(0, 13));
        tableaus[6].addCardToStack(new Card(0, 13));

        //Create a dummy wastepile
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("", algoritmCtrl.kingCheck());
    }

    /**
     * Test with only open spaces
     */
    @Test
    public void testKingCheck102() {
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("", algoritmCtrl.kingCheck());
    }

    /**
     * Test with no kings, but open room
     */
    @Test
    public void testKingCheck103() {
        tableaus[0].addCardToStack(new Card(0, 12));
        tableaus[1].addCardToStack(new Card(0, 12));
        tableaus[2].addCardToStack(new Card(0, 12));
        tableaus[3].addCardToStack(new Card(0, 12));

        //Create a dummy wastepile
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("", algoritmCtrl.kingCheck());
    }

    /**
     * No kings, but empty spaces
     */
    @Test
    public void testKingCheck104() {
        tableaus[0].addCardToStack(new Card(1, 5));
        tableaus[1].addCardToStack(new Card(1, 7));
        tableaus[2].addCardToStack(new Card(0, 8));
        tableaus[3].addCardToStack(new Card(0, 7));
        tableaus[4].addCardToStack(new Card(0, 6));

        //Create a dummy wastepile
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("", algoritmCtrl.kingCheck());
    }

    /**
     * No empty spaces
     */
    @Test
    public void testKingCheck105() {
        tableaus[0] = new Tableau(2, Arrays.asList(new Card(0, 13)));
        tableaus[1] = new Tableau(2, Arrays.asList(new Card(1, 13)));
        tableaus[2].addCardToStack(new Card(1, 5));
        tableaus[3].addCardToStack(new Card(1, 7));
        tableaus[4].addCardToStack(new Card(0, 8));
        tableaus[5].addCardToStack(new Card(0, 7));
        tableaus[6].addCardToStack(new Card(0, 6));

        //Create a dummy wastepile
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("", algoritmCtrl.kingCheck());
    }

    /**
     * Test when single king is present and there is an open space for him
     */
    @Test
    public void testKingCheck110() {
        Card wantedCard[] = {new Card(0,13)}; //King of hearts

        tableaus[0].addCardToStack(new Card(1, 3)); //random card

        tableaus[1] = new Tableau(2, Arrays.asList(wantedCard));

        tableaus[2].addCardToStack(new Card(0, 4));
        tableaus[3].addCardToStack(new Card(1, 6)); //random card
        tableaus[4].addCardToStack(new Card(1, 7)); //random card

        //Create a dummy wastepile
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Move " + wantedCard[0].toString() + " to an empty space", algoritmCtrl.kingCheck());
    }

    /**
     * With 2 kings of equal value with cards behind them and same colour, with empty spaces
     */
    @Test
    public void testKingCheck111() {
        Card wantedCard[] = {new Card(0,13)};
        Card distractionCard[] = {new Card(2,13)};

        tableaus[0] = new Tableau(1, Arrays.asList(wantedCard));
        tableaus[1] = new Tableau(1, Arrays.asList(distractionCard));

        //Create a dummy wastepile
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Move any king to an empty space", algoritmCtrl.kingCheck());
    }

    /**
     * With 2 kings of equal value with cards behind them and different colour, with empty spaces
     */
    @Test
    public void testKingCheck112() {
        Card wantedCard[] = {new Card(0,13)};
        Card distractionCard[] = {new Card(1,13)};

        tableaus[0] = new Tableau(1, Arrays.asList(wantedCard));
        tableaus[1] = new Tableau(1, Arrays.asList(distractionCard));

        //Create a dummy wastepile
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Move any king to an empty space", algoritmCtrl.kingCheck());
    }

    /**
     * With 2 kings of equal value with no cards behind them and same colour, with empty spaces
     */
    @Test
    public void testKingCheck113() {
        Card wantedCard[] = {new Card(0,13)};
        Card distractionCard[] = {new Card(2,13)};

        tableaus[0] = new Tableau(0, Arrays.asList(wantedCard));
        tableaus[1] = new Tableau(0, Arrays.asList(distractionCard));

        //Create a dummy wastepile
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("", algoritmCtrl.kingCheck());
    }

    /**
     * With 2 kings, but first has more hidden cards behind it, with empty spaces
     */
    @Test
    public void testKingCheck114() {
        Card wantedCard[] = {new Card(0,13)}; //King of hearts
        Card distractionCard[] = {new Card(1,13)}; //King of spades

        tableaus[0] = new Tableau(2, Arrays.asList(wantedCard));
        tableaus[1].addCardToStack(new Card(0, 10)); //10 of hearts
        tableaus[2] = new Tableau(1, Arrays.asList(distractionCard));
        tableaus[3].addCardToStack(new Card(0, 5)); //5 of hearts
        tableaus[4].addCardToStack(new Card(1, 10)); //10 of spades

        //Create a dummy wastepile
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Move " + wantedCard[0].toString() + " to an empty space", algoritmCtrl.kingCheck());
    }

    /**
     * With 2 kings, but second has more hidden cards behind it, with empty spaces
     */
    @Test
    public void testKingCheck115() {
        Card wantedCard[] = {new Card(0,13)}; //King of hearts
        Card distractionCard[] = {new Card(1,13)}; //King of spades

        tableaus[1] = new Tableau(1, Arrays.asList(distractionCard));
        tableaus[1].addCardToStack(new Card(0, 10)); //10 of hearts
        tableaus[2] = new Tableau(2, Arrays.asList(wantedCard));
        tableaus[3].addCardToStack(new Card(0, 5)); //5 of hearts
        tableaus[4].addCardToStack(new Card(1, 10)); //10 of spades

        //Create a dummy wastepile
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Move " + wantedCard[0].toString() + " to an empty space", algoritmCtrl.kingCheck());
    }

    /**
     * With 2 equal value kings, first is better because of lower value card exists, with empty spaces
     */
    @Test
    public void testKingCheck116() {
        Card wantedCard[] = {new Card(0,13)}; //King of hearts
        Card distractionCard[] = {new Card(1,13)}; //King of spades

        tableaus[0].addCardToStack(new Card(0, 12)); //Queen of hearts
        tableaus[1] = new Tableau(2, Arrays.asList(wantedCard));
        tableaus[2].addCardToStack(new Card(0, 11)); //jack of hearts
        tableaus[3].addCardToStack(new Card(1, 12)); //Queen of spades
        tableaus[4] = new Tableau(2, Arrays.asList(distractionCard));

        //Create a dummy wastepile
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Move " + wantedCard[0].toString() + " to an empty space", algoritmCtrl.kingCheck());
    }

    /**
     * With 2 equal value kings, second is better because of lower value cards exists, with empty spaces
     */
    @Test
    public void testKingCheck117() {
        Card wantedCard[] = {new Card(0,13)}; //King of hearts
        Card distractionCard[] = {new Card(1,13)}; //King of spades

        tableaus[0].addCardToStack(new Card(0, 12)); //Queen of hearts
        tableaus[1] = new Tableau(2, Arrays.asList(distractionCard));
        tableaus[2].addCardToStack(new Card(0, 11)); //jack of hearts
        tableaus[3].addCardToStack(new Card(1, 12)); //Queen of spades
        tableaus[4] = new Tableau(2, Arrays.asList(wantedCard));

        //Create a dummy wastepile
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Move " + wantedCard[0].toString() + " to an empty space", algoritmCtrl.kingCheck());
    }

    /**
     * With 2 equal value kings, first is better because lower value card has more hidden cards behind it, with empty spaces
     */
    @Test
    public void testKingCheck118() {
        Card wantedCard[] = {new Card(0,13)}; //King of Hearts
        Card distractionCard[] = {new Card(1,13)}; //King of Spades
        Card cardOfInterest[] = {new Card(1, 12)}; //Queen of Spades

        tableaus[0] = new Tableau(2, Arrays.asList(cardOfInterest)); //Queen of Spades
        tableaus[1] = new Tableau(2, Arrays.asList(distractionCard)); //King of Spades
        tableaus[3].addCardToStack(new Card(0, 12)); //Queen of Hearts
        tableaus[4] = new Tableau(2, Arrays.asList(wantedCard));//King of Hearts

        //Create a dummy wastepile
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Move " + wantedCard[0].toString() + " to an empty space", algoritmCtrl.kingCheck());
    }

    /**
     * With 2 equal value kings, second is better because lower value card has more hidden cards behind it, with empty spaces
     */
    @Test
    public void testKingCheck119() {

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
        assertEquals("Ryk " + foundationCard.toString() + " fra grundbunken ned på rækken med " + tableauCard, algoritmCtrl.foundationToTableau());
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
        assertEquals("Ryk " + foundationCard.toString() + " fra grundbunken ned på rækken med " + tableauDestinationCard, algoritmCtrl.foundationToTableau());
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

    @Test
    public void testRevealHiddenCard() {
        tableaus[1] = new Tableau(3);
        tableaus[1].addCardToStack(new Card(0, 4));
        tableaus[2] = new Tableau(2);

        Waste waste = new Waste(null, true);

        algoritmCtrl = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        //Test
        assertEquals("Turn over a card from the tableau with the highest amount of hidden cards", algoritmCtrl.revealHiddenCard());
    }

}