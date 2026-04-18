public class PoisonDart implements Item {
    @Override
    public String getName() {
        return "Poison Dart";
    }

    @Override
    public void use(Player user, BattleEngine battle) {
        System.out.printf("%s throws a Poison Dart!%n", user.getName());
        Combatant target = battle.promptTarget();
        if (target == null) {
            System.out.println("No valid target.");
            return;
        }
        target.addStatusEffect(new PoisonEffect(3));
    }
}