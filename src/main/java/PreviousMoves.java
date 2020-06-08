import java.util.ArrayList;
import java.util.List;

public class PreviousMoves {

    private List<String> previousMoves = new ArrayList<String>();

    public PreviousMoves(){}

    //TODO: split up into add and regonize?
    public boolean PreviousMovesRecognized(String previousMove) {

        boolean previousMoveFound = false;

        if (!previousMoves.isEmpty()) {

            for (String previousMovess : previousMoves) {

                if (previousMove.equals(previousMovess)){
                    previousMoveFound = true;
                }
            }

            if (!previousMoveFound){
                previousMoves.add(previousMove);
            }

        } else {
            previousMoves.add(previousMove);
            previousMoveFound = false;
        }

        return previousMoveFound;
    }

}
