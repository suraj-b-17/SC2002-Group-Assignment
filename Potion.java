public class Potion implements Item {
    @Override
    public String getName() {
        return "Potion";
    }

    @Override
    public void use(Player user, BattleEngine battle) {
        user.heal(100);
        System.out.printf("%s uses Potion and restores 100 HP. HP: %d/%d%n",
            user.getName(), user.getHp(), user.getMaxHp());
    }
}