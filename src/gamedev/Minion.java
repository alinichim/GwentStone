package gamedev;

import java.util.ArrayList;

import gamedev.BattlingCard;

public class Minion extends BattlingCard {
	protected int attackDamage;

	public Minion(
			String name,
			String description,
			ArrayList<String> colors,
			int mana,
			int health,
			int attackDamage
			) {
		super(name, description, colors, mana, health);
		this.attackDamage = attackDamage;
		this.type = 0;
	}

	public Minion(Minion m) {
		this(
				m.getName(),
				m.getDescription(),
				m.getColors(),
				m.getMana(),
				m.getHealth(),
				m.getAttackDamage()
			);
	}

	public void attackCommand(BattlingCard target) {
		target.setHealth(target.getHealth() - this.attackDamage);
	}

	// Getters.
	public int getAttackDamage() { return attackDamage; }
	// Setters.
	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}
}
