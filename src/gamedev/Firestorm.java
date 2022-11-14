package gamedev;

import java.util.ArrayList;

import gamedev.SpellCard;
import gamedev.BattleGround;
import gamedev.Minion;

public class Firestorm extends SpellCard {
	public Firestorm(
			String name,
			String description,
			ArrayList<String> colors,
			int mana
			) {
		super(name, description, colors, mana);
	}

	// This ability deals 1 damage to all minions on a row.
	public void useSpell(BattleGround bg, int targetRowIdx) {
		// Get the row of minions.
		ArrayList<Minion> targets = bg.getRows().get(targetRowIdx).getMinions();
		// Deal 1 damage to minions.
		for (Minion m : targets) m.setHealth(m.getHealth() - 1);
	}
}
