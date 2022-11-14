package gamedev;

import java.util.ArrayList;

import gamedev.SpecialMinion;
import gamedev.Minion;

public class Miraj extends SpecialMinion {
	public Miraj(
			String name,
			String description,
			ArrayList<String> colors,
			int mana,
			int health,
			int attackDamage
			) {
		super(name, description, colors, mana, health, attackDamage);
	}

	public void useAbility(Minion target) {
		target.setAttackDamage(target.getAttackDamage() - 2);
	}
}
