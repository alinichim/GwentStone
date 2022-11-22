package main;

import fileio.CardInput;
import fileio.DecksInput;
import fileio.Input;
import gamedev.BattleGround;
import gamedev.CardCollection;
import gamedev.Minion;
import gamedev.Player;
import gamedev.Card;
import gamedev.PlayerHUD;
import gamedev.SpellCard;
import gamedev.SpecialMinion;

import java.util.ArrayList;
import java.util.Arrays;

public final class InitGame {
    // Arrays with card names.
    private static final String[] MINIONS =
            {"Sentinel", "Berseker", "Goliath", "Warden"};
    private static final String[] SPELLS =
            {"Firestorm", "Winterfell", "Heart Hound"};

    // Useful array lists.
    private static final ArrayList<String> MINION_NAMES =
            new ArrayList<>(Arrays.asList(MINIONS));
    private static final ArrayList<String> SPELL_NAMES =
            new ArrayList<>(Arrays.asList(SPELLS));

    private InitGame() { }

    /**
     * This method initializes the game by instantiating the players, decks, and battleground.
     * @param in The Input.
     * @return The BattleGround.
     */
    public static BattleGround initializeGame(final Input in) {
        // Create the players.
        Player p1 = constructPlayer(in.getPlayerOneDecks());
        Player p2 = constructPlayer(in.getPlayerTwoDecks());

        // Create the battleground.
        BattleGround bg = new BattleGround();
        Player[] players = {p1, p2};
        bg.setPlayers(new ArrayList<>(Arrays.asList(players)));

        return bg;
    }

    /**
     * This method returns a fully constructed Player with data from DecksInput.
     * @param inDecks The DecksInput.
     * @return The Player.
     */
    private static Player constructPlayer(final DecksInput inDecks) {
        ArrayList<CardCollection> decks = new ArrayList<>();
        for (ArrayList<CardInput> deckIn : inDecks.getDecks()) {
            ArrayList<Card> cards = new ArrayList<>();
            for (CardInput i : deckIn) {
                // Get the card's details.
                String name = i.getName();
                String desc = i.getDescription();
                ArrayList<String> colors = i.getColors();
                int mana = i.getMana();
                int health = i.getHealth();
                int atk = i.getAttackDamage();

                Card c;

                // Check the type of the card.
                if (MINION_NAMES.contains(name)) {
                    c = new Minion(name, desc, colors, mana, health, atk);
                } else if (SPELL_NAMES.contains(name)) {
                    c = new SpellCard(name, desc, colors, mana);
                } else {
                    c = new SpecialMinion(name, desc, colors, mana, health, atk);
                }

                // Add the card to the list.
                cards.add(c);
            }
            decks.add(new CardCollection(cards));
        }
        return new Player(decks, new PlayerHUD());
    }
}
