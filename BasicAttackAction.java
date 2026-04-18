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

        
        boolean hasSmokeBomb = target.getStatusEffects().stream()
            .anyMatch(e -> e instanceof SmokeBombEffect);

        if (hasSmokeBomb) {
            
            System.out.printf("%s attempts to attack %s for %d damage, but is blocked by Smoke Bomb!%n",
                actor.getName(), target.getName(), damage);
            target.takeDamage(damage); 
        } else {
            
            target.takeDamage(damage);
            System.out.printf("%s attacks %s for %d damage.%n",
                actor.getName(), target.getName(), damage);
        }

        if (target.isDefeated()) {
            System.out.printf("%s has been eliminated!%n", target.getName());
        }
    }
}