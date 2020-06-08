import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoveAlgoritm {

    private List<Tableau> tableaus;
    private List<Foundation> foundations;
    private Card waste;
    private boolean wastePile;

    public MoveAlgoritm(List<Tableau> tableaus, List<Foundation> foundations, Card waste, boolean wastePile) {
        this.tableaus = tableaus;
        this.foundations = foundations;
        this.waste = waste;
        this.wastePile = wastePile;
    }

    public String getBestMove() {

        Collections.sort(tableaus,Tableau.AllCardsCompare);

        String bestMove = "";

        List<String> moves = new ArrayList<String>();

        moves.add(checkEs());
        moves.add(kingCheck());
        moves.add(revealHiddenCard());
        moves.add(foundationToTableau());
        moves.add(moveToFoundation());
        moves.add(typeStreak());
        moves.add(revealCardFromWaste());
        moves.add(endGame());

        for (String move : moves) {
            if (!move.equals("")) {
                bestMove = move;
                break;
            }
        }

        return bestMove;
    }


    //Altid ryk en es til grundbunker
    public String checkEs() {

        for (Tableau tableau : tableaus) {

            //check if first visible card in stable is es
                Card card = tableau.getVisibleCards()[tableau.getVisibleCards().length - 1];

                if (card.getValue() == 1) {
                    return "Ryk " + card.toString() + " til Foundation";
                }
        }

        return "";
    }

    /**
     * If there is at minimum one King and one empty space, finds best suitable King. Best suitable King is found from which one frees the highest amount of cards.
     * @return  Instructions to Player
     */
    public String kingCheck() {
        //Copy Tableaus and sort
        List<Tableau> sortedTableaus = new ArrayList<Tableau>(tableaus);
        Collections.sort(sortedTableaus,Tableau.HiddenCardsCompare); //Sort so first found has highest amount of hidden cards
        List<Card> kingsAvailable = new ArrayList<Card>();

        int emptySpaces = 0;

        //Find available kings and check for empty spaces
        for (Tableau tableau : sortedTableaus) {
            if (tableau.isEmpty()){
                emptySpaces++;
            }

            //Check if first card is king
            // TODO: might want to make it more complex later
            if (!tableau.isEmpty()) {
                Card card = tableau.getVisibleCards()[0];

                if (card.getValue() == 13) {
                    kingsAvailable.add(card);
                }
            }
        }

        if (kingsAvailable.size() > 1 && emptySpaces > 0) {
            boolean bestKingFound = false;
            int currSearchValue = 13; //Start by searching for best king to move

            int redKingScore = 0, blackKingScore = 0, currHighscore = 0;

            while(!bestKingFound && currSearchValue > 0) {
                //Reset scores for round
                redKingScore = 0; blackKingScore = 0; currHighscore = 0;

                for(Card currKing : kingsAvailable) {
                    //Looking through for X value card
                    for(Tableau tab : sortedTableaus) {

                        //There are cards to check
                        if(tab.getVisibleCards().length > 0) {
                            Card backCard = tab.getVisibleCards()[0]; //Take card from the back of the stack

                            //Is the card we're looking for
                            if(backCard.getValue() == currSearchValue && currHighscore > tab.countHiddenCards()) {

                                //Check if suit matches king
                                if(backCard.getValue() % 2 == 0 && backCard.getSuit() % 2 != currKing.getSuit() % 2
                                        || backCard.getValue() % 2 == 1 && backCard.getSuit() % 2 == currKing.getSuit() % 2) {

                                    if(currKing.getSuit() % 2 == 0) redKingScore = 1; else blackKingScore = 1; //Set score to 1
                                    currHighscore = tab.countHiddenCards(); //Set new highscore
                                }
                                bestKingFound = true; //Only one answer is found

                            } else if(backCard.getValue() == currSearchValue && currHighscore == tab.countHiddenCards()) {
                                if(backCard.getValue() % 2 == 0 && backCard.getSuit() % 2 != currKing.getSuit() % 2
                                        || backCard.getValue() % 2 == 1 && backCard.getSuit() % 2 == currKing.getSuit() % 2) {

                                    if(currKing.getSuit() % 2 == 0) redKingScore++; else blackKingScore++; //Add score
                                }
                                bestKingFound = (redKingScore + blackKingScore == 1); //Multiple answers are found
                            }
                        }
                    }
                }
                if(!bestKingFound) currSearchValue--; //Go down a value in case we need to continue search
            }

            Card bestKing = null;
            for(Card king : kingsAvailable) {
                if(redKingScore > blackKingScore && king.getSuit() % 2 == 0) { //If red king is best
                    bestKing = king;
                    break;
                } else if(blackKingScore > redKingScore && king.getSuit() % 2 == 1) { //If black king is best
                    bestKing = king;
                    break;
                } else if(redKingScore == blackKingScore) { //If it's either or
                    bestKing = king;
                    break;
                }
            }
            return "Move " + bestKing.toString() + " to empty space";
        } else if (kingsAvailable.size() == 1 && emptySpaces > 0) {
            return "Move " + kingsAvailable.get(0).toString() + " to empty space";
        }
        return "";
    }

    private List<Tableau> sortAfterHiddenCards(){

        //TODO: make sorting algoritm
        List<Tableau> tableau = tableaus;

        return tableau;
    }

    /**
     * Checks if there is a tableau where the player can turn over a hidden card
     *
     * @return  Instruction to player
     */
    public String revealHiddenCard(){
        for(Tableau tableau : sortAfterHiddenCards()) {
            if(tableau.getVisibleCards() == null || tableau.getVisibleCards().length == 0
                    && tableau.countHiddenCards() > 0) {
                return "Turn over a card from the tableau with the highest amount of hidden cards";
            }
        }
        return "";
    }

    /**
     * Control if taking a card from foundation to tableau opens up other interactions
     *
     * @return  Instructions to player
     */
    public String foundationToTableau() {
        //TODO Should this be sorted?
        for (Foundation foundation : foundations) {
            if (foundation.countCards() > 0) {  //If there is a card in the foundation
                Card foundationCard = foundation.peekCard(); //Set current possible card

                for (Tableau tableau : tableaus) {
                    Card[] tableauCards = tableau.getVisibleCards();
                    //Check if possible to place card
                    if (tableauCards.length != 0 &&
                            tableauCards[tableauCards.length - 1].getValue() - 1 == foundationCard.getValue()
                            && tableauCards[tableauCards.length - 1].getSuit() % 2 != foundationCard.getSuit() % 2) { //Check if possible to move card from foundation to tableau
                        //Check if it opens up possibilities
                        //First check tableaus
                        for (Tableau otherTableau : tableaus) {
                            if (tableau != otherTableau) {
                                for(Card card : otherTableau.getVisibleCards()) {
                                    if(card.getValue() == foundationCard.getValue()-1 && card.getSuit() % 2 != foundationCard.getSuit() % 2) {
                                        return "Ryk " + foundationCard.toString() + " fra grundbunken ned på rækken med " + tableauCards[tableauCards.length - 1].toString();
                                    }
                                }
                            }
                        }
                        //Then check waste
                        if (waste != null && waste.getValue() == foundationCard.getValue() - 1 && waste.getSuit() % 2 != foundationCard.getSuit() % 2) {
                            return "Ryk " + foundationCard.toString() + " fra grundbunken ned på rækken med " + tableauCards[tableauCards.length - 1].toString();
                        }
                    }
                }
            }
        }
        return "";
    }

    //Checks if cards (other than Aces) can be moved to foundation

    /**
     * Checks if cards from tableau, other than Aces, can be moved to foundation
     *
     * @return  Instructions for player
     */
    public String moveToFoundation() {
        //Check if possible to move from tableau
        for (Tableau tableau : tableaus) {
            if(!tableau.isEmpty()) {
                Card card = tableau.getTopCard();

                for (Foundation foundation : foundations) {

                    //check first if card can be moved to foundation
                    if (foundation.countCards() > 0 &&
                            card.getValue() == foundation.peekCard().getValue() + 1 &&
                            card.getSuit() == foundation.peekCard().getSuit()) {

                        //If creating empty space, controls King is there to replace or next card in foundation is able to be put up aswell
                        if (tableau.getVisibleCards().length - 1 != 0 || tableau.countHiddenCards() != 0 || //Is card left behind
                                checkForMoveableCardFromValue(13) || //Is there a king to take the space
                                checkForMoveableCardFromSuitAndValue(card.getSuit(), card.getValue()+1)) { //Is the card needed for another card
                            return "Move " + card.toString() + " to it's respective foundation";
                        }
                    }
                }
            }
        }
        //Check if possible to move from waste
        if(waste != null) {
            for(Foundation foundation : foundations) {
                if(foundation.countCards() > 0 &&
                        waste.getValue() == foundation.peekCard().getValue() + 1 &&
                        waste.getSuit() == foundation.peekCard().getSuit()) {
                    return "Move " + waste.toString() + " to it's respective foundation";
                }
            }
        }
        return "";
    }

    /**
     * Searches tableaus for moveable card of given value
     * @param value Value to search for
     * @return  True if card has been found
     */
    private boolean checkForMoveableCardFromValue(int value) {
        boolean result = false;
        for(Tableau tableau : tableaus) {
            result = tableau.searchMoveableCardByValue(value) && !(value == 13 && tableau.countHiddenCards() == 0); //True if card found and it isn't a King on an empty space
            if(result) break;
        }
        return result || waste != null && (waste.getValue() == value); //returns true if found in tableau or in waste
    }

    /**
     * Searches tableaus for moveable card of given suit and value
     * @param suit  Suit to search for
     * @param value Value to search for
     * @return  True if card has been found
     */
    private boolean checkForMoveableCardFromSuitAndValue(int suit, int value) {
        boolean result = false;
        for(Tableau tableau : tableaus) {
            result = tableau.searchMoveableCardBySuitAndValue(suit, value);
            if(result) break;
        }
        return result || waste != null && (waste.getSuit() == suit && waste.getValue() == value); //returns true if found in tableau or in waste
    }

    //TODO: fix this
    //Hvis muligt sørg for at “typerne” passer. F.eks. hvis du kan rykke en hjerter 4 til to forskellige 5’er så prioriter den som har en hjerter 6
    public String typeStreak() {
        Card[] cards, cards2;
        String move = "";
        for (Tableau tableau : tableaus) {
            cards = tableau.getVisibleCards();

            for (Tableau tableau2 : tableaus) {
                cards2 = tableau2.getVisibleCards();
                //Hvis en af bunkerne er tomme er der ingen grund til at sammenligne dem
                if (cards.length - 1 >= 0 && cards2.length - 1 >= 0) {
                    //hvis øverste kort i tableu passer med anden tableus øverste kort lig den på hvis "typerne" passer ellers vent
                    if (cards[cards.length - 1].getValue() == cards2[cards2.length - 1].getValue() - 1 && cards[cards.length - 1].getSuit() % 2 != cards2[cards2.length - 1].getSuit() % 2) {
                        move = "Tag " + cards[cards.length - 1] + " og placer kortet på " + cards2[cards2.length - 1].toString();
                        if (cards2.length - 2 >= 0 && cards[cards.length - 1].getSuit() == cards2[cards2.length - 2].getSuit()) {
                            return move;
                        }
                    }
                }
            }

            //hvis waste passer så lig den på
            if (waste != null && cards.length - 1 >= 0 && cards[cards.length - 1].getValue() - 1 == waste.getValue() && cards[cards.length - 1].getSuit() % 2 != waste.getSuit() % 2) {
                move = "Tag " + waste.toString() + " og placer kortet på " + cards[cards.length - 1].toString();
                if (cards.length - 2 >= 0 && waste.getSuit() == cards[cards.length - 2].getSuit()){
                    return move;
                }
            }
        }

        return move;
    }

    /**
     * Translator to tell if possible to draw card from the waste pile
     * @return  Instructions to player
     */
    public String revealCardFromWaste() {
        return wastePile ? "Vend et kort fra grundbunken" : "" ;
    }

    //TODO Take a stance to delete this, literally only returns a string
    //Game is unsolvable (redo last move(s) or give up)
    public String endGame() {
        return "Game is unsolvable (redo last move(s) or give up)";
    }

}
