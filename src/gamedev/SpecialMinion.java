package gamedev;

import java.util.ArrayList;

import gamedev.Minion;

abstract public class SpecialMinion extends Minion {
	public SpecialMinion(
			String name,
			String description,
			ArrayList<String> colors,
			int mana,
			int health,
			int attackDamage
			) {
		super(name, description, colors, mana, health, attackDamage);
		this.type = 1;
	}

	public SpecialMinion(SpecialMinion m) {
		this(
				m.getName(),
				m.getDescription(),
				m.getColors(),
				m.getMana(),
				m.getHealth(),
				m.getAttackDamage()
			);
	}

	abstract public void useAbility(Minion m);
}
