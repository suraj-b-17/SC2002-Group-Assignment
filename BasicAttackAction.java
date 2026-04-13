public class BasicAttackAction implements Action {
    private final Combatant target;

    public BasicAttackAction(Combatant target) {
        this.target = target;
    }

    @Override
    public String getName() {
        return "Basic Attack";
    }

    @Override
    public void execute(BattleEngine battle, Combatant actor) {
        int damage = Math.max(0, actor.getAttack() - target.getDefense());
        target.takeDamage(damage);
        System.out.printf("%s attacks %s for %d damage.%n",
            actor.getName(), target.getName(), damage);
        if (target.isDefeated()) {
            System.out.printf("%s has been eliminated!%n", target.getName());
        }
    }
}