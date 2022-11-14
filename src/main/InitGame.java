package main;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Input;
import fileio.DecksInput;
import fileio.CardInput;

import gamedev.Player;
import gamedev.PlayerHUD;
import gamedev.BattleGround;
import gamedev.Card;
import gamedev.Deck;

// TODO: Implement game iteration.
// TODO: Refactor
public class InitGame {
	static private ArrayList<String> minionNames = {"Sentinel", "Berseker", "Goliath", "Warden"};
	static private ArrayList<String> specialMinionNames = {"The Ripper", "Miraj", "The Cursed One", "Disciple"};
	static private Arraylist<String> spellNames = {"Firestorm", "Winterfell", "Heart Hound"};
	static private ArrayList<String> heroNames = {"Lord Royce", "Empress Thorina", "King Mudface", "General Kocioraw"};

	public InitGame() {}

	// Initialize the game by instantiating the players, decks, and
	// battleground.
	static public void initializeGame(Input in, ArrayNode out) {
		// Create the players.
		Player p1 = constructPlayer(in.getPlayerOneDecks());
		Player p2 = constructPlayer(in.getPlayerTwoDecks());

		// Create the battleground.
		BattleGround bg = new BattleGround();
		bg.setPlayers(new ArrayList<Player>({p1, p2}));
	}

	// Return a fully constructed Player with data from DecksInput.
	static private Player constructPlayer(DecksInput inDecks) {
		ArrayList<Deck> decks = new ArrayList<Deck>();
		for (ArrayList<CardInput> deck : inDecks.getDecks()) {
			ArrayList<Card> cards = new ArrayList<Card>();
			for (CardInput c : deck) {
				if (minionNames.indexOf(c.getName()) != -1) {
					cards.add(new Minion(
								c.getName(),
								c.getDescription(),
								c.getColors(),
								c.getMana(),
								c.getHealth(),
								c.getAttackDamage()
								));
				} else if (specialMinionNames.indexOf(c.getName()) != -1) {
					addAppropiateSpecialMinion(cards, c);
				} else {
					addAppropiateSpellCard(cards, c);
				}
			}
			decks.add(new Deck(cards));
		}
		return new Player(decks, new PlayerHUD());
	}

	static private void addAppropiateSpecialMinion(ArrayList<Card> cards, CardInput c) {
		if (c.getName().equals("The Ripper")) {
			cards.add(new TheRipper(
						c.getName(),
						c.getDescription(),
						c.getColors(),
						c.getMana(),
						c.getHealth(),
						c.getAttackDamage()
						));
		} else if (c.getName().equals("Miraj")) {
			cards.add(new Miraj(
						c.getName(),
						c.getDescription(),
						c.getColors(),
						c.getMana(),
						c.getHealth(),
						c.getAttackDamage()
						));
		} else if (c.getName().equals("The Cursed One")) {
			cards.add(new TheCursedOne(
						c.getName(),
						c.getDescription(),
						c.getColors(),
						c.getMana(),
						c.getHealth(),
						c.getAttackDamage()
						));
		} else if (c.getName().equals("Disciple")) {
			cards.add(new Disciple(
						c.getName(),
						c.getDescription(),
						c.getColors(),
						c.getMana(),
						c.getHealth(),
						c.getAttackDamage()
						));
		}
	}

	static private void addAppropiateSpellCard(ArrayList<Card> cards, CardInput c) {
		if (c.getName().equals("Firestorm")) {
			cards.add(new Firestorm(
						c.getName(),
						c.getDescription(),
						c.getColors(),
						c.getMana()
						));
		} else if (c.getName().equals("Winterfell")) {
			cards.add(new Winterfell(
						c.getName(),
						c.getDescription(),
						c.getColors(),
						c.getMana()
						));
		} else if (c.getName().equals("Heart Hound")) {
			cards.add(new HeartHound(
						c.getName(),
						c.getDescription(),
						c.getColors(),
						c.getMana()
						));
		}
	}
