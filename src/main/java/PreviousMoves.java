import java.util.ArrayList;
import java.util.List;

public class PreviousMoves {

    private List<MoveAlgoritm> previousMoves = new ArrayList<MoveAlgoritm>();

    public PreviousMoves(){}

    //TODO: split up into add and regonize?
    public boolean PreviousMovesRecognized(MoveAlgoritm previousMove) {

        boolean previousMoveFound = false;

        if (!previousMoves.isEmpty()) {
            for (MoveAlgoritm previousMovess : previousMoves) {
                if (previousMove.equals(previousMovess)){
                    previousMoveFound = true;
                }
            }

            if (!previousMoveFound){
                previousMoves.add(previousMove);
                previousMoveFound = false;

            }

        } else {
            previousMoves.add(previousMove);
            previousMoveFound = false;
        }

        return previousMoveFound;
    }
}
