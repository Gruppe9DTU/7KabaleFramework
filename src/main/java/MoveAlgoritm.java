import java.util.List;

public class MoveAlgoritm {

    private List<Tableau> tableaus;
    private List<Foundation> foundations;
    private Card waste;

    public MoveAlgoritm(List<Tableau> tableaus, List<Foundation> foundations, Card waste) {
        this.tableaus = tableaus;
        this.foundations = foundations;
        this.waste = waste;
    }

    public String getBestMove() {

        tabuleSorter(0, tableaus.size() - 1);

        String bestMove = "";

        List<String> moves = null;

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

    //sort the tableau order so that the one with the highest amount of cards is first
    //uses the quick-sort algorithm to sort the list of tableau's
    private void tabuleSorter(int l, int r) {

        if (l >= r) {
            return;
        }

        int pivot = tableaus.get(r).getVisibleCards().length + tableaus.get(r).countHiddenCards();
        int cnt = l;

        for (int i = l; i <= r; i++) {

            int tableauTotalCards = tableaus.get(i).getVisibleCards().length + tableaus.get(i).countHiddenCards();

            if (tableauTotalCards <= pivot) {

                Tableau tabuleauHolder = tableaus.get(cnt);
                tableaus.set(cnt, tableaus.get(i));
                tableaus.set(i, tabuleauHolder);

                cnt++;
            }
        }

        tabuleSorter(l, cnt - 2);
        tabuleSorter(cnt, r);

    }

    //Altid ryk en es til grundbunker
    private String checkEs() {

        for (Tableau tableau : tableaus) {

            //check if first visible card in stable is es
            Card card = tableau.getVisibleCards()[tableau.getVisibleCards().length - 1];

            if (card.getValue() == 1) {
                return "Move " + card.toString() + " to Foundation";
            }
        }

        return "not possible";
    }

    //Ryk ikke kort fra byggestabel til grundbunken med mindre der er en konge, som kan tage dens plads
    //Hvis rød og sort konge kan fylde en tom plads i byggestablen, vælg den hvor der er størst mulighed for at lave en stabel (hvis der ligger rød knægt, vælg rød konge og vent på sort dronning osv.)
    private String kingCheck() {

        List<Card> kingsAvalible = null;

        //find available kings
        for (Tableau tableau : tableaus) {

            //check if first card is king
            // TODO: might want to make it more complex later
            Card card = tableau.getVisibleCards()[tableau.getVisibleCards().length - 1];

            if (card.getValue() == 13) {
                kingsAvalible.add(card);
            }
        }

        if (kingsAvalible == null) {

        } else if (kingsAvalible.size() > 1) {

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

                if (kingSuitStreak > leadingKingSuitStreak) {
                    leadingKingSuitStreak = kingSuitStreak;
                    leadingCard = king;
                }
            }

            return "Best king to move is " + leadingCard.toString();

        } else if (kingsAvalible.size() == 1) {
            return "Move " + kingsAvalible.get(1).toString() + "to empty space";

        }

        return "";

    }

    private List<Tableau> sortAfterHiddenCards(){

        //TODO: make sorting algoritm

        return tableaus;
    }

    //Afslør skjulte kort (prioriterer bunke med højest antal skjulte kort) (flyt slutkortet i stablen, samt det der hænger fast på den, over på en anden stabel, hvis det kommer til at vende et skjult kort)
    private String revealHiddenCard(){

        List<Tableau> hiddenCardsTableau = sortAfterHiddenCards();


        return "";
    }

    //Tag kort fra grundbunken ned hvis det muliggøre øvrige træk (og giver mening)
    private String grundbunkeToBuildStable() {
        for (Foundation foundation : foundations) {
            for (Tableau tableau : tableaus) {
                Card[] cards = tableau.getVisibleCards();
                if (cards[cards.length - 1].getValue() - 1 == foundation.peekCard().getValue() && cards[cards.length - 1].getSuit() % 2 != foundation.peekCard().getSuit() % 2) { // Tjek om det er muligt at rykke et kort fra grundbunken ned på en af rækkerne
                    if (waste.getValue() == foundation.peekCard().getValue() - 1 && waste.getSuit() % 2 != foundation.peekCard().getSuit() % 2) { //Tjek om det vil muliggøre andre træk at rykke kort fra grundbunken ned på en af rækkerne
                        return "Ryk " + foundation.peekCard().toString() + "fra grundbunken ned på rækken med " + cards[cards.length - 1].toString();
                    }
                }
            }
        }
        return "";
    }

    //Tjek om kort kan lægges til grundbunken
    private String moveToFoundation() {
        for (Tableau tableau : tableaus) {
            Card[] cards = tableau.getVisibleCards();
            for (Foundation foundation : foundations) {

                //check first if card can be moved to foundation
                if (cards[cards.length - 1].getValue() == foundation.peekCard().getValue() + 1 && cards[cards.length - 1].getSuit() == foundation.peekCard().getSuit()) {

                    //now check if move creates an empty space and if a king is avalible to take that place
                    if (cards.length - 1 != 0 && tableau.countHiddenCards() != 0 || !kingCheck().equals("")) {
                        return "Tag " + cards[cards.length - 1].toString() + " og placer den i grundbunken med matchende type";
                    }

                }
            }
        }

        return "";
    }

    //Hvis muligt sørg for at “typerne” passer. F.eks. hvis du kan rykke en hjerter 4 til to forskellige 5’er så prioriter den som har en hjerter 6
    private String typeStreak() {
        Card[] cards;
        List<Tableau> useableTableaus = null;
        for (Tableau tableau : tableaus) {
            cards = tableau.getVisibleCards();

            if (cards[cards.length - 1].getValue() == waste.getValue() - 1 && cards[cards.length - 1].getSuit() % 2 != waste.getSuit() % 2) {
                useableTableaus.add(tableau);
            }
        }
        if (useableTableaus != null) {
            for (Tableau utableau : useableTableaus) {
                cards = utableau.getVisibleCards();

                if (cards.length >= 3) {
                    if (cards[cards.length - 3].getSuit() == waste.getSuit()) {
                        return "Tag " + waste.toString() + " og placer kortet på " + cards[cards.length - 1].toString();
                    }
                }
            }
        }

        return "";
    }

    //Vend kort fra grundbunken hvis ingen træk muligt.
    private String revealCardFromWaste() {

        if (waste != null) { //Hvis det er muligt at trække et kort fra grundbunken skal den returnere true
            return "Vend et kort fra grundbunken";
        }

        return "";
    }

    //Game is unsolvable (redo last move(s) or give up)
    private String endGame() {
        return "Game is unsolvable (redo last move(s) or give up)";
    }

}
