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

        if (!checkEs().equals("")){
                return checkEs();

        } else if (!kingCheck().equals("")){
                return kingCheck();

        }else if (!revealHiddenCard().equals("")){
                return revealHiddenCard();

        } else if (!grundbunkeToBuildStable().equals("")){
                return grundbunkeToBuildStable();

        } else if (!moveToFoundation().equals("")){
                return moveToFoundation();

        } else if (!moveTableau().equals("")){
            return moveTableau();

        } else if (!typeStreak().equals("")){
                return typeStreak();

        } else if (!revealCardFromWaste().equals("")){
                return revealCardFromWaste();

        } else {
            return "Game is unsolvable (redo last move(s) or give up)";
        }

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

    //Ryk ikke kort fra byggestabel til grundbunken med mindre der er en konge, som kan tage dens plads
    //Hvis rød og sort konge kan fylde en tom plads i byggestablen, vælg den hvor der er størst mulighed for at lave en stabel (hvis der ligger rød knægt, vælg rød konge og vent på sort dronning osv.)
    public String kingCheck() {

        List<Card> kingsAvalible = new ArrayList<Card>();

        int emptySpaces = 0;
        //find available kings

        for (Tableau tableau : tableaus) {

            if (tableau.isEmpty()){
            emptySpaces++;
            }

            //check if first card is king
            // TODO: might want to make it more complex later
            if (!tableau.isEmpty()) {
                Card card = tableau.getVisibleCards()[tableau.getVisibleCards().length - 1];

                if (card.getValue() == 13) {
                    kingsAvalible.add(card);
                }
            }

        }

        if (kingsAvalible.size() > 1 && emptySpaces > 0) {

            int kingSuitStreak = 0;
            int leadingKingSuitStreak = 0;
            Card leadingCard = null;

            for (Card king : kingsAvalible) {

                for (Tableau tableau : tableaus) {

                    Card[] card = tableau.getVisibleCards();

                    for (int i = 0; i < card.length; i++) {
                        if (card[i].getSuit() == king.getSuit() && card[i].getValue() == king.getValue() - kingSuitStreak) {
                            kingSuitStreak++;
                        }
                    }

                }

                if (kingSuitStreak >= leadingKingSuitStreak) {
                    leadingKingSuitStreak = kingSuitStreak;
                    leadingCard = king;
                }
            }

            return "Best king to move is " + leadingCard.toString();

        } else if (kingsAvalible.size() == 1 && emptySpaces > 0) {
            return "Move " + kingsAvalible.get(0).toString() + " to empty space";

        }

        return "";

    }

    /**
     * Checks if there is a tableau where the player can turn over a hidden card
     *
     * @return  Instruction to player
     */
    public String revealHiddenCard(){
        for(Tableau tableau : tableaus) {
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
    public String grundbunkeToBuildStable() {
        for (Foundation foundation : foundations) {
            if (foundation.countCards() > 0) {  //If there is a card in the foundation
                Card foundationCard = foundation.peekCard(); //Set current possible card

                for (Tableau tableau : tableaus) {
                    Card[] tableauCards = tableau.getVisibleCards();
                    //Check if possible to place card
                    if (tableauCards.length != 0 &&
                            tableauCards[tableauCards.length - 1].getValue() - 1 == foundationCard.getValue()
                            && tableauCards[tableauCards.length - 1].getSuit() % 2 != foundationCard.getSuit() % 2) { //Check if possible to move card from foundation to tableau

                        //Check if it opens up possibilities //TODO Check with others if best order
                        //First check waste
                        if (waste != null && waste.getValue() == foundationCard.getValue() - 1 && waste.getSuit() % 2 != foundationCard.getSuit() % 2) {
                            return "Ryk " + foundationCard.toString() + " fra grundbunken ned på rækken med " + tableauCards[tableauCards.length - 1].toString();
                        }

                        //Then check other tableaus
                        for (Tableau otherTableau : tableaus) {
                            if (tableau != otherTableau) {
                                for(Card card : otherTableau.getVisibleCards()) {
                                    if(card.getValue() == foundationCard.getValue()-1 && card.getSuit() % 2 != foundationCard.getSuit() % 2) {
                                        return "Ryk " + foundationCard.toString() + " fra grundbunken ned på rækken med " + tableauCards[tableauCards.length - 1].toString();
                                    }
                                }
                            }
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

    /**
     * Moves cards in tableaus to another tableaus to reveal hidden card or create empty space
     *
     * @return  Instructions for player
     */
    public String moveTableau(){
        Card[] cards, cards2;
        String move = "";

        for (Tableau tableau : tableaus) {
            cards = tableau.getVisibleCards();
            for (Tableau tableau2 : tableaus) {
                cards2 = tableau2.getVisibleCards();

                //Hvis en af bunkerne er tomme er der ingen grund til at sammenligne dem
                if (cards.length - 1 >= 0 && cards2.length - 1 >= 0) {
                    //Hvis der er mere end ét kort tilstæde i byggestablen og det nederste kort passer på det øverste kort i en anden byggestabel, ryk alle de synlige kort fra byggestablen over til den anden byggestabel
                    if (cards.length - 1 != 0 && cards[0].getValue() == cards2[cards2.length - 1].getValue() - 1 && cards[0].getSuit() % 2 != cards2[cards2.length - 1].getSuit() % 2) {
                        move = "Tag alle de synlige kort fra byggestablen med det nederste kort " + cards[0] + " og placer dem på " + cards2[cards2.length - 1].toString();
                        if (cards2.length - 2 >= 0 && cards[0].getSuit() == cards2[cards2.length - 2].getSuit()) {
                            return move;
                        }
                    }
                }
            }
        }
        return move;
    }

    /**
     * Moves top card of tableau or waste to another tableau if the types match.
     * For example if you can move a 4 of hearts to a 5 of spades and 5 of clubs, prioritize the one that has a 6 of hearts in the same tableau.
     *
     * @return  Instructions for player
     */
    //Hvis muligt sørg for at “typerne” passer. F.eks. hvis du kan rykke en hjerter 4 til to forskellige 5’er så prioriter den som har en hjerter 6
    public String typeStreak() {
        Card[] cards, cards2;
        String move = "", prioMove = "";
        for (Tableau tableau : tableaus) {
            cards = tableau.getVisibleCards();

            for (Tableau tableau2 : tableaus) {
                cards2 = tableau2.getVisibleCards();

                //Hvis en af bunkerne er tomme er der ingen grund til at sammenligne dem
                if (cards.length - 1 >= 0 && cards2.length - 1 >= 0) {

                    //Hvis øverste kort i tableu passer med anden tableus øverste kort lig den på hvis "typerne" passer ellers vent
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
                    prioMove = move;
                }
            }
        }

        return !prioMove.equals("") ? prioMove : move;
    }

    /**
     * Translator to tell if possible to draw card from the waste pile
     * @return  Instructions to player
     */
    public String revealCardFromWaste() {
        return wastePile ? "Vend et kort fra grundbunken" : "" ;
    }


}
