package gamedev;

import java.util.ArrayList;

interface HeroAbility {
    void useAbility(BattleGround bg, int targetRowIdx);
}

public final class Hero extends BattlingCard {
    private HeroAbility ability;
    private static final int DEF_HERO_HP = 30;

    public Hero(
            final String name,
            final String description,
            final ArrayList<String> colors,
            final int mana
    ) {
        super(name, description, colors, mana, DEF_HERO_HP);
        this.type = HERO_TYPE;

        switch (name) {
            case "Lord Royce" -> {
                // This ability freezes the minion with the most attack damage on row.
                ability = (BattleGround bg, int targetRowIdx) -> {
                    // Get the row of minions.
                    ArrayList<Minion> targets = bg.getRows().get(targetRowIdx).getMinions();
                    // Find the minion with the most attack damage.
                    Minion target = targets.get(0);
                    for (Minion m : targets) {
                        if (m.getAttackDamage() > target.getAttackDamage()) {
                            target = m;
                        }
                    }
                    // Freeze the minion.
                    target.setFrozen(true);
                };
            }
            case "Empress Thorina" -> {
                // Deletes the minion with the most health on row.
                ability = (BattleGround bg, int targetRowIdx) -> {
                    // Fetch the minions.
                    ArrayList<Minion> row = bg.getRows().get(targetRowIdx).getMinions();
                    Minion targetMinion = row.get(0);
                    // Search for the minion with the most health.
                    for (Minion m : row) {
                        if (m.getHealth() > targetMinion.getHealth()) {
                            targetMinion = m;
                        }
                    }
                    // Delete the target minion.
                    row.remove(targetMinion);
                };
            }
            case "King Mudface" -> {
                // This ability adds 1 health to every minion on target row.
                ability = (BattleGround bg, int targetRowIdx) -> {
                    // Get the row of minions.
                    ArrayList<Minion> targets = bg.getRows().get(targetRowIdx).getMinions();
                    // Increment minions health.
                    for (Minion m : targets) {
                        m.setHealth(m.getHealth() + 1);
                    }
                };
            }
            case "General Kocioraw" -> {
                // This ability adds 1 attack damage to all minions on target row.
                ability = (BattleGround bg, int targetRowIdx) -> {
                    // Get the row of minions.
                    ArrayList<Minion> targets = bg.getRows().get(targetRowIdx).getMinions();
                    // Increment minions attack damage.
                    for (Minion target : targets) {
                        target.setAttackDamage(target.getAttackDamage() + 1);
                    }
                };
            }
            default -> {
            }
        }
    }

    public Hero() {
        super();
        this.health = DEF_HERO_HP;
        this.type = HERO_TYPE;
    }

    public Hero(final Hero h) {
        this(
                h.getName(),
                h.getDescription(),
                h.getColors(),
                h.getMana()
        );
    }

    /**
     * This method executes the Hero's ability.
     * @param bg The BattleGround.
     * @param targetRowIdx The target row index.
     */
    public void useAbility(final BattleGround bg, final int targetRowIdx) {
        ability.useAbility(bg, targetRowIdx);
    }
}
