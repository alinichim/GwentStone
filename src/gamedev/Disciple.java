package gamedev;

import java.util.ArrayList;

import gamedev.SpecialMinion;
import gamedev.Minion;

public class Disciple extends SpecialMinion {
	public Disciple(
			String name,
			String description,
			ArrayList<String> colors,
			int mana,
			int health,
			int attackDamage
			) {
		super(name, description, colors, mana, health, attackDamage);
		this.attackDamage = 0;
	}

	public void useAbility(Minion target) {
		target.setHealth(target.getHealth() + 2);
	}
}
