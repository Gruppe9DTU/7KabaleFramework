import java.util.ArrayList;
import java.util.List;

public class Foundation {
    private List<Card> cards = new ArrayList<Card>();

    public void addCard(Card card) {
        if (cards.size() > 0) {
            Card lastcard = peekCard();
            if(card.getSuit() != lastcard.getSuit() || card.getValue() != (lastcard.getValue() + 1))
                System.out.println("Cannot add card to deck");
            else
                cards.add(card);
        }
        else {
            if(card.getValue() != 1)
                System.out.println("Cannot add non-aces to empty foundation");
            else
                cards.add(card);
        }
    }

    public Card takeCard() { return cards.remove(cards.size()-1); }

    public Card peekCard() { return cards.get(cards.size()-1); }

    public int countCards() { return cards.size(); }

    public Boolean isComplete() {
        Card lastcard = peekCard();
        if (lastcard.getValue() == 13 && countCards() == 13)
            return true;
        else
            return false;
    }


}
