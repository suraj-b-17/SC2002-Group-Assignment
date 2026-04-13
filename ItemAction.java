public class ItemAction implements Action {
    private final Item item;

    public ItemAction(Item item) {
        this.item = item;
    }

    @Override
    public String getName() {
        return "Use Item: " + item.getName();
    }

    @Override
    public void execute(BattleEngine battle, Combatant actor) {
        if (!(actor instanceof Player)) return;
        item.use((Player) actor, battle);
    }
}