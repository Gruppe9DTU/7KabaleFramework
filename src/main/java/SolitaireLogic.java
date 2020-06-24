import javafx.scene.control.Tab;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolitaireLogic {
    private Deck deck;
    private Waste waste;
    private Tableau[] tableau;
    private Foundation[] foundation;
    private boolean isWon = false;

    /**
     * Constructor for the controller
     */
    public SolitaireLogic() {
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
        //Tableau
        String tableauLengths = "";
        String tableauValues = "";
        int largest = 0;
        for(Tableau t : tableau) {
            if(t.getVisibleCards().size() > largest) {
                largest = t.getVisibleCards().size();
            }
        }
        for(int i = 0; i < largest; i++) {
            for (int j = 0; j < tableau.length; j++) {
                if (i == 0) tableauLengths += " " + tableau[j].countHiddenCards() + "  ";
                if (i == 0) tableauValues += tableau[j].isEmpty() ? "Emp " : tableau[j].getVisibleCards().size() == 0 ? "Hid " : tableau[j].getVisibleCards().size() < i+1 ? "    " :
                        tableau[j].getVisibleCards().get(i).shortString() + " ";
                else tableauValues += tableau[j].isEmpty() ? "    " : tableau[j].getVisibleCards().size() == 0 ? "    " : tableau[j].getVisibleCards().size() < i+1 ? "    " :
                        tableau[j].getVisibleCards().get(i).shortString() + " ";
            }
            tableauValues += "\n";
        }

        return (wasteNfoundation + "\n" + tableauLengths + "\n" + tableauValues + "\n");
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

    public List<Card> takeFromTableau(int i, int j) {
        return tableau[i].removeCardFromStack(j);
    }

    public int getVisibleCardsTablaeu(int i) {
        return tableau[i].getVisibleCards().size();
    }

    public void addToTableau(List<Card> card, int i, int j) {
        if(tableau[i].addListCardsToStack(card) && j != 999) {
            tableau[j].removeListCards(card, deck);
        }
    }

    public void removeFromTableau(List<Card> card, int i) {
            if(i != 999) tableau[i].removeListCards(card, deck);
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

    public void updateIsWon() {
        isWon = true;
        for(Foundation f : foundation) {
            if(f.countCards() != 0){
                if(f.peekCard().getSuit() != 13) {
                    isWon = false;
                    break;
                }
            } else {
                isWon = false;
                break;
            }
        }
    }

    public boolean getIsWon() {
        return  isWon;
    }

}