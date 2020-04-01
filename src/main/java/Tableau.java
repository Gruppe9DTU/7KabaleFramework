import java.util.ArrayList;
import java.util.List;

public class Tableau {
    private int hiddenCards;
    private List<Card> visibleCards = new ArrayList<Card>();

    public Tableau(int hiddenCards) {
        this.hiddenCards = hiddenCards;
    }

    public Card[] getVisibleCards() {
        Card[] returnval = new Card[visibleCards.size()];
        for(int i = 0; i < visibleCards.size(); i++) {
            returnval[i] = visibleCards.get(i);
        }
        return returnval;
    }

    public int countHiddenCards() {
        return hiddenCards;
    }

    /***
     * for testing purposes
     *
     * @param card
     */
    public void revealCard(Card card) {
        hiddenCards -= 1;
        visibleCards.add(card);
    }

    public boolean isEmpty() {
        if (hiddenCards == 0 && visibleCards.size() == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public void addCardToStack(Card card) {
        if(visibleCards.size() > 0) {
            Card lastCard = visibleCards.get(visibleCards.size() - 1);
            if (lastCard.getSuit() % 2 == card.getSuit() % 2 || card.getValue() != (lastCard.getValue() - 1))
                System.out.println("Wrong card type, cannot stack hiddenCards of the same color, or of higher value");
            else
                visibleCards.add(card);
        }
        else {
            visibleCards.add(card);
        }
    }
}
