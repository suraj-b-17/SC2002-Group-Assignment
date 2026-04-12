public abstract class StatusEffect {
    protected int duration;

    public StatusEffect(int duration) {
        this.duration = duration;
    }

    public abstract void apply(Combatant target);
    public abstract void onTick(Combatant target);

    public void tick() {
        duration--;
    }

    public int getDuration() { return duration; }
}