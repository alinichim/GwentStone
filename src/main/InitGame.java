package main;

import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;

import fileio.*;

import gamedev.*;

public class InitGame {
	// Arrays with card names.
	static private String[] minions =
	{"Sentinel", "Berseker", "Goliath", "Warden"};
	static private String[] spells =
	{"Firestorm", "Winterfell", "Heart Hound"};

	// Useful array lists.
	private static ArrayList<String> minionNames =
		new ArrayList<String>(Arrays.asList(minions));
	private static ArrayList<String> spellNames =
		new ArrayList<String>(Arrays.asList(spells));

	public InitGame() {}

	// Initialize the game by instantiating the players, decks, and
	// battleground.
	static public void initializeGame(Input in, ArrayNode out, ObjectMapper mapper) {
		// Create the players.
		Player p1 = constructPlayer(in.getPlayerOneDecks());
		Player p2 = constructPlayer(in.getPlayerTwoDecks());

		// Create the battleground.
		BattleGround bg = new BattleGround();
		Player[] players = {p1, p2};
		bg.setPlayers(new ArrayList<Player>(Arrays.asList(players)));
	}

	// Return a fully constructed Player with data from DecksInput.
	static private Player constructPlayer(DecksInput inDecks) {
		ArrayList<Deck> decks = new ArrayList<Deck>();
		for (ArrayList<CardInput> deckIn : inDecks.getDecks()) {
			ArrayList<Card> cards = new ArrayList<Card>();
			for (CardInput i : deckIn) {
				// Get the card's details.
				String name = i.getName();
				String desc = i.getDescription();
				ArrayList<String> colors = i.getColors();
				int mana = i.getMana();
				int health = i.getHealth();
				int atk = i.getAttackDamage();

				Card c = null;

				// Check the type of the card.
				if (minionNames.indexOf(c.getName()) != -1) {
					c = new Minion(name, desc, colors, mana, health, atk);
				} else if (spellNames.indexOf(c.getName()) != -1) {
					c = new SpellCard(name, desc, colors, mana);
				} else {
					c = new SpecialMinion(name, desc, colors, mana, health, atk);
				}

				// Add the card to the list.
				cards.add(c);
			}
			decks.add(new Deck(cards));
		}
		return new Player(decks, new PlayerHUD());
	}
}
