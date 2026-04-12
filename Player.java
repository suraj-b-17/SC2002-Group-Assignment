import java.util.ArrayList;
import java.util.List;

public abstract class Player extends Combatant {
    private List<Item> items = new ArrayList<>();

    public Player(String name, int hp, int attack, int defense, int speed) {
        super(name, hp, attack, defense, speed);
    }

    public abstract boolean isSpecialReady();
    public abstract int getSpecialCooldown();
    public abstract void executeSpecialSkill(BattleEngine battle, Combatant target, boolean freeUse);
    public abstract boolean needsTarget();

    public void addItem(Item item) { items.add(item); }
    public boolean hasItems() { return !items.isEmpty(); }
    public List<Item> getItems() { return items; }
    public Item removeItem(int index) { return items.remove(index); }
}