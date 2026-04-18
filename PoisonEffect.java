public class PoisonEffect extends StatusEffect {
    private static final int POISON_DAMAGE = 5;

    public PoisonEffect(int duration) {
        super(duration);
    }

    @Override
    public void apply(Combatant target) {
        System.out.printf("%s is poisoned for %d turns!%n", target.getName(), duration);
    }

    @Override
    public void onTick(Combatant target) {
        // Deal 5 poison damage each turn, bypasses Smoke Bomb
        target.currentHp = Math.max(0, target.currentHp - POISON_DAMAGE);
        System.out.printf("%s takes %d poison damage! HP: %d/%d%n",
            target.getName(), POISON_DAMAGE, target.getCurrentHp(), target.getMaxHp());
    }
}