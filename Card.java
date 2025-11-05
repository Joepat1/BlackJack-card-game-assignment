// Card.java
// Card class represents a playing card

public class Card {
    private final String face; // face o card ("Ace, "Duece", ...)
    private final String suit; // suit of card ("Hearts", "Diamongs", etc.)

    // two-argument constructor initializes card's face and suit
    public Card(String cardFace, String cardSuit) {
        this.face = cardFace; // initialize face of card
        this.suit = cardSuit; // initialize suit of card
    }

    //return String representation of card
    public String toString() {
        return face + " of " + suit;
    } // end of toString method
} // end class Card