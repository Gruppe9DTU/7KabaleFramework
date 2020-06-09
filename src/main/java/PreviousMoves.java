import java.util.ArrayList;
import java.util.List;

public class PreviousMoves {

    private List<String> previousMoves = new ArrayList<String>();
    private int previousMovesFound = 0;

    public PreviousMoves(){}

    public void addPreviousMove(String gameImage) {

        if (!previousMoves.isEmpty()) {

            previousMovesFound = 0;

            for (String previousMove : previousMoves) {

                if (gameImage.equals(previousMove)){
                    previousMovesFound++;
                } else if (previousMovesFound > 7){ //kan alligevel ikke skippe over 7 gange i denne verison
                    break;
                }
            }

            previousMoves.add(gameImage);


        } else {
            previousMoves.add(gameImage);
        }
    }

    public int timeslastMoveIsRecognized(){
        return previousMovesFound;
    }

}
