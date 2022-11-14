package gamedev;

import java.util.ArrayList;

import gamedev.SpellCard;

public class HeartHound extends SpellCard {
	public HeartHound(
			String name,
			String description,
			ArrayList<String> colors,
			int mana
			) {
		super(name, description, colors, mana);
	}

	// This ability extracts the minion with the most health from the enemy row
	// and inserts it on the mirrored allied row.
	public void useSpell(BattleGround bg, int targetRowIdx) {
		// Get the list of minions.
		ArrayList<Minion> targets = bg.getRows().get(targetRowIdx).getMinions();
		// Extract the minion with the most health.
		Minion target = targets.get(0);
		for (Minion m : targets)
			if (m.getHealth() > target.getHealth())
				target = m;
		targets.remove(target);
		// Get the list of minions of the mirrored row and insert the target
		// minion.
		targets = bg.getRows().get(3 - targetRowIdx).getMinions();
		targets.add(target);
	}
}
