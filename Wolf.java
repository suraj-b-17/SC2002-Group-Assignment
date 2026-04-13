import java.util.List;

public class Wolf extends Combatant {
    public Wolf(String name) {
        super(name, 40, 45, 5, 35);
    }

    @Override
    public Action chooseAction(List<Combatant> targets) {
        return new BasicAttackAction(targets.get(0));
    }
}