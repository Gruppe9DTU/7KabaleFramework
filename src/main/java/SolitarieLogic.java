import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SolitarieLogic {
    private Deck deck;
    private Waste waste;
    private Tableau[] tableau;
    private Foundation[] foundation;

    /**
     * Constructor for the controller
     */
    public SolitarieLogic() {
        deck = new Deck();
        deck.shuffle();
        waste = new Waste(false, null);
        tableau = new Tableau[7];
        for(int i = 0 ; i < 7 ; i++){
            List<Card> visibleCards = new ArrayList();
            visibleCards.add(deck.getNextCard());
            tableau[i] = new Tableau(i, visibleCards);
        }
        foundation = new Foundation[4];
        for(int i = 0 ; i < foundation.length ; i++ ) foundation[i] = new Foundation();
        waste = new Waste(deck.getDeck().size() - 21, deck);
    }
    /**
     * A printet version of the current game state
     */
    public String getGameState() {
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
        //wasteNfoundation = waste.isWastePilePresent() ? "W" : "Emp";
        wasteNfoundation = "" + waste.unknownSize();
        wasteNfoundation += "|"+ shownWaste + "     " + foundationString;
        System.out.println(wasteNfoundation);
        //Tableau
        String tableauLengths = "";
        String tableauValues = "";
        for(int i = 0 ; i < tableau.length ; i++) {
            tableauLengths += " " + tableau[i].countHiddenCards() + "  ";
            tableauValues += tableau[i].isEmpty() ? "Emp ": tableau[i].getVisibleCards().size() == 0 ? "Hid " : tableau[i].getTopCard().shortString() + " " ;
        }

        return (tableauLengths + "\n" + tableauValues + "\n");
    }

    public Tableau[] getTableau() { return tableau; }

    public void setTableaus(List<Integer> hiddenCards, List<List<Card>> transTableaus) {
        for (int i = 0 ; i < 7 ; i++) {
            if(!transTableaus.isEmpty())
                tableau[i] = new Tableau(hiddenCards.get(i), transTableaus.get(i));
            else tableau[i] = new Tableau(hiddenCards.get(i), new ArrayList<>());
        }
    }

    public Foundation[] getFoundation() { return foundation; }
    public void setFoundations(List<Card> cards) {
        for(int i = 0 ; i < 4 ; i++) {
            if(!cards.isEmpty())
                foundation[i] = new Foundation(cards.get(i));
            else foundation[i] = new Foundation();
        }
    }

    public Waste getWaste() { return waste; }
    public void setWaste(Waste waste) {
        this.waste = waste;
    }

    /**
     * Used for tests, to easily set the tableau
     * @param foundation
     */
    public void setFoundation(Foundation[] foundation){
        this.foundation = foundation;
    }

    public Card takeFromTableau(int i) {
        return tableau[i].removeCardFromStack(deck);
    }

    public void addToTableau(Card card, int i) {
        tableau[i].addCardToStack(card);
    }

    public Card takeFromFoundation(int i) {
        return foundation[i].takeCard();
    }

    public void addToFoundation(Card card, int i) {
        foundation[i].addCard(card);
    }

    public void revealFromWaste() {
        waste.revealCard();
    }

    public Card takeFromWaste() {
        if(waste.getKnownCards().size() > 0) {
            return waste.takeCard();
        }
        else return null;
    }
}