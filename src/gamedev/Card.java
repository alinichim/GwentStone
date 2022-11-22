package gamedev;

import java.util.ArrayList;

public abstract class Card {
    protected String name, description;
    protected ArrayList<String> colors = new ArrayList<>();
    protected int mana;
    // type has this format:
    // 0 - minion
    // 1 - special minion
    // 2 - environment
    // 3 - hero
    protected int type;
    protected boolean attacked = false;
    public static final int MINION_TYPE = 0, SPECIAL_MINION_TYPE = 1, SPELL_TYPE = 2, HERO_TYPE = 3;

    public Card(
            final String name,
            final String description,
            final ArrayList<String> colors,
            final int mana
    ) {
        this.name = name;
        this.description = description;
        this.mana = mana;
        this.colors.addAll(colors);
    }

    public Card() {
    }

    public Card(final Card c) {
        this(c.getName(), c.getDescription(), c.getColors(), c.getMana());
        this.type = c.getType();
        this.attacked = c.getAttacked();
    }

    /**
     * This method returns the 'attacked' token of the Card.
     * @return The boolean value of the 'attacked' token.
     */
    public boolean getAttacked() {
        return attacked;
    }

    /**
     * This method sets the 'attacked' token of the Card.
     * @param attacked The new value of the 'attacked' token.
     */
    public void setAttacked(final boolean attacked) {
        this.attacked = attacked;
    }

    /**
     * This method returns the name of the Card.
     * @return The name of the Card.
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets the name of the Card.
     * @param name The new name of the Card.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * This method returns the description of the Card.
     * @return The description of the Card.
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method sets the description of the Card.
     * @param desc The new description of the Card.
     */
    public void setDescription(final String desc) {
        this.description = desc;
    }

    /**
     * This method returns the Card's list of colors.
     * @return The Card's list of colors.
     */
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * This method sets the Card's list of colors.
     * @param colors The Card's new list of colors.
     */
    public void setColors(final ArrayList<String> colors) {
        this.colors.clear();
        this.colors.addAll(colors);
    }

    /**
     * This method returns the Card's mana cost.
     * @return The Card's mana cost.
     */
    public int getMana() {
        return mana;
    }

    /**
     * This method sets the Card's mana cost.
     * @param mana The Card's new mana cost.
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * This method returns the Card's type.
     * @return The Card's type.
     */
    public int getType() {
        return type;
    }

    /**
     * This method sets the Card's type.
     * @param type The Card's new type.
     */
    public void setType(final int type) {
        this.type = type;
    }
}
