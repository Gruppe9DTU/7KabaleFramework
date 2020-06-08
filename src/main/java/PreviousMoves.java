import java.util.ArrayList;
import java.util.List;

public class PreviousMoves {

    private List<String> previousMoves = new ArrayList<String>();
    private int previousMovesFound = 0;

    public PreviousMoves(){}

    public void addPreviousMove(String gameImage) {

        previousMovesFound = 0; //TODO Consider if possible to move into if statement

        if (!previousMoves.isEmpty()) {
            //TODO Consider: I'm afraid of the length, since I don't know how many times a move can be made throughout the game.
            for (String previousMove : previousMoves) {

                if (gameImage.equals(previousMove)){
                    previousMovesFound++;
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
