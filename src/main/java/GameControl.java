import java.util.*;

public class GameControl {
    private SolitaireLogic logic;
    private MoveAlgorithm ma;
    private PreviousStatesContainer prevStates;

    public GameControl(){
        logic = new SolitaireLogic();
        ma = new MoveAlgorithm(logic);
        this.prevStates = PreviousStatesContainer.getInstance();
    }

    public void playGame() {
        while (!logic.getIsWon()) {
            makeMove();
            logic.updateIsWon();
        }
    }

    public void makeMove() {
        ma = new MoveAlgorithm(logic);
        String moveSuggestion = ma.getBestMove(prevStates.getLatestSolutionToState(logic.getGameState()));
        prevStates.addPreviousMove(new PreviousState(logic.getGameState(), ma.getMoveChosen()));
        System.out.println(moveSuggestion);
        Scanner input = new Scanner(System.in);
        String firstChoice;
        Card chosenCard = null;
        List<Card> chosenCards = new ArrayList();
        int fromNo = 1000;
        boolean isMoving;
        while(true) {
            isMoving = false;
            System.out.println("What type of pile do you want to move from? (Tableau, Foundation, Waste)");
            firstChoice = input.nextLine();
            if (firstChoice.equals("w")) {
                System.out.println("Do you want to Reveal or Take?");
                String wasteChoice = input.nextLine();
                if(wasteChoice.equals("r")) {
                    logic.revealFromWaste();
                }
                else if (wasteChoice.equals("t")) {
                    chosenCard = logic.takeFromWaste();
                    chosenCards.add(chosenCard);
                    if(chosenCard.equals(null)) {
                        System.out.println("Waste is empty");
                    }
                    else {
                        isMoving = true;
                    }
                }
                break;
            } else if (firstChoice.equals("t")) {
                System.out.println("Please choose the tableau number(1-7)");
                isMoving = true;
                fromNo = readIntFromInput(1,7);
                System.out.println("Please choose how many cards to take(1-" +
                        logic.getVisibleCardsTablaeu(fromNo-1) + ")");
                int cardNo = readIntFromInput(1, logic.getVisibleCardsTablaeu(fromNo-1));
                chosenCards = logic.takeFromTableau(fromNo-1, cardNo-1);
                break;
            } else if (firstChoice.equals("f")) {
                System.out.println("Please choose the foundation number(1-4)");
                isMoving = true;
                fromNo = readIntFromInput(1, 4);
                chosenCard = logic.takeFromFoundation(fromNo-1);
                chosenCards.add(chosenCard);
                break;
            } else {
                System.out.println("please try again");
            }
        }
        while(isMoving) {
            System.out.println("What type of pile do you want to move to? (Tableau, Foundation)");
            String secondChoice = input.nextLine();
            if (secondChoice.equals("t")) {
                System.out.println("Please choose the tableau number(1-7)");
                int choiceNo = readIntFromInput(1, 7);
                Collections.reverse(chosenCards);
                logic.addToTableau(chosenCards, choiceNo-1, fromNo - 1);
                isMoving = false;
            } else if (secondChoice.equals("f")) {
                System.out.println("Please choose the foundation number(1-4)");
                int choiceNo = readIntFromInput(1, 4);
                logic.addToFoundation(chosenCards.get(0), choiceNo-1);
                if(firstChoice.equals("t")) {
                    logic.removeFromTableau(chosenCards, fromNo-1);
                }
                isMoving = false;
            } else {
                System.out.println("please try again");
            }
        }
    }

    public int readIntFromInput(int min, int max) {
        Scanner input = new Scanner(System.in);
        while (true) {
            try {
                int choiceNo = input.nextInt();
                if(choiceNo < min || choiceNo > max) {
                    System.out.println("Input out of range, please write a number between " + min + " and "
                    + max);
                }
                else{
                    return choiceNo;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please only input integers");
                input.next();
                continue;
            }
        }
    }
}
