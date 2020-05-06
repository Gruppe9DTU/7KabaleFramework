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
        moves.add(grundbunkeToBuildStable());
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

    private List<Tableau> sortAfterHiddenCards(){

        //TODO: make sorting algoritm
        List<Tableau> tableau = tableaus;

        return tableau;
    }

    //Afslør skjulte kort (prioriterer bunke med højest antal skjulte kort) (flyt slutkortet i stablen, samt det der hænger fast på den, over på en anden stabel, hvis det kommer til at vende et skjult kort)
    public String revealHiddenCard(){

        List<Tableau> hiddenCardsTableaus = sortAfterHiddenCards();

        return "";
    }

    /**
     * Control if taking a card from foundation to tableau opens up other interactions
     * @return  String  Instructions to player
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
    public String testGrundbunkeToBuildStable() {
        return grundbunkeToBuildStable();
    }

    //Tjek om kort kan lægges til grundbunken
    public String moveToFoundation() {
        for (Tableau tableau : tableaus) {
            Card[] cards = tableau.getVisibleCards();
            for (Foundation foundation : foundations) {

                //check first if card can be moved to foundation
                //if count cards == 0 it should be fixed by the checkEs function
                if (foundation.countCards() > 0 && cards[cards.length - 1].getValue() == foundation.peekCard().getValue() + 1 && cards[cards.length - 1].getSuit() == foundation.peekCard().getSuit()) {

                    //now check if move creates an empty space and if a king is avalible to take that place
                    if (cards.length - 1 != 0 && tableau.countHiddenCards() != 0 || !kingCheck().equals("")) {
                        return "Tag " + cards[cards.length - 1].toString() + " og placer den i grundbunken med matchende type";
                    }

                }
            }
        }

        return "";
    }

    //TODO: fix this
    //Hvis muligt sørg for at “typerne” passer. F.eks. hvis du kan rykke en hjerter 4 til to forskellige 5’er så prioriter den som har en hjerter 6
    public String typeStreak() {
        Card[] cards;
        List<Tableau> useableTableaus = null;
        for (Tableau tableau : tableaus) {
            cards = tableau.getVisibleCards();

            if (waste != null && cards[cards.length - 1].getValue() == waste.getValue() - 1 && cards[cards.length - 1].getSuit() % 2 != waste.getSuit() % 2) {
                useableTableaus.add(tableau);
            }
        }

        if (useableTableaus != null) {
            for (Tableau utableau : useableTableaus) {
                cards = utableau.getVisibleCards();
                return "Tag " + waste.toString() + " og placer kortet på " + cards[cards.length - 1].toString();
            }
        }

        return "";
    }

    /**
     * Translator to tell if possible to draw card from the waste pile
     * @return  String  Instructions to player
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
