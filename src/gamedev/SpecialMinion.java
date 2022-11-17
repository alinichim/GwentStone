package gamedev;

import java.util.ArrayList;

import gamedev.Minion;

interface minionAbility {
	void useAbility(Minion target);
}

public class SpecialMinion extends Minion {
	private minionAbility ability;

	public SpecialMinion(
			String name,
			String description,
			ArrayList<String> colors,
			int mana,
			int health,
			int attackDamage
			) {
		super(name, description, colors, mana, health, attackDamage);
		this.type = 1;

		switch (name) {
			case "The Ripper":
				{
					// This ability decreases minion's attack damage by 2.
					ability = (Minion target) -> {
						target.setAttackDamage(target.getAttackDamage() - 2);
					};
					break;
				}
			case "Miraj":
				{
					// This ability swaps the target minion's health with the
					// current minion's health.
					ability = (Minion target) -> {
						int h = target.getHealth();
						target.setHealth(this.getHealth());
						this.setHealth(h);
					};
					break;
				}
			case "The Cursed One":
				{
					// This ability swaps the target minion's attack damage and
					// health.
					ability = (Minion target) -> {
						int a = target.getAttackDamage();
						target.setAttackDamage(target.getHealth());
						target.setHealth(a);
					};
					break;
				}
			case "Disciple":
				{
					// This ability increases the target minion's health by 1.
					ability = (Minion target) -> {
						target.setHealth(target.getHealth() + 2);
					};
					break;
				}
		}
	}

	public SpecialMinion(SpecialMinion m) {
		this(
				m.getName(),
				m.getDescription(),
				m.getColors(),
				m.getMana(),
				m.getHealth(),
				m.getAttackDamage()
			);
	}

	public void useAbility(Minion target) {
		ability.useAbility(target);
	}
}
