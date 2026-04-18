public class ShieldEffect extends StatusEffect {
    public ShieldEffect() {
        super(1); // absorbs one hit then expires
    }

    @Override
    public void apply(Combatant target) {
        System.out.printf("%s is shielded and will absorb the next hit completely!%n",
            target.getName());
    }

    @Override
    public void onTick(Combatant target) {}
}