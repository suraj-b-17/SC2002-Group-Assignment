public class DefendAction implements Action {
    @Override
    public String getName() {
        return "Defend";
    }

    @Override
    public void execute(BattleEngine battle, Combatant actor) {
        actor.addStatusEffect(new DefendEffect(2));
        System.out.printf("%s uses Defend and increases defense for the current and next turn.%n",
            actor.getName());
    }
}