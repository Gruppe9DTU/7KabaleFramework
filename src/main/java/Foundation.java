import java.util.ArrayList;
import java.util.List;

/**
 * Class to act as a Foundation of a game of Solitaire. Foundation is where cards are collected Ace to King to win the game
 */
public class Foundation {
    private List<Card> cards = new ArrayList<>();

    public Foundation() {} //Default constructor
    public Foundation(Card card) { cards.add(card); } //Constructor with top card

    /**
     * Adds a card to the top of the Foundation, if it is able to.
     *
     * @param card  Card instance to be added
     */
    public void addCard(Card card) {
        if (cards.size() > 0) { //If there are cards in the Foundation
            Card lastcard = peekCard();
            if(card.getSuit() != lastcard.getSuit() || card.getValue() != (lastcard.getValue() + 1))
                System.out.println("Cannot add card to deck");
            else
                cards.add(card);
        }
        else { //If it is the first card in the Foundation
            if(card.getValue() != 1)
                System.out.println("Cannot add non-aces to empty foundation");
            else
                cards.add(card);
        }
    }

    /**
     * Takes the card from the top of the foundation
     *
     * @return  Card instance to be removed from Foundation
     */
    public Card takeCard() { return cards.remove(cards.size()-1); }

    /**
     * Returns the card on the top of the foundation
     *
     * @return  Card instance of the top card
     */
    public Card peekCard() { return cards.size() > 0 ? cards.get(cards.size()-1) : null; }

    /**
     * Returns the amount of cards in the pile, from the value of the top card.
     * This gives the correct value, for a proper played game, even if only the top card is added.
     *
     * @return  Amount of cards in foundation
     */
    public int countCards() { return peekCard() != null ? peekCard().getValue() : 0; }

    /**
     * Looks if the top card is an ace, if so marks it as complete.
     *
     * @return True if foundation is complete, else false
     */
    public Boolean isComplete() { return peekCard().getValue() == 13 && countCards() == 13; }
}