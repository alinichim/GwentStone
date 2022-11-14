package gamedev;

import java.util.ArrayList;

import gamedev.CardCollection;

public class CardCollection {
	private ArrayList<Card> cards = new ArrayList<Card>();

	public CardCollection(ArrayList<Card> cards) {
		for (Card c : cards) this.cards.add(c);
	}

	public CardCollection() {}

	public CardCollection(CardCollection deck) {
		this(deck.getCards());
	}

	// Getters.
	public ArrayList<Card> getCards() { return cards; }
	// Setters.
	public void setCards(ArrayList<Card> cards) {
		this.cards.clear();
		for (Card c : cards) this.cards.add(c);
	}
}
