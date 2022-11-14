package gamedev;

import java.util.ArrayList;

import gamedev.Hero;

public class KingMudface extends Hero {
	public KingMudface() {}

	// This ability adds 1 health to every minion on target row.
	public void useAbility(BattleGround bg, int targetRowIdx) {
		// Get the row of minions.
		ArrayList<Minion> targets = bg.getRows().get(targetRowIdx).getMinions();
		// Increment minions health.
		for (Minion m : targets) m.setHealth(m.getHealth() + 1);
	}
}
