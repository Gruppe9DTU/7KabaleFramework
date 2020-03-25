public class Card {
    private int suit;
    private int value;

    /***
     * Constructor for the Card object. A playing card.
     *
     * @param suit a numerical value of suit, 0 to 3
     * @param value a numerical value of the card from 1 to 13.
     */
    public Card (int suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public int getSuit() {
        return this.suit;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /***
     * A method to get the suit in string form
     *
     * @return A string explaining the suit, depending on the value of the int
     */
    public String readSuit() {
        switch(suit) {
            case 0:
                return "Hearts";
            case 1:
                return "Spades";
            case 2:
                return "Diamonds";
            case 3:
                return "Clubs";
            default:
                return "error reading suit, please make sure card is correct.";
        }
    }

    /***
     * A method to convert a card to a string
     *
     * @return A string explaining the card
     */
    public String toString() {
        switch(value){
            case 1:
                return "Ace of " + readSuit();
            case 11:
                return "Jack of " + readSuit();
            case 12:
                return "Queen of " + readSuit();
            case 13:
                return "King of " + readSuit();
            default:
                return value + " of " + readSuit();
        }
    }
}
