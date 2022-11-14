package gamedev;

import java.util.ArrayList;

import gamedev.SpecialMinion;
import gamedev.Minion;

public class TheCursedOne extends SpecialMinion {
	public TheCursedOne(
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
		int currentAttack = target.getAttackDamage();
		target.setAttackDamage(target.getHealth());
		target.setHealth(currentAttack);
	}
}
