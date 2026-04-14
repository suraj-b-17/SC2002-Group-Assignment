import java.util.List;
import java.util.stream.Collectors;

public class SpeedBasedTurnOrder implements TurnOrderStrategy {
    @Override
    public List<Combatant> determineOrder(List<Combatant> combatants) {
        return combatants.stream()
            .filter(Combatant::isAlive)
            .sorted((a, b) -> b.getSpeed() - a.getSpeed())
            .collect(Collectors.toList());
    }
}