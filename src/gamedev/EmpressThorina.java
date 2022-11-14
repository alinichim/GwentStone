package gamedev;

import java.util.ArrayList;

import gamedev.Hero;

public class EmpressThorina extends Hero {
	public EmpressThorina() {
		super();
	}

	// Delets the minion with the most health on row.
	public void useAbility(BattleGround bg, int targetRowIdx) {
		// Fetch the minions.
		ArrayList<Minion> row = bg.getRows().get(targetRowIdx).getMinions();
		Minion targetMinion = row.get(0);
		// Search for the minion with the most health.
		for (Minion m : row)
			if (m.getHealth() > targetMinion.getHealth())
				targetMinion = m;
		// Delete the target minion.
		row.remove(targetMinion);
	}
}
