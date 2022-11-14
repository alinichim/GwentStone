package gamedev;

import java.util.ArrayList;

import gamedev.CardCollection;
import gamedev.Minion;

public class Row extends CardCollection {
	private ArrayList<Minion> minions = new ArrayList<Minion>();
	private int maxMinions = 5;

	public Row(ArrayList<Minion> minions) {
		for (Minion m : minions) this.minions.add(new Minion(m));
	}

	public Row() {}

	public Row(Row r) {
		this(r.getMinions());
	}

	// Getters.
	public ArrayList<Minion> getMinions() { return minions; }
	public int getMaxMinions() { return maxMinions; }
	// Setters.
	public void setMinions(ArrayList<Minion> minions) {
		this.minions.clear();
		for (Minion m : minions) this.minions.add(new Minion(m));
	}
	public void setMaxMinions(int max) { this.maxMinions = max; }
}
