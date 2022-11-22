package gamedev;

import fileio.CardInput;

import java.util.ArrayList;

public final class PlayerHUD {
    private int mana;
    private CardCollection deck, hand;
    private Hero hero;

    public PlayerHUD(final CardCollection deck, final CardCollection hand, final int mana) {
        this.deck = new CardCollection(deck);
        this.hand = new CardCollection(hand);
        this.mana = mana;
    }

    public PlayerHUD() {
        this.deck = new CardCollection();
        this.hand = new CardCollection();
        this.mana = 0;
    }

    public PlayerHUD(final PlayerHUD hud) {
        this(hud.getDeck(), hud.getHand(), hud.getMana());
    }

    /**
     * This method returns the PlayerHUD's deck.
     * @return The PlayerHUD's deck.
     */
    public CardCollection getDeck() {
        return deck;
    }

    /**
     * This method sets the PlayerHUD's new deck.
     * @param deck The PlayerHUD's new deck.
     */
    public void setDeck(final CardCollection deck) {
        this.deck = new CardCollection(deck);
    }

    /**
     * This method returns the PlayerHUD's hand.
     * @return The PlayerHUD's hand.
     */
    public CardCollection getHand() {
        return hand;
    }

    /**
     * This method sets the PlayerHUD's new hand.
     * @param hand The PlayerHUD's new hand.
     */
    public void setHand(final CardCollection hand) {
        this.hand = new CardCollection(hand);
    }

    /**
     * This method returns the PlayerHUD's mana.
     * @return The PlayerHUD's mana.
     */
    public int getMana() {
        return mana;
    }

    /**
     * This method sets the PlayerHUD's new mana.
     * @param mana The PlayerHUD's new mana.
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * This method returns the PlayerHUD's hero.
     * @return The PlayerHUD's hero.
     */
    public Hero getHero() {
        return hero;
    }

    /**
     * This method sets the PlayerHUD's new hero.
     * @param hero The PlayerHUD's new hero.
     */
    public void setHero(final Hero hero) {
        this.hero = new Hero(hero);
    }

    /**
     * This method sets the PlayerHUD's new hero from a CardInput.
     * @param c The PlayerHUD's new hero.
     */
    public void setHero(final CardInput c) {
        String name = c.getName();
        String description = c.getDescription();
        ArrayList<String> colors = c.getColors();
        int cardMana = c.getMana();
        this.hero = new Hero(name, description, colors, cardMana);
    }
}
