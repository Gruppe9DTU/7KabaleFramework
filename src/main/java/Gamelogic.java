import java.util.ArrayList;
import java.util.List;

public class Gamelogic {
    Deck deck;
    Waste waste;
    Tableau[] tableau;
    Foundation[] foundation;

    public Gamelogic() {
        deck = new Deck();
        deck.shuffle();
        foundation = new Foundation[4];
        for(int i = 0 ; i < foundation.length ; i++ ) foundation[i] = new Foundation();
        tableau = new Tableau[7];
        for(int i = 0 ; i < tableau.length ; i++) {
            List<Card> visibleCards = new ArrayList();
            visibleCards.add(deck.getNextCard());
            tableau[i] = new Tableau(i, visibleCards); //'i' in constructor needs to be replaced by a read value of how many hidden cards we can see
        }
        waste = new Waste(deck.getDeck().size() - 21, deck);

        printGame();
    }

    private void printGame() {
        //Waste
        String wasteNfoundation, shownWaste = "";
//        for(Card c : waste.getKnownCards()) { shownWaste += c.shortString() + "|"; }
        if(waste.getKnownCards().size() != 0) {
            shownWaste += waste.getKnownCards().get(waste.getKnownCards().size() - 1).shortString();
        } else shownWaste += "Emp ";
        //Foundation
        String foundationString = "";
        for(int i = 0 ; i < foundation.length ; i++){
            foundationString += foundation[i].countCards() > 0 ? foundation[i].peekCard().shortString()+ " " : "Emp ";
        }
        wasteNfoundation = waste.wasteSize() + "|"+ shownWaste + "     " + foundationString;
        System.out.println(wasteNfoundation);
        //Tableau
        String tableauLengths = "";
        String tableauValues = "";
        for(int i = 0 ; i < tableau.length ; i++) {
            tableauLengths += " " + tableau[i].countHiddenCards() + "  ";
            tableauValues += tableau[i].isEmpty() ? "Emp " : tableau[i].getTopCard().shortString() + " " ;
        }
        System.out.println(tableauLengths);
        System.out.println(tableauValues);
    }

    public Tableau[] getTableau() {
        return tableau;
    }

    public Foundation[] getFoundation() {
        return foundation;
    }
}
