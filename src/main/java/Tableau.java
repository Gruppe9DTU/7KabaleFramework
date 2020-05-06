import java.util.ArrayList;
import java.util.List;

public class Tableau {
    private int hiddenCards;
    private List<Card> visibleCards = new ArrayList<Card>();

    /**
     * For testing purposes
     *
     * @param hiddenCards
     */
    public Tableau(int hiddenCards) {
        this.hiddenCards = hiddenCards;
    }

    public Tableau(int hiddenCards, List<Card> visibleCards) {
        this.hiddenCards = hiddenCards;
        this.visibleCards.addAll(visibleCards);
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

    public Card getTopCard() {
        return visibleCards.get(visibleCards.size() - 1);
    }

    /**
     * Method to find a moveable card of a chosen value
     *
     * @param value Value of 1-13 to search for
     * @return      If card of chosen value is found and moveable
     */
    public boolean searchMoveableCardByValue(int value) {
        for(Card card : getVisibleCards()) {
            if (card.getValue() == value) return true;
        }
        return false;
    }

    public boolean searchMoveableCardBySuitAndValue(int suit, int value) {
        for(Card card : getVisibleCards()) {
            if (card.getSuit() == suit && card.getValue() == value) return true;
        }
        return false;
    }
}
