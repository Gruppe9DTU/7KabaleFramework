import java.util.List;

public class MoveAlgoritm {

    List<Tableau> tableaus;
    List<Foundation> foundations;
    Waste waste;

    public MoveAlgoritm(List<Tableau> tableaus, List<Foundation> foundations, Waste waste) {
        this.tableaus = tableaus;
        this.foundations = foundations;
        this.waste = waste;
    }

    public String getBestMove(){
        String bestMove = "";


        return bestMove;
    };


    //Altid ryk en es til grundbunker
    private String checkEs(){

        for (Tableau tableu : tableaus){
            //check if first visible card in stable is es

            Card[] card = tableu.getVisibleCards();
            if (card[card.length-1].getValue() == 1){
                return "Move " + card[card.length-1].toString() + " to Foundation";
            }
        }
        return "";
    };

    //Ryk ikke kort fra byggestabel til grundbunken med mindre der er en konge, som kan tage dens plads
    private String kingCheck(){

        return "";
    };

    //Hvis rød og sort konge kan fylde en tom plads i byggestablen, vælg den hvor der er størst mulighed for at lave en stabel (hvis der ligger rød knægt, vælg rød konge og vent på sort dronning osv.)
    private String highestStableStreak(){
        return "";
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
        for (Tableau tableau : tableaus){
            Card[] cards = tableau.getVisibleCards();
            for (Foundation foundation: foundations) {
                if (cards[cards.length-1].getValue() == foundation.peekCard().getValue()+1 && cards[0].getSuit() == foundation.peekCard().getSuit()){
                    //foundation.addCard(cards[cards.length-1]);
                    return "Tag " + cards[cards.length-1].toString() + " og placer den i grundbunken med matchende type";
                }
            }
        }

        return "";
    }

    //Hvis muligt sørg for at “typerne” passer. F.eks. hvis du kan rykke en hjerter 4 til to forskellige 5’er så priority den som har en hjerter 6
    private String typeStreak(){
        return "";
    }

    //Vend kort fra grundbunken hvis ingen træk muligt.
    private String revealCardFromWaste(){

        if (waste.revealCard()){ //Hvis det er muligt at trække et kort fra grundbunken skal den returnere true
            return "Vend et kort fra grundbunken";
        }

        return "";
    }

    //Game is unsolvable (redo last move(s) or give up)
    private String endGame(){
        return "Game is unsolvable (redo last move(s) or give up)";
    }

}
