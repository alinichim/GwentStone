package gamedev;

import java.util.ArrayList;

public final class Player {
    private final ArrayList<CardCollection> decks = new ArrayList<>();
    private PlayerHUD hud;
    private int wins = 0;

    public Player(final ArrayList<CardCollection> decks, final PlayerHUD hud) {
        for (CardCollection deck : decks) {
            this.decks.add(new CardCollection(deck));
        }
        this.hud = new PlayerHUD(hud);
    }

    public Player() {
        this(new ArrayList<CardCollection>(), new PlayerHUD());
    }

    public Player(final Player p) {
        this(p.getDecks(), p.getPlayerHUD());
    }

    /**
     * This method returns the Player's decks.
     * @return The Player's decks.
     */
    public ArrayList<CardCollection> getDecks() {
        return decks;
    }

    /**
     * This method sets the Player's decks.
     * @param decks The Player's new decks.
     */
    public void setDecks(final ArrayList<CardCollection> decks) {
        this.decks.clear();
        for (CardCollection deck : decks) {
            this.decks.add(new CardCollection(deck));
        }
    }

    /**
     * This method returns the Player's HUD.
     * @return The Player's HUD.
     */
    public PlayerHUD getPlayerHUD() {
        return hud;
    }

    /**
     * This method sets the Player's HUD.
     * @param playerHUD The Player's new HUD.
     */
    public void setPlayerHUD(final PlayerHUD playerHUD) {
        this.hud = new PlayerHUD(playerHUD);
    }

    /**
     * This method returns the Player's wins.
     * @return The Player's wins.
     */
    public int getWins() {
        return wins;
    }

    /**
     * This method sets the Player's wins.
     * @param wins The Player's new wins.
     */
    public void setWins(final int wins) {
        this.wins = wins;
    }
}
