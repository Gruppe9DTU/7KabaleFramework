/**
 * Class to act as a save state, by saving a picture (String) of a previous state and what move was made at that state
 */
public class PreviousState {
    private String state;   //Saved image of game state
    private int move;       //Move made on saved game state

    //TODO New PreviousStates not created and saved?

    /**
     * Constructor for a saved state
     *
     * @param state Game state
     * @param move  Integer reference to latest move provided by MoveAlgorithm
     */
    public PreviousState(String state, int move) {
        this.state = state;
        this.move = move;
    }

    /**
     * Getters and Setters for PreviousState class
     */
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public int getMove() { return move; }
    public void setMove(int move) { this.move = move; }
}