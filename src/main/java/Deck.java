import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//TODO Bliver brugt af tests, kan dette undgås så klassen kan slettes?
public class Deck {
    List<Card> deck = new ArrayList<Card>();

    public Deck() {
        generateDeck();
    }

    /**
     * generated a full deck of cards
     *
     */
    public void generateDeck() {
        List generated = new ArrayList<Card>();
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 14; j++) {
                generated.add(new Card(i, j));
            }
        }
        deck = generated;
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
     */
    public void shuffle() {
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < deck.size(); i++) {
            swap(deck, i, random.nextInt(deck.size()));
        }
    }

    public List<Card> getDeck() { return deck; }

    public Card getNextCard() {
        return deck.remove(deck.size()-1);
    }
}