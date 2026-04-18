public class Shield implements Item {
    @Override
    public String getName() {
        return "Shield";
    }

    @Override
    public void use(Player user, BattleEngine battle) {
        user.addStatusEffect(new ShieldEffect());
        System.out.printf("%s equips a Shield and will absorb the next hit completely!%n",
            user.getName());
    }
}