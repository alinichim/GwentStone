package gamedev;

import java.util.ArrayList;

public final class Row extends CardCollection {
    private final ArrayList<Minion> minions = new ArrayList<>();
    public static final int MAX_SIZE = 5;

    public Row(final ArrayList<Minion> minions) {
        for (Minion m : minions) {
            this.minions.add(new Minion(m));
        }
    }

    public Row() {
    }

    public Row(final Row r) {
        this(r.getMinions());
    }

    /**
     * This method returns the Row's minions.
     * @return The Row's minions.
     */
    public ArrayList<Minion> getMinions() {
        return minions;
    }

    /**
     * This method sets the Row's new minions.
     * @param minions The Row's new minions.
     */
    public void setMinions(final ArrayList<Minion> minions) {
        this.minions.clear();
        for (Minion m : minions) {
            this.minions.add(new Minion(m));
        }
    }
}
