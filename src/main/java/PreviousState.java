public class PreviousState {
    private String state;
    private int move;

    public PreviousState(String state, int move) {
        this.state = state;
        this.move = move;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }
}
