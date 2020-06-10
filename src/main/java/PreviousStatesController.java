import java.util.ArrayList;
import java.util.List;

public class PreviousStatesController {

    private List<PreviousState> previousStates = new ArrayList<PreviousState>();

    public PreviousStatesController(){}

    public void addPreviousMove(PreviousState preState) {
        previousStates.add(preState);
    }

    /**
     * Compares current state of game to ealier game states
     *
     * @param currState String of current state of game
     * @return          Latest solution for that state
     */
    public PreviousState getLatestSolutionToState(String currState) {
        PreviousState state = null;

        for (PreviousState preState : previousStates) {
            if(currState.equals(preState.getState())){
                state = preState;
            }
        }
        return state;
    }
}