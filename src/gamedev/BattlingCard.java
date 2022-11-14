package gamedev;

import java.util.ArrayList;

import gamedev.Card;

abstract public class BattlingCard extends Card {
	protected int health;
	protected boolean frozen;

	public BattlingCard(
			String name,
			String description,
			ArrayList<String> colors,
			int mana,
			int health
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

	// Getters.
	public int getHealth() { return health; }
	public boolean getFrozen() { return frozen; } 
	// Setters.
	public void setHealth(int health) { this.health = health; }
	public void setFrozen(boolean frozen) { this.frozen = frozen; }
}
