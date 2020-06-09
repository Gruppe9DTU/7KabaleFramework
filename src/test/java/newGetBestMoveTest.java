import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class newGetBestMoveTest {

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
     * Test if duplicate move is recognized and a the next in line is chosen
     */

    @Test
    public void newGetBestMove1(){

        PreviousMoves previousMoves = new PreviousMoves();

        Card tableauCard = new Card(0,1); //ace of hearts

        tableaus[0].addCardToStack(new Card(1, 3)); //random card

        tableaus[1].addCardToStack(new Card(1, 2)); //random card
        tableaus[1].addCardToStack(tableauCard);

        tableaus[2].addCardToStack(new Card(0, 3));
        tableaus[3].addCardToStack(new Card(1, 4)); //random card
        tableaus[4].addCardToStack(new Card(1, 6)); //random card

        tableaus[5].addCardToStack(new Card(1, 3)); //random card
        tableaus[5].addCardToStack(new Card(0, 2)); //random card

        tableaus[6].addCardToStack(new Card(1, 7)); //random card


        //Create a wastepile with 8 of Hearts on top
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        Gamelogic gamelogic = new Gamelogic();

        gamelogic.setTableau(tableaus);
        gamelogic.setFoundation(foundations);
        gamelogic.setWaste(waste);

        previousMoves.addPreviousMove(gamelogic.printGame());
        previousMoves.addPreviousMove(gamelogic.printGame());
        MoveAlgoritm move = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());


        assertEquals("Dette layout af kort er blevet præsenteret før, hvis tidligere træk ikke virkede, kan du prøve: \n" +
                        "Tag 2 of Hearts og placer kortet på 3 of Spades"
                , move.getBestMove(previousMoves));
    }

    /**
     * Test that two different moves produces result without recognition
     */
    @Test
    public void newGetBestMove2(){

        PreviousMoves previousMoves = new PreviousMoves();

        Card tableauCard = new Card(0,1); //ace of hearts

        tableaus[0].addCardToStack(new Card(1, 3)); //random card

        tableaus[1].addCardToStack(new Card(1, 2)); //random card
        tableaus[1].addCardToStack(tableauCard);

        tableaus[2].addCardToStack(new Card(0, 3));
        tableaus[3].addCardToStack(new Card(1, 4)); //random card
        tableaus[4].addCardToStack(new Card(1, 6)); //random card

        tableaus[5].addCardToStack(new Card(1, 3)); //random card

        tableaus[6].addCardToStack(new Card(1, 7)); //random card


        //Create a wastepile with 8 of Hearts on top
        List<Card> wasteCards = new ArrayList<Card>();
        wasteCards.add(new Card(0, 8));
        Waste waste = new Waste(wasteCards, true);

        Gamelogic gamelogic = new Gamelogic();

        gamelogic.setTableau(tableaus);
        gamelogic.setFoundation(foundations);
        gamelogic.setWaste(waste);

        previousMoves.addPreviousMove(gamelogic.printGame());
        MoveAlgoritm move = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Ryk Ace of Hearts til Foundation", move.getBestMove(previousMoves));

        tableaus[5].addCardToStack(new Card(0, 2)); //random card //single new card should be enough to change outcome
        gamelogic.setTableau(tableaus);
        previousMoves.addPreviousMove(gamelogic.printGame());
        MoveAlgoritm move2 = new MoveAlgoritm(Arrays.asList(tableaus), Arrays.asList(foundations), waste.lookAtTop(), waste.getPileStatus());

        assertEquals("Ryk Ace of Hearts til Foundation", move2.getBestMove(previousMoves));

    }

}
