import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class to act as Tableau of a game of Solitaire. Tableau is where the game is mainly played and cards a put from King to Ace.
 */
public class Tableau {
    private int hiddenCards;                                //Amount of hidden cards in Tableau
    private List<Card> visibleCards = new ArrayList<>();    //List of Card instances

    /**
     * Constructor for Tableau class
     *
     * @param hiddenCards   Amount of hidden cards in Tableau
     * @param visibleCards  List of instances of Card in Tableau, in the order highest to lowest
     */
    public Tableau(int hiddenCards, List<Card> visibleCards) {
        this.hiddenCards = hiddenCards;
        if(visibleCards != null) this.visibleCards.addAll(visibleCards);
    }

    /**
     * Returns the amount of hidden cards in Tableau
     *
     * @return  Amount of hidden cards
     */
    public int countHiddenCards() { return hiddenCards; }

    /**
     * Controls for if the Tableau is empty, for both hidden and visible cards
     *
     * @return  True if no instance of Card is present, else false
     */
    public boolean isEmpty() { return hiddenCards == 0 && visibleCards.size() == 0; }

    /**
     * Adds a Card to the stack of visible cards, if conditions are met
     *
     * @param card  Instance of Card to be added to the List of visible cards
     */
    public boolean addCardToStack(Card card) {
        if(visibleCards.size() > 0) {
            Card lastCard = visibleCards.get(visibleCards.size() - 1);
            if (lastCard.getSuit() % 2 == card.getSuit() % 2 || card.getValue() != (lastCard.getValue() - 1)){
                System.out.println("Wrong card type, cannot stack cards of the same color, or of higher value");
                return false;
            }
            else {
                visibleCards.add(card);
                return true;
            }

        }
        else {
            if(card.getValue() == 13) {
                visibleCards.add(card);
                return true;
            }
            else {
                System.out.println("Cannot add non-kings to empty tableau");
                return false;
            }
        }
    }

    public void revealHiddenCard(Card card) {
        visibleCards.add(card);
    }

    public List<Card> removeCardFromStack(int i) {
        List<Card> toRemove = new ArrayList();
        for (int j = visibleCards.size() - 1; j >= visibleCards.size() - 1 - i; j--) {
            toRemove.add(visibleCards.get(j));
        }
        return toRemove;
    }

    public boolean addListCardsToStack(List<Card> cards) {
        for(Card c : cards) {
            if(!addCardToStack(c)) {
                return false;
            }
        }
        return true;
    }

    public void removeListCards(List<Card> cards, Deck deck) {
        visibleCards.removeAll(cards);
        if(hiddenCards != 0 && visibleCards.size() == 0) {
            hiddenCards--;
            revealHiddenCard(deck.getNextCard());
        }
    }

    /**
     * Searches through the Tableau, for a card that matches in wanted value
     *
     * @param value Value of 1-13 to search for
     * @return      If card of chosen value is found and moveable
     */
    public boolean searchForMoveableCardByValue(int value) {
        for(Card card : getVisibleCards()) {
            if (card.getValue() == value) return true;
        }
        return false;
    }

    /**
     * Search through the Tableau, for a card that matches in wanted suit and value
     *
     * @param suit  Wanted suit
     * @param value Wanted value
     * @return      True if card is in Tableau, else false
     */
    public boolean searchForMoveableCardBySuitAndValue(int suit, int value) {
        for(Card card : getVisibleCards()) {
            if (card.getSuit() == suit && card.getValue() == value) return true;
        }
        return false;
    }

    /**
     * Getter for list of visible cards
     *
     * @return  List of the visible cards of the Tableau
     */
    public List<Card> getVisibleCards() { return visibleCards; }

    /**
     * Returns instance of the card in the back of the Tableau, aka the Card with the highest value.
     * @return  Card in the back of the Tableau
     */
    public Card getTopCard() {
        return visibleCards.get(visibleCards.size()-1);
    }

    /**
     * Comparator methods to sort the cards after how many hidden cards is in the Tableau
     */
    public static Comparator<Tableau> HiddenCardsCompare = (tableau1, tableau2) -> {
        int numCards1 = tableau1.countHiddenCards();
        int numCards2 = tableau2.countHiddenCards();

        return numCards2-numCards1;
    };
}