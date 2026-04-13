import java.util.List;

public class Wizard extends Player {
    private int specialCooldown = 0;
    private int attackBonus = 0;

    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
    }

    @Override
    public Action chooseAction(List<Combatant> targets) {
        return null;
    }

    @Override
    public boolean isSpecialReady() {
        return specialCooldown == 0;
    }

    @Override
    public int getSpecialCooldown() { return specialCooldown; }

    @Override
    public boolean needsTarget() { return false; }

    @Override
    public void executeSpecialSkill(BattleEngine battle, Combatant target, boolean freeUse) {
        List<Combatant> enemies = battle.getAliveEnemies();
        System.out.printf("%s uses Arcane Blast!%n", name);
        for (Combatant enemy : enemies) {
            int damage = Math.max(0, attack - enemy.getDefense());
            enemy.takeDamage(damage);
            System.out.printf("  -> %s takes %d damage. HP: %d/%d%n",
                enemy.getName(), damage, enemy.getCurrentHp(), enemy.getMaxHp());
            if (enemy.isDefeated()) {
                attackBonus += 10;
                attack += 10;
                System.out.printf("  -> %s eliminated! Wizard ATK +10 (now %d)%n",
                    enemy.getName(), attack);
            }
        }
        if (!freeUse) {
            specialCooldown = 3;
        }
    }

    public int getAttackBonus() { return attackBonus; }

    @Override
    public void endTurn() {
        if (specialCooldown > 0) specialCooldown--;
    }
}