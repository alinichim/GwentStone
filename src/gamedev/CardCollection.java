package gamedev;

import java.util.ArrayList;

public class CardCollection {
    protected ArrayList<Card> cards = new ArrayList<>();

    public CardCollection(final ArrayList<Card> cards) {
        this.cards.addAll(cards);
    }

    public CardCollection() {
    }

    public CardCollection(final CardCollection deck) {
        this(deck.getCards());
    }

    /**
     * This method returns the CardCollection's cards.
     * @return The CardCollection's cards.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * This method sets the CardCollection's new cards.
     * @param cards The CardCollection's new cards.
     */
    public void setCards(final ArrayList<Card> cards) {
        this.cards.clear();
        this.cards.addAll(cards);
    }
}
