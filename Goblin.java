import java.util.List;

public class Goblin extends Combatant {
    public Goblin(String name) {
        super(name, 55, 35, 15, 25);
    }

    @Override
    public Action chooseAction(List<Combatant> targets) {
        return new BasicAttackAction(targets.get(0));
    }
}