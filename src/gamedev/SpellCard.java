package gamedev;

import java.util.ArrayList;

import gamedev.Card;
import gamedev.BattleGround;

interface Spell {
	void useSpell(BattleGround bg, int targetRowIdx);
}

public class SpellCard extends Card {
	private Spell spell;

	public SpellCard(
			String name,
			String description,
			ArrayList<String> colors,
			int mana
			) {
		super(name, description, colors, mana);
		this.type = 2;

		switch (name) {
			case "Firestorm":
				{
					// This spell decreses the health of all minions on target
					// row by 1.
					spell = (BattleGround bg, int targetRowIdx) -> {
						// Get the row.
						Row r = bg.getRows().get(targetRowIdx);
						// Deal damage to minions.
						for (Minion m : r.getMinions())
							m.setHealth(m.getHealth() - 1);
					};
					break;
				}
			case "Winterfell":
				{
					// This spell freezes all minions on a row.
					spell = (BattleGround bg, int targetRowIdx) -> {
						// Get the row.
						Row r = bg.getRows().get(targetRowIdx);
						// Freeze minions.
						for (Minion m : r.getMinions())
							m.setFrozen(true);
					};
					break;
				}
			case "Heart Hound":
				{
					// This spell steals the minion with the most health.
					spell = (BattleGround bg, int targetRowIdx) -> {
						// Get the row.
						Row r = bg.getRows().get(targetRowIdx);
						// Find the minion with the most health.
						Minion target = r.getMinions().get(0);
						for (Minion m : r.getMinions())
							if (m.getHealth() > target.getHealth())
								target = m;
						// Steal the target minion.
						r.getMinions().remove(target);
						r = bg.getRows().get(3 - targetRowIdx);
						r.getMinions().add(target);
					};
					break;
				}
		}

	}

	public void useSpell(BattleGround bg, int targetRowIdx) {
		spell.useSpell(bg, targetRowIdx);
	}
}
