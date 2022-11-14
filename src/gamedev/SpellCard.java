package gamedev;

import java.util.ArrayList;

import gamedev.Card;
import gamedev.BattleGround;

abstract public class SpellCard extends Card {
	public SpellCard(
			String name,
			String description,
			ArrayList<String> colors,
			int mana
			) {
		super(name, description, colors, mana);
		this.type = 2;
	}

	abstract public void useSpell(BattleGround bg, int targetRowIdx);
}
