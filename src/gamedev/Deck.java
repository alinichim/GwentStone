package gamedev;

import java.util.ArrayList;

import gamedev.Card;

public class Deck extends CardCollection {
	private ArrayList<Card> cards = new ArrayList<Card>();

	public Deck(ArrayList<Card> cards) {
		super(cards);
	}

	public Deck() {}

	public Deck(Deck deck) {
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
