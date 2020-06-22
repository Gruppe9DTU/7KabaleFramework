import java.util.ArrayList;
import java.util.List;

/**
 * Container for instances of PreviousState class
 */
public class PreviousStatesContainer {
    private List<PreviousState> previousStates = new ArrayList<PreviousState>();
    private static PreviousStatesContainer singleton = null;

    private PreviousStatesContainer() {
        //Empty singleton constructor
    }

    /**
     * Singleton method, use this to get the current instance of this class
     *
     * @return  Current instance of class
     */
    public static PreviousStatesContainer getInstance() {
        if(singleton == null) singleton = new PreviousStatesContainer();
        return singleton;
    }

    /**
     * Adds an instance of PreviousState to list, to act as a memory of the game being played
     * @param preState  Class with saved game state and latest move proposed for that state
     */
    public void addPreviousMove(PreviousState preState) {
        previousStates.add(preState);
    }

    /**
     * Resets the memory by creating a fresh ArrayList
     */
    public void resetMemory() {
        this.previousStates = new ArrayList<>();
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
            if(currState.equals(preState.getState())){ state = preState; }
        }
        return state;
    }
}