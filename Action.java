public interface Action {
    String getName();
    void execute(BattleEngine battle, Combatant actor);
}