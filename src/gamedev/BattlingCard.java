package gamedev;

import java.util.ArrayList;

public abstract class BattlingCard extends Card {
    protected int health;
    protected boolean frozen;

    public BattlingCard(
            final String name,
            final String description,
            final ArrayList<String> colors,
            final int mana,
            final int health
    ) {
        super(name, description, colors, mana);
        this.health = health;
        this.frozen = false;
    }

    public BattlingCard() {
        super();
        this.frozen = false;
        this.health = 1;
    }

    /**
     * This method returns the BattlingCard's health points.
     * @return The BattlingCard's health points.
     */
    public int getHealth() {
        return health;
    }

    /**
     * This method sets the BattlingCard's new health points.
     * @param health The BattlingCard's new health points.
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * This method returns the BattlingCard's 'frozen' flag.
     * @return The BattlingCard's 'frozen' flag.
     */
    public boolean getFrozen() {
        return frozen;
    }

    /**
     * This method sets the BattlingCard's 'frozen' flag.
     * @param frozen The BattlingCard's new 'frozen' value.
     */
    public void setFrozen(final boolean frozen) {
        this.frozen = frozen;
    }
}
