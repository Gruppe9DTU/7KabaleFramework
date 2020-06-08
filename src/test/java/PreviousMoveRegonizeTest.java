import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PreviousMoveRegonizeTest {

    @Test
    public void testPreviousMoveRecognized1(){

        PreviousMoves previousMoves = new PreviousMoves();

        Gamelogic gamelogic = new Gamelogic();
        previousMoves.addPreviousMove(gamelogic.printGame());

        assertEquals(0, previousMoves.timeslastMoveIsRecognized());

    }

    @Test
    public void testPreviousMoveRecognized2(){

        PreviousMoves previousMoves = new PreviousMoves();

        Gamelogic gamelogic = new Gamelogic();

        previousMoves.addPreviousMove(gamelogic.printGame());

        Gamelogic gamelogic2 = gamelogic;
        previousMoves.addPreviousMove(gamelogic2.printGame());

        assertEquals(1, previousMoves.timeslastMoveIsRecognized());

    }

    //might produce a false result because the same starting point is created at random!!!
    @Test
    public void testPreviousMoveRecognized3(){

        PreviousMoves previousMoves = new PreviousMoves();

        Gamelogic gamelogic = new Gamelogic();

        previousMoves.addPreviousMove(gamelogic.printGame());

        Gamelogic gamelogic2 = new Gamelogic();
        previousMoves.addPreviousMove(gamelogic2.printGame());

        assertEquals(0, previousMoves.timeslastMoveIsRecognized());

    }

    @Test
    public void testPreviousMoveRecognized4(){

        PreviousMoves previousMoves = new PreviousMoves();

        Gamelogic gamelogic = new Gamelogic();

        previousMoves.addPreviousMove(gamelogic.printGame());

        Gamelogic gamelogic2 = new Gamelogic();

        previousMoves.addPreviousMove(gamelogic2.printGame());

        Gamelogic gamelogic3 = gamelogic;
        previousMoves.addPreviousMove(gamelogic3.printGame());

        assertEquals(1, previousMoves.timeslastMoveIsRecognized());

    }

    @Test
    public void testPreviousMoveRecognized5(){

        PreviousMoves previousMoves = new PreviousMoves();

        Gamelogic gamelogic = new Gamelogic();
        previousMoves.addPreviousMove(gamelogic.printGame());

        Gamelogic gamelogic2 = new Gamelogic();
        previousMoves.addPreviousMove(gamelogic2.printGame());

        Gamelogic gamelogic3 = gamelogic;
        previousMoves.addPreviousMove(gamelogic3.printGame());

        Gamelogic gamelogic4 = gamelogic3;
        previousMoves.addPreviousMove(gamelogic4.printGame());

        assertEquals(2, previousMoves.timeslastMoveIsRecognized());

    }
}
