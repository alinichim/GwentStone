package gamedev;

import java.util.ArrayList;

import gamedev.BattlingCard;
import gamedev.BattleGround;

interface heroAbility {
	void useAbility(BattleGround bg, int targetRowIdx);
}

public class Hero extends BattlingCard {
	private heroAbility ability;

	public Hero(
			String name,
			String description,
			ArrayList<String> colors,
			int mana
			) {
		super(name, description, colors, mana, 30);
		this.type = 3;

		switch (name) {
			case "Lord Royce":
				{
					// This ability freezes the minion with the most attack damage on row.
					ability = (BattleGround bg, int targetRowIdx) -> {
						// Get the row of minions.
						ArrayList<Minion> targets = bg.getRows().get(targetRowIdx).getMinions();
						// Find the minion with the most attack damage.
						Minion target = targets.get(0);
						for (Minion m : targets)
							if (m.getAttackDamage() > target.getAttackDamage())
								target = m;
						// Freeze the minion.
						target.setFrozen(true);
					};
					break;
				}
			case "Empress Thorina":
				{
					// Delets the minion with the most health on row.
					ability = (BattleGround bg, int targetRowIdx) -> {
						// Fetch the minions.
						ArrayList<Minion> row = bg.getRows().get(targetRowIdx).getMinions();
						Minion targetMinion = row.get(0);
						// Search for the minion with the most health.
						for (Minion m : row)
							if (m.getHealth() > targetMinion.getHealth())
								targetMinion = m;
						// Delete the target minion.
						row.remove(targetMinion);
					};
					break;
				}
			case "King Mudface":
				{
					// This ability adds 1 health to every minion on target row.
					ability = (BattleGround bg, int targetRowIdx) -> {
						// Get the row of minions.
						ArrayList<Minion> targets = bg.getRows().get(targetRowIdx).getMinions();
						// Increment minions health.
						for (Minion m : targets) m.setHealth(m.getHealth() + 1);
					};
					break;
				}
			case "General Kocioraw":
				{
					// This ability adds 1 attack damage to all minions on target row.
					ability = (BattleGround bg, int targetRowIdx) -> {
						// Get the row of minions.
						ArrayList<Minion> targets = bg.getRows().get(targetRowIdx).getMinions();
						// Increment minions attack damage.
						for (Minion target : targets)
							target.setAttackDamage(target.getAttackDamage() + 1);
					};
					break;
				}
		}
	}

	public Hero() {
		super();
		this.health = 30;
		this.type = 3;
	}

	public Hero(Hero h) {
		this(
				h.getName(),
				h.getDescription(),
				h.getColors(),
				h.getMana()
			 );
	}

	public void useAbility(BattleGround bg, int targetRowIdx) {
		ability.useAbility(bg, targetRowIdx);
	}
}
