package gamedev;

import java.util.ArrayList;

import gamedev.BattlingCard;
import gamedev.BattleGround;

public abstract class Hero extends BattlingCard {
	public Hero(
			String name,
			String description,
			ArrayList<String> colors,
			int mana,
			int health
			) {
		super(name, description, colors, mana, health);
		this.health = 30;
		this.type = 3;
	}

	public Hero() {
		super();
		this.health = 30;
		this.type = 3;
	}

	public abstract void useAbility(BattleGround bg, int targetRowIdx);
}
