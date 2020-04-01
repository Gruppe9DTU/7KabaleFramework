import java.util.List;

public class MoveAlgoritm {

    List<ByggeStable> byggestabler;
    List<GrundBunke> grundbunke;
    List<Card> treakbunke;

    public MoveAlgoritm(List<ByggeStable> byggestabler, List<GrundBunke> grundbunke, List<Card> treakbunke) {
        this.byggestabler = byggestabler;
        this.grundbunke = grundbunke;
        this.treakbunke = treakbunke;
    }

    private String move(){
        String bestMove = "";

        return bestMove;
    };


    //Altid ryk en es til grundbunker
    private String CheckEs(){

        for (int i = 0; i < byggestabler)

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
    private String moveToGrundbunke(){
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
        return "no additional turn is detected";
    }

}
