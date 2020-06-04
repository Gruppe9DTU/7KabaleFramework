import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PreviousMoveRegonizeTest {

    @Test
    public void testPreviousMoveRecognized1(){

        PreviousMoves previousMoves = new PreviousMoves();

        Gamelogic gamelogic = new Gamelogic();
        List<Tableau> tableaus = Arrays.asList(gamelogic.getTableau());
        List<Foundation> foundations = Arrays.asList(gamelogic.getFoundation());
        MoveAlgoritm move = new MoveAlgoritm(tableaus, foundations, gamelogic.waste.lookAtTop(), gamelogic.getWaste().getPileStatus());

        assertEquals(false, previousMoves.PreviousMovesRecognized(move));

    }
}
