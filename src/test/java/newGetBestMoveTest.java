import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class newGetBestMoveTest {

    @Test
    public void newGetBestMoveTest1(){

        PreviousMoves previousMoves = new PreviousMoves();
        Gamelogic gamelogic = new Gamelogic();

        List<Tableau> tableaus = Arrays.asList(gamelogic.getTableau());
        List<Foundation> foundations = Arrays.asList(gamelogic.getFoundation());
        MoveAlgoritm move = new MoveAlgoritm(tableaus, foundations, gamelogic.waste.lookAtTop(), gamelogic.getWaste().getPileStatus());
        previousMoves.addPreviousMove(gamelogic.printGame());

        Gamelogic gamelogic2 = new Gamelogic();

        List<Tableau> tableaus2 = Arrays.asList(gamelogic2.getTableau());
        List<Foundation> foundations2 = Arrays.asList(gamelogic2.getFoundation());
        MoveAlgoritm move2 = new MoveAlgoritm(tableaus2, foundations2, gamelogic2.waste.lookAtTop(), gamelogic2.getWaste().getPileStatus());
        previousMoves.addPreviousMove(gamelogic2.printGame());

        assertNotEquals(move.getBestMove(previousMoves), move2.getBestMove(previousMoves));
    }

    @Test
    public void newGetBestMoveTest2(){ //tests if the resoult of a normal getBest move would be the same if the previous moves are checked when a dublicate is created

        PreviousMoves previousMoves = new PreviousMoves();
        Gamelogic gamelogic = new Gamelogic();

        List<Tableau> tableaus = Arrays.asList(gamelogic.getTableau());
        List<Foundation> foundations = Arrays.asList(gamelogic.getFoundation());
        MoveAlgoritm move = new MoveAlgoritm(tableaus, foundations, gamelogic.waste.lookAtTop(), gamelogic.getWaste().getPileStatus());
        previousMoves.addPreviousMove(gamelogic.printGame());

        Gamelogic gamelogic2 = gamelogic;

        List<Tableau> tableaus2 = Arrays.asList(gamelogic2.getTableau());
        List<Foundation> foundations2 = Arrays.asList(gamelogic2.getFoundation());
        MoveAlgoritm move2 = new MoveAlgoritm(tableaus2, foundations2, gamelogic2.waste.lookAtTop(), gamelogic2.getWaste().getPileStatus());
        previousMoves.addPreviousMove(gamelogic2.printGame());

        assertEquals(move.getBestMove(previousMoves), move2.getBestMove(previousMoves));

    }

    @Test
    public void newGetBestMoveTest3(){ //tests if the resoult of a normal getBest move would be the same if the previous moves are checked when a dublicate is created

        PreviousMoves previousMoves = new PreviousMoves();
        Gamelogic gamelogic = new Gamelogic();

        List<Tableau> tableaus = Arrays.asList(gamelogic.getTableau());
        List<Foundation> foundations = Arrays.asList(gamelogic.getFoundation());
        MoveAlgoritm move = new MoveAlgoritm(tableaus, foundations, gamelogic.waste.lookAtTop(), gamelogic.getWaste().getPileStatus());
        previousMoves.addPreviousMove(gamelogic.printGame());

        Gamelogic gamelogic2 = gamelogic;

        List<Tableau> tableaus2 = Arrays.asList(gamelogic2.getTableau());
        List<Foundation> foundations2 = Arrays.asList(gamelogic2.getFoundation());
        MoveAlgoritm move2 = new MoveAlgoritm(tableaus2, foundations2, gamelogic2.waste.lookAtTop(), gamelogic2.getWaste().getPileStatus());
        previousMoves.addPreviousMove(gamelogic2.printGame());

        assertNotEquals(move.getBestMove(), move2.getBestMove(previousMoves));

    }

    @Test
    public void newGetBestMoveTest4(){

        PreviousMoves previousMoves = new PreviousMoves();

        Gamelogic gamelogic = new Gamelogic();
        previousMoves.addPreviousMove(gamelogic.printGame());

        Gamelogic gamelogic2 = gamelogic;
        previousMoves.addPreviousMove(gamelogic2.printGame());

        Gamelogic gamelogic3 = gamelogic;
        previousMoves.addPreviousMove(gamelogic3.printGame());

        assertEquals(2, previousMoves.timeslastMoveIsRecognized());
    }
}
