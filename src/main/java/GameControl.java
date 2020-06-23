import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
        String choice;
        Card chosenCard = null;
        List<Card> chosenCards = new ArrayList();
        boolean isMoving;
        while(true) {
            isMoving = false;
            System.out.println("What type of pile do you want to move from? (Tableau, Foundation, Waste)");
            choice = input.nextLine();
            if (choice.equals("w")) {
                System.out.println("Do you want to Reveal or Take?");
                choice = input.nextLine();
                if(choice.equals("r")) {
                    logic.revealFromWaste();
                }
                else if (choice.equals("t")) {
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
            } else if (choice.equals("t")) {
                System.out.println("Please choose the tableau number(1-7)");
                isMoving = true;
                int choiceNo = input.nextInt();
                System.out.println("Please choose how many cards to take(1-" +
                        logic.getVisibleCardsTablaeu(choiceNo-1) + ")");
                int cardNo = input.nextInt();
                chosenCards = logic.takeFromTableau(choiceNo-1, cardNo-1);
                break;
            } else if (choice.equals("f")) {
                System.out.println("Please choose the foundation number(1-4)");
                isMoving = true;
                int choiceNo = input.nextInt();
                chosenCard = logic.takeFromFoundation(choiceNo-1);
                chosenCards.add(chosenCard);
                break;
            } else {
                System.out.println("please try again");
            }
        }
        while(isMoving) {
            System.out.println("What type of pile do you want to move to? (Tableau, Foundation)");
            choice = input.nextLine();
            if (choice.equals("t")) {
                System.out.println("Please choose the tableau number(1-7)");
                int choiceNo = input.nextInt();
                Collections.reverse(chosenCards);
                logic.addToTableau(chosenCards, choiceNo-1);
                isMoving = false;
            } else if (choice.equals("f")) {
                System.out.println("Please choose the foundation number(1-4)");
                int choiceNo = input.nextInt();
                logic.addToFoundation(chosenCards.get(0), choiceNo-1);
                isMoving = false;
            } else {
                System.out.println("please try again");
            }
        }
    }
}
