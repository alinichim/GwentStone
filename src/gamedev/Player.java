package gamedev;

import java.util.ArrayList;

import gamedev.Card;
import gamedev.Deck;
import gamedev.PlayerHUD;

public class Player {
	private ArrayList<Deck> decks;
	private PlayerHUD hud;

	public Player(ArrayList<Deck> decks, PlayerHUD hud) {
		for (Deck deck : decks) this.decks.add(new Deck(deck));
		this.hud = new PlayerHUD(hud);
	}

	public Player() {
		this(new ArrayList<Deck>(), new PlayerHUD());
	}

	public Player(Player p) {
		this(p.getDecks(), p.getPlayerHUD());
	}

	// Getters.
	public ArrayList<Deck> getDecks() { return decks; }
	public PlayerHUD getPlayerHUD() { return hud; }
	// Setters.
	public void setDecks(ArrayList<Deck> decks) {
		this.decks.clear();
		for (Deck deck : decks) this.decks.add(new Deck(deck));
	}
	public void setPlayerHUD(PlayerHUD hud) {
		this.hud = new PlayerHUD(hud);
	}
}
