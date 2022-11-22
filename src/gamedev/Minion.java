package gamedev;

import java.util.ArrayList;

public class Minion extends BattlingCard {
    protected int attackDamage;
    public Minion(
            final String name,
            final String description,
            final ArrayList<String> colors,
            final int mana,
            final int health,
            final int attackDamage
    ) {
        super(name, description, colors, mana, health);
        this.attackDamage = attackDamage;
        this.type = MINION_TYPE;
    }

    public Minion(final Minion m) {
        this(
                m.getName(),
                m.getDescription(),
                m.getColors(),
                m.getMana(),
                m.getHealth(),
                m.getAttackDamage()
        );
    }

    /**
     * This method executes the attack command on another BattlingCard.
     * @param target The target BattlingCard.
     */
    public void attackCommand(final BattlingCard target) {
        target.setHealth(target.getHealth() - this.attackDamage);
    }

    /**
     * This method returns the Minion's attack damage.
     * @return The Minion's attack damage.
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * This method sets the Minion's new attack damage.
     * @param attackDamage The Minion's new attack damage.
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }
}
