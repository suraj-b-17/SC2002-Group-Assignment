public class SmokeBomb implements Item {
    @Override
    public String getName() {
        return "Smoke Bomb";
    }

    @Override
    public void use(Player user, BattleEngine battle) {
        user.addStatusEffect(new SmokeBombEffect(2));
        System.out.printf("%s uses Smoke Bomb. Enemy attacks deal 0 damage this turn and next turn.%n",
            user.getName());
    }
}