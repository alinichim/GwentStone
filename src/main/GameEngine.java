package main;

import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import gamedev.BattleGround;

public class GameEngine {
	private static Random rand;

	public GameEngine();

	// Iterate through games.
	public static void interateGames(Input in, BattleGround bg, ArrayNode out) {
		// Get the games list.
		ArrayList<GameInput> gamesIn = in.getGames();

		// Iterate.
		for (Gameinput game : gamesIn) {
			// Run the game.
			runGame(game, bg, out);
		}
	}

	// The games runs here.
	private static void runGame(GameInput game, BattleGround bg, ArrayNode out) {
		// Get the start game data.
		StartGameInput startIn = game.getStartGame();

		// Setup the game.
		rand = new Random(startIn.getShuffleSeed());
		// Get players deck indexes.
		int d1 = startIn.getPlayerOneDeckIdx();
		int d2 = startIn.getPlayerTwoDeckIdx();
		// Get players.
		Player p1 = bg.getPlayers().get(0);
		Player p2 = bg.getPlayers().get(1);
		// Get players HUD.
		PlayerHUD hud1 = p1.getPlayerHUD();
		PlayerHUD hud2 = p2.getPlayerHUD();
		// Add the deck to HUD.
		hud1.setDeck(p1.getDecks().get(d1));
		hud2.setDeck(p2.getDecks().get(d2));
		// Shuffle decks.
		Random.shuffle(hud1.getDeck(), rand);
		Random.shuffle(hud2.getDeck(), rand);
		// Set players heroes.
		CardInput c1 = startIn.getPlayerOneHero();
		CardInput c2 = startIn.getPlayerTwoHero();
		hud1.setHero(
				new Hero(
					c1.getName(),
					c1.getDescription(),
					c1.getColors(),
					c1.getMana(),
					c1.getHealth()
					)
				);
		hud2.setHero(
				new Hero(
					c2.getName(),
					c2.getDescription(),
					c2.getColors(),
					c2.getMana(),
					c2.getHealth()
					)
				);
		// Set the player turn.
		bg.setPlayerTurn(startIn.getStartingPlayer());
		// Set turns.
		bg.setTurns(0);

		// Get the actions.
		ArrayList<ActionsInput> actions = startIn.getActions();

		// Execute actions.
		for (ActionsInput action : actions) {
			// Call the Controller to take care of the command.
			ObjectNode node = Controller.executeCommand(bg, action);

			// Add the output to ArrayNode.
			out.addAll(node);
		}
	}
}
