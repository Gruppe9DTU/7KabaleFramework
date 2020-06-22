import java.util.Scanner;

public class GameControl {
    private SolitarieLogic logic;

    public GameControl(){
        logic = new SolitarieLogic();
        System.out.println(logic.getGameState());
    }

    public void makeMove() {
        Scanner input = new Scanner(System.in);
        String choice;
        Card chosenCard = null;
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
                    if(chosenCard.equals(null)) {
                        System.out.println("Waste is empty");
                    }
                    else {
                        isMoving = true;
                    }
                }
                break;
            } else if (choice.equals("t")) {
                System.out.println("Please choose the tableau number");
                isMoving = true;
                int choiceNo = input.nextInt();
                chosenCard = logic.takeFromTableau(choiceNo);
                break;
            } else if (choice.equals("f")) {
                System.out.println("Please choose the foundation number");
                isMoving = true;
                int choiceNo = input.nextInt();
                chosenCard = logic.takeFromFoundation(choiceNo);
                break;
            } else {
                System.out.println("please try again");
            }
        }
        while(isMoving) {
            System.out.println("What type of pile do you want to move to? (Tableau, Foundation)");
            choice = input.nextLine();
            if (choice.equals("t")) {
                System.out.println("Please choose the tableau number");
                int choiceNo = input.nextInt();
                logic.addToTableau(chosenCard, choiceNo);
                isMoving = false;
            } else if (choice.equals("f")) {
                System.out.println("Please choose the foundatin number");
                int choiceNo = input.nextInt();
                logic.addToFoundation(chosenCard, choiceNo);
                isMoving = false;
            } else {
                System.out.println("please try again");
            }
        }
        System.out.println(logic.getGameState());
    }
}
