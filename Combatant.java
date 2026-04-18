import java.util.ArrayList;
import java.util.List;

public abstract class Combatant {
    protected String name;
    protected int maxHp;
    protected int currentHp;
    protected int attack;
    protected int defense;
    protected int speed;
    protected List<StatusEffect> statusEffects = new ArrayList<>();

    public Combatant(String name, int hp, int attack, int defense, int speed) {
        this.name = name;
        this.maxHp = hp;
        this.currentHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    public abstract Action chooseAction(List<Combatant> targets);

    public void takeDamage(int damage) {
        boolean hasSmokeBomb = statusEffects.stream().anyMatch(e -> e instanceof SmokeBombEffect);
        if (hasSmokeBomb) {
            System.out.println(name + " is protected by Smoke Bomb! 0 damage.");
            return;
        }
        // Check Shield - absorbs one hit completely then is consumed
        boolean hasShield = statusEffects.stream().anyMatch(e -> e instanceof ShieldEffect);
        if (hasShield) {
            System.out.printf("%s's Shield absorbs the hit! 0 damage.%n", name);
            statusEffects.removeIf(e -> e instanceof ShieldEffect);
            return;
        }
        currentHp = Math.max(0, currentHp - damage);
    }

    public void heal(int amount) {
        currentHp = Math.min(maxHp, currentHp + amount);
    }

    public boolean isAlive() {
        return currentHp > 0;
    }

    public boolean isDefeated() {
        return currentHp <= 0;
    }

    public boolean isStunned() {
        return statusEffects.stream().anyMatch(e -> e instanceof StunEffect);
    }

    public boolean canAct() {
        return !isStunned();
    }

    public void startTurn() {
        tickStatusEffects();
    }

    public void endTurn() {}

    public void applyStatusEffect(StatusEffect effect) {
        effect.apply(this);
        statusEffects.add(effect);
    }

    public void addStatusEffect(StatusEffect effect) {
        applyStatusEffect(effect);
    }

    public void tickStatusEffects() {
        for (StatusEffect e : statusEffects) {
            e.onTick(this);
            e.tick();
        }
        statusEffects.removeIf(e -> e.getDuration() <= 0);
    }

    public String getStatusLine() {
        return String.format("%s HP:%d/%d ATK:%d DEF:%d SPD:%d",
            name, currentHp, maxHp, attack, defense, speed);
    }

    public String getName() { return name; }
    public int getSpeed() { return speed; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getCurrentHp() { return currentHp; }
    public int getMaxHp() { return maxHp; }
    public int getHp() { return currentHp; }
    public List<StatusEffect> getStatusEffects() { return statusEffects; }
}