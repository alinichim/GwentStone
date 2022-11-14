package gamedev;

import java.util.ArrayList;

abstract public class Card {
	protected String name, description;
	protected ArrayList<String> colors = new ArrayList<String>();
	protected int mana;
	// type has this format:
	// 0 - minion
	// 1 - special minion
	// 2 - environment
	// 3 - hero
	protected int type;
	protected boolean attacked;

	public Card(String name, String description, ArrayList<String> colors, int mana) {
		this.name = new String(name);
		this.description = new String(description);
		this.mana = mana;
		for (String s : colors) this.colors.add(new String(s));
	}

	public Card() {}

	public Card(Card c) {
		this(c.getName(), c.getDescription(), c.getColors(), c.getMana());
	}

	// Getters.
	public boolean getAttacked() { return attacked; }
	public String getName() { return name; }
	public String getDescription() { return description; }
	public ArrayList<String> getColors() { return colors; }
	public int getMana() { return mana; }
	public int getType() { return type; }
	// Setters.
	public void setAttacked(boolean attacked) { this.attacked = attacked; }
	public void setType(int type) { this.type = type; }
	public void setName(String name) { this.name = new String(name); }
	public void setDescription(String desc) { this.description = new String(desc); }
	public void setColors(ArrayList<String> colors) {
		this.colors.clear();
		for (String s : colors) this.colors.add(new String(s));
	}
	public void setMana(int mana) { this.mana = mana; }
}
