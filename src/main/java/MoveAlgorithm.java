import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class to calculate the best possible move for a given game state and return the suggestion to the user
 */
public class MoveAlgorithm {
    private List<Tableau> tableaus;
    private List<Foundation> foundations;
    private Card wasteCard;
    private boolean wastePile;
    private int moveChosen;

    /**
     * Constructor for the move Algorithm
     *
     * @param game  The current game
     */
    public MoveAlgorithm(SolitaireLogic game) {
        this.tableaus = Arrays.asList(game.getTableau());       //Get list of tableau
        this.foundations = Arrays.asList(game.getFoundation()); //Get list of foundations
        this.wasteCard = game.getWaste().lookAtTop();               //Get the top card of waste (1-card rule)
        this.wastePile = game.getWaste().isWastePilePresent();       //Get if there is a wastepile to draw from or not
        Collections.sort(tableaus,Tableau.HiddenCardsCompare);  //Sort the cards after how many hidden cards is in the tableau
        System.out.println(game.getGameState());
    }

    /**
     * Method to get the best move for the user
     *
     * @param preState  The current state of the game
     * @return          Instructions for the user
     */
    public String getBestMove(PreviousState preState) {
        int latestMove;
        String bestMove;

        //Gets latest move done for position
        if(preState == null) latestMove = 0;
        else latestMove = preState.getMove();

        bestMove = moveChooser(latestMove);

        return bestMove;
    }

    /**
     * Chooses the move to be instructed to the user, by selecting the first viable move possible
     */
    private String moveChooser(int latestMove) {
        String bestMove;

        switch (++latestMove) { //Skips previous move, goes to first if none were made before
            case 1:
                bestMove = checkWin();
                if (!bestMove.equals("")){
                    moveChosen = 1;
                    break;
                }

            case 2:
                bestMove = autoFinish();
                if (!bestMove.equals("")){
                    moveChosen = 2;
                    break;
                }

            case 3:
                bestMove = checkAce();
                if (!bestMove.equals("")) {
                    moveChosen = 3;
                    break;
                }

            case 4:
                bestMove = kingCheck();
                if (!bestMove.equals("")) {
                    moveChosen = 4;
                    break;
                }

            case 5:
                bestMove = revealHiddenCard();
                if (!bestMove.equals("")) {
                    moveChosen = 5;
                    break;
                }

            case 6:
                bestMove = moveTableau();
                if (!bestMove.equals("")) {
                    moveChosen = 6;
                    break;
                }

            case 7:
                bestMove = moveToFoundation();
                if (!bestMove.equals("")) {
                    moveChosen = 7;
                    break;
                }

            case 8:
                bestMove = typeStreak();
                if (!bestMove.equals("")) {
                    moveChosen = 8;
                    break;
                }

            case 9:
                bestMove = foundationToTableau();
                if (!bestMove.equals("")) {
                    moveChosen = 9;
                    break;
                }

            case 10:
                bestMove = revealCardFromWaste();
                if (!bestMove.equals("")) {
                    moveChosen = 10;
                    break;
                }

            default:
                if(latestMove == 1) bestMove = "Intet træk blev fundet for nuværende spil";
                else bestMove = "Der kunne ikke findes noget nyt træk for denne position af spillet";
        }
        return bestMove;
    }

    /**
     * Checks if foundations are complete
     *
     * @return  Instructions to player
     */
    public String checkWin(){
        for(Foundation foundation : foundations){
            if (!foundation.isComplete()){
                return  "";
            }
        }
        return "A king is present in each of the foundations thus the game should be done";
    }

    /**
     * Controls if it's possible for the player to just add cards to foundation to win, ergo there are no hidden cards left.
     *
     * @return  Instructions to player
     */
    public String autoFinish(){
        if (!wastePile) {
            for (Tableau tableau : tableaus) {
                if (tableau.countHiddenCards() != 0) {
                    return "";
                }
            }
            return "All cards should be present and game should be able to be completed";
        }
        return "";
    }

    /**
     * If there is an ace move it to the corresponding foundation
     *
     * @return Instructions to Player
     */
    public String checkAce() {


        for (Tableau tableau : tableaus) {
            List<Card> visibleCards = tableau.getVisibleCards();

            //Check if first visible card in tableau is es
            if(visibleCards.size() > 0) {
                Card card = visibleCards.get(visibleCards.size() - 1);

                if (card.getValue() == 1) {
                    return "Ryk " + card.toString() + " til Foundation";
                }
            }
        }
        if (wasteCard != null && wasteCard.getValue() == 1) return "Ryk " + wasteCard.toString() + " til Foundation";
        return "";
    }

    /**
     * If there is at minimum one King and one empty space, finds best suitable King. Best suitable King is found from which one frees the highest amount of cards.
     *
     * @return Instructions to Player
     */
    public String kingCheck() {
        //Copy Tableaus and sort
        List<Card> kingsAvailable = new ArrayList<>();
        int emptySpaces = 0;

        //Find available kings and check for empty spaces
        for (Tableau tableau : tableaus) {
            if (tableau.isEmpty()) {
                emptySpaces++;
            } else if (tableau.getVisibleCards().size() > 0) {//Check if first card is king
                Card card = tableau.getVisibleCards().get(0);

                if (card.getValue() == 13 && tableau.countHiddenCards() != 0) {
                    kingsAvailable.add(card);
                }
            }
        }
        if(wasteCard != null && wasteCard.getValue() == 13) kingsAvailable.add(wasteCard);

        if (kingsAvailable.size() > 1 && emptySpaces > 0) {
            boolean bestKingFound = false;
            int currSearchValue = 13; //Start by searching for best king to move

            int redKingScore = 0, blackKingScore = 0, currMostFreedCards;

            while (!bestKingFound && currSearchValue > 0) {
                //Reset scores for round
                redKingScore = 0; blackKingScore = 0; currMostFreedCards = 0;

                for (Card currKing : kingsAvailable) {
                    //Looking through for X value card
                    for (Tableau tab : tableaus) {

                        //There are cards to check
                        if (tab.getVisibleCards().size() > 0) {
                            Card backCard = tab.getVisibleCards().get(0); //Take card from the back of the stack

                            //Is the card we're looking for
                            if (backCard.getValue() == currSearchValue && tab.countHiddenCards() > currMostFreedCards) {

                                if (isCompatibleToKingsStack(currKing, backCard)) { //Check if card matches kings stack
                                    if (currKing.getSuit() % 2 == 0) {
                                        redKingScore = 1; //Set score to 1-0 for red
                                        blackKingScore = 0;
                                    } else {
                                        blackKingScore = 1; //Set score to 1-0 for black
                                        redKingScore = 0;
                                    }
                                    currMostFreedCards = tab.countHiddenCards(); //Set new highscore
                                }
                                bestKingFound = true; //Only one answer is found

                            } else if (backCard.getValue() == currSearchValue && currMostFreedCards == tab.countHiddenCards()) {
                                if (isCompatibleToKingsStack(currKing, backCard)) { //Check if card matches kings stack

                                    if(currKing.getSuit() % 2 == 0) redKingScore++; else blackKingScore++; //Add score
                                }
                                bestKingFound = (redKingScore + blackKingScore == 1); //Multiple answers are found
                            }
                        }
                    }
                }
                if (!bestKingFound) currSearchValue--; //Go down a value in case we need to continue search
            }

            String bestKing = null;
            for (Card king : kingsAvailable) {
                if (redKingScore > blackKingScore && king.getSuit() % 2 == 0) { //If red king is best
                    bestKing = king.toString();
                    break;
                } else if (blackKingScore > redKingScore && king.getSuit() % 2 == 1) { //If black king is best
                    bestKing = king.toString();
                    break;
                } else if (redKingScore == blackKingScore) { //If it's either or
                    bestKing = "any king";
                    break;
                }
            }
            return "Move " + bestKing + " to an empty space";
        } else if (kingsAvailable.size() == 1 && emptySpaces > 0) { //Only one king found
            return "Move " + kingsAvailable.get(0).toString() + " to an empty space";
        }
        return "";
    }

    /**
     * Checks if card can be part of the stack of a king
     *
     * @param currKing King which stacks we compare
     * @param backCard Card to check if compatible
     * @return True if compatible, else false
     */
    private boolean isCompatibleToKingsStack(Card currKing, Card backCard) {
        return backCard.getValue() % 2 == 0 && backCard.getSuit() % 2 != currKing.getSuit() % 2
                || backCard.getValue() % 2 == 1 && backCard.getSuit() % 2 == currKing.getSuit() % 2;
    }

    /**
     * Checks if there is a tableau where the player can turn over a hidden card
     *
     * @return Instruction to player
     */
    public String revealHiddenCard() {
        for (Tableau tableau : tableaus) {
            if (tableau.getVisibleCards() == null || tableau.getVisibleCards().size() == 0
                    && tableau.countHiddenCards() > 0) {
                return "Turn over a card from the tableau with the highest amount of hidden cards";
            }
        }
        return "";
    }

    /**
     * Control if taking a card from foundation to tableau opens up other interactions
     *
     * @return Instructions to player
     */
    public String foundationToTableau() {
        for (Foundation foundation : foundations) {
            if (foundation.countCards() > 0) {  //If there is a card in the foundation
                Card foundationCard = foundation.peekCard(); //Set current possible card

                for (Tableau tableau : tableaus) {
                    List<Card> tableauCards = tableau.getVisibleCards();
                    //Check if possible to place card
                    if (tableauCards.size() != 0 &&
                            tableauCards.get(tableauCards.size() - 1).getValue() - 1 == foundationCard.getValue()
                            && tableauCards.get(tableauCards.size() - 1).getSuit() % 2 != foundationCard.getSuit() % 2) { //Check if possible to move card from foundation to tableau
                        //Check if it opens up possibilities
                        //First check tableaus
                        for (Tableau otherTableau : tableaus) {
                            if (tableau != otherTableau) {
                                for (Card card : otherTableau.getVisibleCards()) {
                                    if (card.getValue() == foundationCard.getValue() - 1 && card.getSuit() % 2 != foundationCard.getSuit() % 2) {
                                        return "Ryk " + foundationCard.toString() + " fra grundbunken ned på rækken med " + tableauCards.get(tableauCards.size() - 1).toString();
                                    }
                                }
                            }
                        }
                        //Then check waste
                        if (wasteCard != null && wasteCard.getValue() == foundationCard.getValue() - 1 && wasteCard.getSuit() % 2 != foundationCard.getSuit() % 2) {
                            return "Ryk " + foundationCard.toString() + " fra grundbunken ned på rækken med " + tableauCards.get(tableauCards.size() - 1).toString();
                        }
                    }
                }
            }
        }
        return "";
    }

    /**
     * Checks if cards from tableau, other than Aces, can be moved to foundation
     *
     * @return Instructions for player
     */
    public String moveToFoundation() {
        //Check if possible to move from tableau
        for (Tableau tableau : tableaus) {
            if (tableau.getVisibleCards().size() != 0) {
                Card card = tableau.getTopCard();

                for (Foundation foundation : foundations) {

                    //check first if card can be moved to foundation
                    if (foundation.countCards() > 0 &&
                            card.getValue() == foundation.peekCard().getValue() + 1 &&
                            card.getSuit() == foundation.peekCard().getSuit()) {

                        //If creating empty space, controls King is there to replace or next card in foundation is able to be put up aswell
                        if (tableau.getVisibleCards().size() - 1 != 0 || tableau.countHiddenCards() != 0 || //Is card left behind
                                checkForMoveableCardFromValue(13) || //Is there a king to take the space
                                checkForMoveableCardFromSuitAndValue(card.getSuit(), card.getValue() + 1)) { //Is the card needed for another card
                            return "Move " + card.toString() + " to it's respective foundation";
                        }
                    }
                }
            }
        }
        //Check if possible to move from waste
        if (wasteCard != null) {
            for (Foundation foundation : foundations) {
                if (foundation.countCards() > 0 &&
                        wasteCard.getValue() == foundation.peekCard().getValue() + 1 &&
                        wasteCard.getSuit() == foundation.peekCard().getSuit()) {
                    return "Move " + wasteCard.toString() + " to it's respective foundation";
                }
            }
        }
        return "";
    }

    /**
     * Searches tableaus for moveable card of given value
     *
     * @param value Value to search for
     * @return True if card has been found
     */
    private boolean checkForMoveableCardFromValue(int value) {
        boolean result = false;
        for (Tableau tableau : tableaus) {
            result = tableau.searchForMoveableCardByValue(value) && !(value == 13 && tableau.countHiddenCards() == 0); //True if card found and it isn't a King on an empty space
            if (result) break;
        }
        return result || wasteCard != null && (wasteCard.getValue() == value); //returns true if found in tableau or in waste
    }

    /**
     * Searches tableaus for moveable card of given suit and value
     *
     * @param suit  Suit to search for
     * @param value Value to search for
     * @return True if card has been found
     */
    private boolean checkForMoveableCardFromSuitAndValue(int suit, int value) {
        boolean result = false;
        for (Tableau tableau : tableaus) {
            result = tableau.searchForMoveableCardBySuitAndValue(suit, value);
            if (result) break;
        }
        return result || wasteCard != null && (wasteCard.getSuit() == suit && wasteCard.getValue() == value); //returns true if found in tableau or in waste
    }

    /**
     * Moves cards in tableaus to another tableaus to reveal hidden card or create empty space
     *
     * @return Instructions for player
     */
    public String moveTableau() {
        List<Card> cards, cards2;

        for (Tableau tableau : tableaus) {
            cards = tableau.getVisibleCards();
            for (Tableau tableau2 : tableaus) {
                cards2 = tableau2.getVisibleCards();

                //Hvis en af bunkerne er tomme er der ingen grund til at sammenligne dem
                if (cards != cards2 && cards.size() > 0 && cards2.size() > 0) {
                    //Hvis der er mere end ét kort tilstæde i byggestablen og det nederste kort passer på det øverste kort i en anden byggestabel, ryk alle de synlige kort fra byggestablen over til den anden byggestabel
                    if (cards.get(0).getValue() == cards2.get(cards2.size() - 1).getValue() - 1
                            && cards.get(0).getSuit() % 2 != cards2.get(cards2.size() - 1).getSuit() % 2) {
                        return "Tag alle de synlige kort fra byggestablen med det nederste kort " + cards.get(0) + " og placer dem på " + cards2.get(cards2.size() - 1).toString();
                    }
                }
            }

//            //hvis waste passer så lig den på //TODO Control that this works, don't think algorithms are controlling waste to tableau at current state
            if (wasteCard != null && cards.size() > 0 && cards.get(cards.size() - 1).getValue() - 1 == wasteCard.getValue() && cards.get(cards.size() - 1).getSuit() % 2 != wasteCard.getSuit() % 2) {
                return "Tag " + wasteCard.toString() + " og placer kortet på " + cards.get(cards.size() - 1).toString();
            }
        }
        return "";
    }

    /**
     * Moves top card of tableau or waste to another tableau if the suits match.
     * For example if you can move a 4 of hearts to a 5 of spades and 5 of clubs, prioritize the one that has a 6 of hearts in the same tableau.
     *
     * @return Instructions for player
     */
    //Hvis muligt sørg for at “typerne” passer. F.eks. hvis du kan rykke en hjerter 4 til to forskellige 5’er så prioriter den som har en hjerter 6
    public String typeStreak() {
        List<Card> cards, cards2;
        for (Tableau tableau : tableaus) {
            cards = tableau.getVisibleCards();

            for (Tableau tableau2 : tableaus) {
                cards2 = tableau2.getVisibleCards();

                //Hvis en af bunkerne er tomme er der ingen grund til at sammenligne dem
                if (cards.size() - 1 >= 0 && cards2.size() - 2 >= 0) {

                    //Hvis øverste kort i tableu passer med anden tableus øverste kort lig den på hvis "typerne" passer ellers vent
                    if (cards.get(cards.size() - 1).getValue() == cards2.get(cards2.size() - 1).getValue() - 1 && cards.get(cards.size() - 1).getSuit() % 2 != cards2.get(cards2.size() - 1).getSuit() % 2) {
                        if (cards2.size() - 2 >= 0 && cards.get(cards.size() - 1).getSuit() == cards2.get(cards2.size() - 2).getSuit()) {
                            return "Tag " + cards.get(cards.size() - 1) + " og placer kortet på " + cards2.get(cards2.size() - 1).toString();
                        }
                    }
                }
            }

            //hvis waste passer så lig den på
            if (wasteCard != null && cards.size() - 1 >= 0 && cards.get(cards.size() - 1).getValue() - 1 == wasteCard.getValue() && cards.get(cards.size() - 1).getSuit() % 2 != wasteCard.getSuit() % 2) {
                if (cards.size() - 2 >= 0 && wasteCard.getSuit() == cards.get(cards.size() - 2).getSuit()) {
                    return "Tag " + wasteCard.toString() + " og placer kortet på " + cards.get(cards.size() - 1).toString();
                }
            }
        }

        return "";
    }

    /**
     * Translator to tell if possible to draw card from the waste pile
     *
     * @return Instructions to player
     */
    public String revealCardFromWaste() { return wastePile ? "Vend et kort fra grundbunken" : ""; }

    /**
     * Getter for move chosen
     *
     * @return  Priority of latest move chosen by the moveChooser method
     */
    public int getMoveChosen() {
        return moveChosen;
    }
}