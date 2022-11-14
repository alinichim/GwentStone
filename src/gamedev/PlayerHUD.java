package gamedev;

import gamedev.Deck;
import gamedev.Hero;

public class PlayerHUD {
	private int mana;
	private Deck deck;
	private CardCollection hand;
	private Hero hero;

	public PlayerHUD(Deck deck, CardCollection hand, int mana) {
		this.deck = new Deck(deck);
		this.hand = new CardCollection(hand);
		this.mana = mana;
	}

	public PlayerHUD() {
		this.deck = new Deck();
		this.hand = new CardCollection();
		this.mana = 0;
	}

	public PlayerHUD(PlayerHUD hud) {
		this(hud.getDeck(), hud.getHand(), hud.getMana());
	}

	// Getters.
	public Deck getDeck() { return deck; }
	public CardCollection getHand() { return hand; }
	public int getMana() { return mana; }
	public Hero getHero() { return hero; }
	// Setters.
	public void setDeck(Deck deck) { this.deck = new Deck(deck); }
	public void setHand(CardCollection hand) { this.hand = new CardCollection(hand); }
	public void setMana(int mana) { this.mana = mana; }
	public void setHero(Hero hero) { this.hero = hero; }
}
