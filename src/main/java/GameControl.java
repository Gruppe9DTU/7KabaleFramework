import java.util.*;

public class GameControl {
    private SolitaireLogic logic;
    private MoveAlgorithm ma;
    private PreviousStatesContainer prevStates;

    public GameControl(){
        logic = new SolitaireLogic();
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
        while(true) {
            System.out.println("Hvilken bunke vil du tage fra? (Byggestabel, Grundbunke, bUnke)");
            firstChoice = input.nextLine();
            if (firstChoice.toLowerCase().equals("u") || firstChoice.toLowerCase().equals("bunke") || firstChoice.toLowerCase().equals("w")) {
                moveFromWaste();
                break;
            } else if (firstChoice.toLowerCase().equals("b") || firstChoice.toLowerCase().equals("byggestabel") || firstChoice.toLowerCase().equals("t")) {
                moveFromTableau();
                break;
            } else if (firstChoice.toLowerCase().equals("g") ||firstChoice.toLowerCase().equals("grundbunke") || firstChoice.toLowerCase().equals("f")) {
                moveFromFoundation();
                break;
            } else {
                System.out.println("Ugyldigt input, prøv venligst igen.");
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

    public void moveFromTableau() {
        Scanner input = new Scanner(System.in);
        List<Card> chosenCards;
        System.out.println("Vælg venligst hvilken byggestabel du vil tage fra (1-7)");
        int fromNo = readIntFromInput(1, 7);
        if(logic.isTableauEmpty(fromNo-1)) {
            System.out.println("Byggestablen er tom.");
            return;
        }
        int cardNo;
        if(logic.getVisibleCardsTablaeu(fromNo - 1) > 1 ) {
            System.out.println("Hvor mange kort vil du samle op (1-"
                    + logic.getVisibleCardsTablaeu(fromNo-1) + ")");
            cardNo = readIntFromInput(1, logic.getVisibleCardsTablaeu(fromNo-1));
        } else cardNo = 1;

        chosenCards = logic.takeFromTableau(fromNo-1, cardNo-1);
        System.out.println("Hvor vil du rykke kortet hen? (Byggestabel, Grundbunke)");
        String choice = input.nextLine();
        if(choice.toLowerCase().equals("b") || choice.toLowerCase().equals("byggestabel") || choice.toLowerCase().equals("t")) {
            System.out.println("Vælg venligst hvilken byggestabel du vil lægge kortet på (1-7)");
            int destNo = readIntFromInput(1, 7);
            Collections.reverse(chosenCards);
            if(logic.addToTableau(chosenCards, destNo-1)) {
                logic.removeFromTableau(chosenCards, fromNo-1);
            }
        }
        else if(choice.toLowerCase().equals("g") || choice.toLowerCase().equals("grundbunke") || choice.toLowerCase().equals("f")) {
            System.out.println("Vælg venligst hvilken grundbunke du vil lægge kortet på (1-4)");
            int destNo = readIntFromInput(1, 4);
            if(logic.addToFoundation(chosenCards.get(0), destNo-1)) {
                List<Card> cards = new ArrayList();
                cards.add(chosenCards.get(0));
                logic.removeFromTableau(cards, fromNo - 1);
            }
        }
    }

    public void moveFromFoundation() {
        Scanner input = new Scanner(System.in);
        List<Card> chosenCards = new ArrayList();
        System.out.println("Vælg venligst hvilken grundbunke du vil tage fra");
        int fromNo = readIntFromInput(1, 4);
        chosenCards.add(logic.takeFromFoundation(fromNo-1));
        System.out.println("Vælg venligst hvilken byggestabel du vil lægge kortet på");
        int destNo = readIntFromInput(1, 7);
        logic.addToTableau(chosenCards, destNo-1);
    }

    public void moveFromWaste() {
        Scanner input = new Scanner(System.in);
        List<Card> chosenCards = new ArrayList();
        System.out.println("Vil du Vende et kort, eller Tage det øverste kort?");
        String choice = input.nextLine();
        if (choice.toLowerCase().equals("v") || choice.toLowerCase().equals("vende") || choice.toLowerCase().equals("r")) {
            logic.revealFromWaste();
        }
        else if (choice.toLowerCase().equals("t") || choice.toLowerCase().equals("tage")) {
            if(logic.takeFromWaste() == null) {
                System.out.println("Der er ingen kort i bunken");
                return;
            }
            chosenCards.add(logic.takeFromWaste());
            System.out.println("Hvor vil du rykke kortet hen? (Byggestabel, Grundbunke)");
            String dest = input.nextLine();
            if(dest.toLowerCase().equals("b") || dest.toLowerCase().equals("byggestabel") || dest.toLowerCase().equals("t")) {
                System.out.println("Vælg venligst hvilken byggestabel du lægge kortet på (1-7)");
                int destNo = readIntFromInput(1, 7);
                if(logic.addToTableau(chosenCards, destNo-1)) {
                    logic.removeFromWaste();
                }
            }
            else if(dest.toLowerCase().equals("g") || dest.toLowerCase().equals("grundbunke") || dest.toLowerCase().equals("f")) {
                System.out.println("Vælg venligst den grundbunke du vil lægge kortet på (1-4)");
                int destNo = readIntFromInput(1, 7);
                if(logic.addToFoundation(chosenCards.get(0), destNo-1)) {
                    logic.removeFromWaste();
                }
            }
        }

    }
}
