package gamedev;

import java.util.ArrayList;

import gamedev.Card;
import gamedev.Deck;
import gamedev.PlayerHUD;

public class Player {
	private ArrayList<Deck> decks;
	private PlayerHUD hud;
	private int wins = 0;

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
	public int getWins() { return wins; }
	// Setters.
	public void setDecks(ArrayList<Deck> decks) {
		this.decks.clear();
		for (Deck deck : decks) this.decks.add(new Deck(deck));
	}
	public void setPlayerHUD(PlayerHUD hud) {
		this.hud = new PlayerHUD(hud);
	}
	public void setWins(int wins) { this.wins = wins; }
}
