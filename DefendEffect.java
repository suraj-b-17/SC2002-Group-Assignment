public class DefendEffect extends StatusEffect {
    public DefendEffect(int duration) {
        super(duration);
    }

    @Override
    public void apply(Combatant target) {
        target.defense += 10;
    }

    @Override
    public void onTick(Combatant target) {
        if (duration <= 1) {
            target.defense -= 10;
        }
    }
}