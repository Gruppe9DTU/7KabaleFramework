import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    public Game() {
        List<Card> deck = generateDeck();
        shuffle(deck);
        for (Card card: deck) {
            System.out.println(card.toString());
        }
    }

    /**
     * generated a full deck of cards
     *
     * @return a full 52-card deck without jokers.
     */
    public List<Card> generateDeck() {
        List deck = new ArrayList<Card>();
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 14; j++) {
                deck.add(new Card(i, j));
            }
        }
        return deck;
    }

    /**
     * helper method for the shuffle method, swaps the position of two cards in a deck.
     *
     * @param deck The source deck
     * @param i index of card one
     * @param j index of card two
     */
    public void swap(List<Card> deck, int i, int j) {
        Card helper = deck.get(i);
        deck.set(i, deck.get(j));
        deck.set(j, helper);
    }

    /**
     * Method to shuffle a deck of cards
     *
     * @param deck the deck which you want to shuffle
     */
    public void shuffle(List<Card> deck) {
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < deck.size(); i++) {
            swap(deck, i, random.nextInt(deck.size()));
        }
    }
}
