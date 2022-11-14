package gamedev;

import java.util.ArrayList;

import gamedev.SpecialMinion;
import gamedev.Minion;

public class TheRipper extends SpecialMinion {
	public TheRipper(
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
