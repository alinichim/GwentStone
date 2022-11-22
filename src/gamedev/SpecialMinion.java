package gamedev;

import java.util.ArrayList;

interface MinionAbility {
    void useAbility(Minion target);
}

public final class SpecialMinion extends Minion {
    private MinionAbility ability;

    public SpecialMinion(
            final String name,
            final String description,
            final ArrayList<String> colors,
            final int mana,
            final int health,
            final int attackDamage
    ) {
        super(name, description, colors, mana, health, attackDamage);
        this.type = SPECIAL_MINION_TYPE;

        switch (name) {
            case "The Ripper" -> {
                // This ability decreases minion's attack damage by 2.
                ability = (Minion target) -> {
                    target.setAttackDamage(target.getAttackDamage() - 2);
                    if (target.getAttackDamage() < 0) {
                        target.setAttackDamage(0);
                    }
                };
            }
            case "Miraj" -> {
                // This ability swaps the target minion's health with the
                // current minion's health.
                ability = (Minion target) -> {
                    int h = target.getHealth();
                    target.setHealth(this.getHealth());
                    this.setHealth(h);
                };
            }
            case "The Cursed One" -> {
                // This ability swaps the target minion's attack damage and
                // health.
                ability = (Minion target) -> {
                    int a = target.getAttackDamage();
                    target.setAttackDamage(target.getHealth());
                    target.setHealth(a);
                };
            }
            case "Disciple" -> {
                // This ability increases the target minion's health by 1.
                ability = (Minion target) -> {
                    target.setHealth(target.getHealth() + 2);
                };
            }
            default -> { }
        }
    }

    public SpecialMinion(final SpecialMinion m) {
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
     * This method executes the SpecialMinion's ability on a target minion.
     * @param target The target minion.
     */
    public void useAbility(final Minion target) {
        ability.useAbility(target);
    }
}
