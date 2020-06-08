import java.util.ArrayList;
import java.util.List;

public class PreviousMoves {

    private List<String> previousMoves = new ArrayList<String>();
    private int previousMovesFound;

    public PreviousMoves(){}

    public void PreviousMovesRecognized(String gamelogicPrintGame) {

        previousMovesFound = 0;

        if (!previousMoves.isEmpty()) {

            for (String previousMovess : previousMoves) {

                if (gamelogicPrintGame.equals(previousMovess)){
                    previousMovesFound++;
                }
            }

            previousMoves.add(gamelogicPrintGame);


        } else {
            previousMoves.add(gamelogicPrintGame);
        }
    }

    public int getPreviousMovesFound(){
        return previousMovesFound;
    }

}
