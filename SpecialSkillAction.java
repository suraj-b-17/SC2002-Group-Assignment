public class SpecialSkillAction implements Action {
    private final boolean freeUse;

    public SpecialSkillAction(boolean freeUse) {
        this.freeUse = freeUse;
    }

    @Override
    public String getName() {
        return freeUse ? "Power Stone Special" : "Special Skill";
    }

    @Override
    public void execute(BattleEngine battle, Combatant actor) {
        if (!(actor instanceof Player)) return;
        battle.performSpecialSkill((Player) actor, freeUse);
    }
}