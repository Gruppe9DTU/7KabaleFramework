import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PreviousMoveRegonizeTest {

    @Test
    public void testPreviousMoveRecognized1(){

        PreviousMoves previousMoves = new PreviousMoves();

        Gamelogic gamelogic = new Gamelogic();

        assertEquals(false, previousMoves.PreviousMovesRecognized(gamelogic.printGame()));

    }

    @Test
    public void testPreviousMoveRecognized2(){

        PreviousMoves previousMoves = new PreviousMoves();

        Gamelogic gamelogic = new Gamelogic();

        previousMoves.PreviousMovesRecognized(gamelogic.printGame());

        Gamelogic gamelogic2 = gamelogic;

        assertTrue(previousMoves.PreviousMovesRecognized(gamelogic2.printGame()));

    }

    //might produce a false result because the same starting point is created at random!!!
    @Test
    public void testPreviousMoveRecognized3(){

        PreviousMoves previousMoves = new PreviousMoves();

        Gamelogic gamelogic = new Gamelogic();

        previousMoves.PreviousMovesRecognized(gamelogic.printGame());

        Gamelogic gamelogic2 = new Gamelogic();

        assertFalse(previousMoves.PreviousMovesRecognized(gamelogic2.printGame()));

    }

    @Test
    public void testPreviousMoveRecognized4(){

        PreviousMoves previousMoves = new PreviousMoves();

        Gamelogic gamelogic = new Gamelogic();

        previousMoves.PreviousMovesRecognized(gamelogic.printGame());

        Gamelogic gamelogic2 = new Gamelogic();

        previousMoves.PreviousMovesRecognized(gamelogic2.printGame());

        Gamelogic gamelogic3 = gamelogic;

        assertTrue(previousMoves.PreviousMovesRecognized(gamelogic3.printGame()));

    }
}
