import java.util.List;

public class Warrior extends Player {
    private int specialCooldown = 0;

    public Warrior() {
        super("Warrior", 260, 40, 20, 30);
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
    public boolean needsTarget() { return true; }

    @Override
    public void executeSpecialSkill(BattleEngine battle, Combatant target, boolean freeUse) {
        int damage = Math.max(0, attack - target.getDefense());
        target.takeDamage(damage);
        target.applyStatusEffect(new StunEffect());
        System.out.printf("%s uses Shield Bash on %s for %d damage and stuns them!%n",
            name, target.getName(), damage);
        if (!freeUse) {
            specialCooldown = 3;
        }
    }

    @Override
    public void endTurn() {
        if (specialCooldown > 0) specialCooldown--;
    }
}