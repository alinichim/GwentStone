package gamedev;

import java.util.ArrayList;

import gamedev.Hero;
import gamedev.BattleGround;

public class GeneralKocioraw extends Hero {
	public GeneralKocioraw() {}

	public void useAbility(BattleGround bg, int targetRowIdx) {
		ArrayList<Minion> targets = bg.getRows().get(targetRowIdx).getMinions();
		for (Minion target : targets)
			target.setAttackDamage(target.getAttackDamage() + 1);
	}
}
