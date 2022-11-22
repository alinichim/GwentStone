package gamedev;

import java.util.ArrayList;

interface Spell {
    void useSpell(BattleGround bg, int targetRowIdx);
}

public final class SpellCard extends Card {
    private Spell spell;

    public SpellCard(
            final String name,
            final String description,
            final ArrayList<String> colors,
            final int mana
    ) {
        super(name, description, colors, mana);
        this.type = SPELL_TYPE;

        switch (name) {
            case "Firestorm" -> {
                // This spell decreses the health of all minions on target
                // row by 1.
                spell = (BattleGround bg, int targetRowIdx) -> {
                    // Get the row.
                    Row r = bg.getRows().get(targetRowIdx);
                    // Deal damage to minions.
                    for (Minion m : r.getMinions()) {
                        m.setHealth(m.getHealth() - 1);
                    }
                };
            }
            case "Winterfell" -> {
                // This spell freezes all minions on a row.
                spell = (BattleGround bg, int targetRowIdx) -> {
                    // Get the row.
                    Row r = bg.getRows().get(targetRowIdx);
                    // Freeze minions.
                    for (Minion m : r.getMinions()) {
                        m.setFrozen(true);
                    }
                };
            }
            case "Heart Hound" -> {
                // This spell steals the minion with the most health.
                spell = (BattleGround bg, int targetRowIdx) -> {
                    // Get the row.
                    Row r = bg.getRows().get(targetRowIdx);
                    // Find the minion with the most health.
                    Minion target = r.getMinions().get(0);
                    for (Minion m : r.getMinions()) {
                        if (m.getHealth() > target.getHealth()) {
                            target = m;
                        }
                    }
                    // Steal the target minion.
                    r.getMinions().remove(target);
                    r = bg.getRows().get(BattleGround.MAX_ROWS - 1 - targetRowIdx);
                    r.getMinions().add(target);
                };
            }
            default -> { }
        }

    }

    /**
     * This method executes the Spell on a target row.
     * @param bg The BattleGround.
     * @param targetRowIdx The target row index.
     */
    public void useSpell(final BattleGround bg, final int targetRowIdx) {
        spell.useSpell(bg, targetRowIdx);
    }
}
