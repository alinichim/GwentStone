package gamedev;

import java.util.ArrayList;

import gamedev.SpellCard;
import gamedev.BattleGround;
import gamedev.Minion;

public class Winterfell extends SpellCard {
	public Winterfell(
			String name,
			String description,
			ArrayList<String> colors,
			int mana
			) {
		super(name, description, colors, mana);
	}

	// This ability freezes all the minions on a row.
	public void useSpell(BattleGround bg, int targetRowIdx) {
		// Get the row of minions.
		ArrayList<Minion> targets = bg.getRows().get(targetRowIdx).getMinions();
		// Freeze minions.
		for (Minion target : targets)
			target.setFrozen(true);
	}
}
