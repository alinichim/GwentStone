package gamedev;

import java.util.ArrayList;

import gamedev.Hero;
import gamedev.Minion;
import gamedev.BattleGround;

public class LordRoyce extends Hero {
	public LordRoyce() {}

	// This ability freezes the minion with the most attack damage on row.
	public void useAbility(BattleGround bg, int targetRowIdx) {
		// Get the row of minions.
		ArrayList<Minion> targets = bg.getRows().get(targetRowIdx).getMinions();
		// Find the minion with the most attack damage.
		Minion target = targets.get(0);
		for (Minion m : targets)
			if (m.getAttackDamage() > target.getAttackDamage())
				target = m;
		// Freeze the minion.
		target.setFrozen(true);
	}
}
