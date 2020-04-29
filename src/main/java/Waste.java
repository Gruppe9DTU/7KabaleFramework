import java.util.ArrayList;
import java.util.List;

public class Waste {
    private List<Card> knowncards = new ArrayList<Card>();
    private List<Card> unknowncards;
    private int iterator = 0;

    public Waste(List<Card> cards) {
        unknowncards = cards;
    }

    public Waste(int n, Deck deck) {
        unknowncards = new ArrayList<Card>();
        for(int i = 0 ; i < n ; i++) {
            unknowncards.add(deck.getNextCard());
        }
    }

    public Card takeCard() {
        Card temp = knowncards.remove(iterator);
        if(iterator == knowncards.size()-1)
            iterator--;
        return temp;
    }

    public Card revealCard() {
        if(unknowncards.size() > 0) {
            Card revealed = unknowncards.remove(unknowncards.size() - 1);
            addToKnown(revealed);
            iterator = knowncards.size() - 1;
            return revealed;
        }
        else {
            if(iterator > knowncards.size()-1) {
                iterator = 0;
            }
            Card temp = knowncards.get(iterator);
            iterator++;
            return temp;
        }
    }

    public void addToKnown(Card card) { knowncards.add(card); }

    public List<Card> getKnownCards() { return knowncards; }

    public int wasteSize() { return unknowncards.size(); }

    public Card lookAtTop(){

        try {
            return knowncards.get(iterator);
        }catch (IndexOutOfBoundsException e){
            //nufin
        }
        return null;
    }
}
