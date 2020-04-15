import java.util.List;

public class MoveAlgoritm {

    private List<Tableau> tableaus;
    private List<Foundation> foundations;
    private List<Card> treakbunke;

    public MoveAlgoritm(List<Tableau> tableaus, List<Foundation> foundations, List<Card> treakbunke) {
        this.tableaus = tableaus;
        this.foundations = foundations;
        this.treakbunke = treakbunke;
    }

    public String getBestMove(){

        tabuleSorter();

        String bestMove = "";

        
        return bestMove;
    };

    //sort the tableau order so that the one with the highest amount of cards is first
    public void tabuleSorter (){

        Tableau holder = null;

    }

    //Altid ryk en es til grundbunker
    private String checkEs(){

        for (Tableau tableau : tableaus){

            //check if first visible card in stable is es
            Card card = tableau.getVisibleCards()[tableau.getVisibleCards().length -1];

            if (card.getValue() == 1){
                return "Move " + card.toString() + " to Foundation";
            }
        }

        return "not possible";
    };

    //Ryk ikke kort fra byggestabel til grundbunken med mindre der er en konge, som kan tage dens plads
    //Hvis rød og sort konge kan fylde en tom plads i byggestablen, vælg den hvor der er størst mulighed for at lave en stabel (hvis der ligger rød knægt, vælg rød konge og vent på sort dronning osv.)
    private String kingCheck(){

        List<Card> kingsAvalible = null;

        //find available kings
        for (Tableau tableau : tableaus){

            //check if first card is king
            // TODO: might want to make it more complex later
            Card card = tableau.getVisibleCards()[tableau.getVisibleCards().length -1];

            if (card.getValue() == 13){
                kingsAvalible.add(card);
            }
        }

        if (kingsAvalible.size() > 1){

            int kingSuitStreak = 0;
            int leadingKingSuitStreak = 0;
            Card leadingCard = null;

            for (Card king : kingsAvalible){

                for (Tableau tableau : tableaus){

                    Card[] card = tableau.getVisibleCards();

                    for (int i = 0; i < card.length - 1; i++){
                        if (card[i].getSuit() == king.getSuit() && card[i].getValue() == king.getValue() - kingSuitStreak){
                            kingSuitStreak++;
                        }
                    }

                }

                if (kingSuitStreak > leadingKingSuitStreak){
                    leadingKingSuitStreak = kingSuitStreak;
                    leadingCard = king;
                }
            }

            return "Best king to move is " + leadingCard.toString();

        } else if (kingsAvalible.size() == 1){
            return "Move " + kingsAvalible.get(1).toString() + "to empty space";

        }

        return "no kings available";

    };

    //Afslør skjulte kort (prioriterer bunke med højest antal skjulte kort) (flyt slutkortet i stablen, samt det der hænger fast på den, over på en anden stabel, hvis det kommer til at vende et skjult kort)
    private String revealHiddenCard(){
        return"";
    }

    //Tag kort fra grundbunken ned hvis det muliggøre øvrige træk (og giver mening)
    private String grundbunkeToBuildStable(){
        return "";
    }

    //Tjek om kort kan lægges til grundbunken
    private String moveToFoundation(){
        return "";
    }

    //Hvis muligt sørg for at “typerne” passer. F.eks. hvis du kan rykke en hjerter 4 til to forskellige 5’er så priority den som har en hjerter 6
    private String typeStreak(){
        return "";
    }

    //Vend kort fra bunke hvis ingen træk muligt.
    private String revealCardFromTreakBunke(){
        return "";
    }

    //Game is unsolvable (redo last move(s) or give up)
    private String endGame(){
        return "Game is unsolvable (redo last move(s) or give up)";
    }

}
