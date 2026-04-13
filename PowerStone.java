public class PowerStone implements Item {
    @Override
    public String getName() {
        return "Power Stone";
    }

    @Override
    public void use(Player user, BattleEngine battle) {
        System.out.printf("%s uses Power Stone.%n", user.getName());
        battle.usePowerStone(user);
    }
}