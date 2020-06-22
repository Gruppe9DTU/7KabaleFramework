public class SolitaireController {
    private PreviousStatesContainer prevStates;

    public SolitaireController() {
        this.prevStates = PreviousStatesContainer.getInstance();
    }

    public String takeMove() {
        //Setup
        SolitarieLogic game = new SolitarieLogic();
        //Find move suggestion
        MoveAlgorithm moveAlgo = new MoveAlgorithm(game);
        String moveSuggestion = moveAlgo.getBestMove(prevStates.getLatestSolutionToState(game.getGameState()));
        //Save and return suggestion
        prevStates.addPreviousMove(new PreviousState(game.getGameState(), moveAlgo.getMoveChosen()));
        return moveSuggestion;
    }

    /**
     * Resets the PreviousStateController, so no knowledge of previous moves should be remembered
     */
    public void resetMemory() {
        prevStates.resetMemory();
    }
}