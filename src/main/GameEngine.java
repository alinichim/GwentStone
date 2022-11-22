package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Input;
import fileio.GameInput;
import fileio.StartGameInput;
import fileio.CardInput;
import fileio.ActionsInput;
import gamedev.BattleGround;
import gamedev.Player;
import gamedev.PlayerHUD;
import gamedev.Row;
import gamedev.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class GameEngine {
    private GameEngine() { }

    /**
     * This method iterates through games.
     * @param in The Input.
     * @param bg The BattleGround.
     * @param out The out Array Node with the resulted state.
     */
    public static void iterateGames(final Input in, final BattleGround bg, final ArrayNode out) {
        // Get the games list.
        ArrayList<GameInput> gamesIn = in.getGames();

        // Iterate.
        for (GameInput game : gamesIn) {
            for (Player p : bg.getPlayers()) {
                p.setPlayerHUD(new PlayerHUD());
            }
            for (Row r : bg.getRows()) {
                r.getMinions().clear();
            }

            // Run the game.
            runGame(game, bg, out);
        }
    }

    /**
     * This method runs a game.
     * @param game The GameInput with the necessary data to initialize a game.
     * @param bg The BattleGround.
     * @param out The out Array Node with the resulted state.
     */
    private static void runGame(final GameInput game, final BattleGround bg, final ArrayNode out) {
        // Get the start game data.
        StartGameInput startIn = game.getStartGame();

        // Set up the game.
        Random rand = new Random(startIn.getShuffleSeed());
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
        ArrayList<Card> cards1 = hud1.getDeck().getCards();
        ArrayList<Card> cards2 = hud2.getDeck().getCards();
        Collections.shuffle(cards1, rand);
        rand = new Random(startIn.getShuffleSeed());
        Collections.shuffle(cards2, rand);
        // Set players heroes.
        CardInput c1 = startIn.getPlayerOneHero();
        CardInput c2 = startIn.getPlayerTwoHero();
        hud1.setHero(c1);
        hud2.setHero(c2);
        // Set the player turn.
        bg.setPlayerTurn(startIn.getStartingPlayer() - 1);
        // Set turns.
        bg.setTurns(1);
        // Draw one card for each player.
        hud1.getHand().getCards().add(hud1.getDeck().getCards().remove(0));
        hud2.getHand().getCards().add(hud2.getDeck().getCards().remove(0));
        // Set players mana.
        hud1.setMana(1);
        hud2.setMana(1);

        // Get the actions.
        ArrayList<ActionsInput> actions = game.getActions();

        // Set the end game to false.
        Controller.setEndGame(false);

        // Execute actions.
        for (ActionsInput action : actions) {
            // Call the Controller to take care of the command.
            Controller.executeCommand(bg, action, out);
        }
    }
}
