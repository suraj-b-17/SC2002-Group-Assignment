import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomTurnOrder implements TurnOrderStrategy {
    @Override
    public List<Combatant> determineOrder(List<Combatant> combatants) {
        // Shuffle combatants randomly each round instead of speed based
        List<Combatant> order = new ArrayList<>();
        for (Combatant c : combatants) {
            if (c.isAlive()) order.add(c);
        }
        Collections.shuffle(order);
        return order;
    }
}