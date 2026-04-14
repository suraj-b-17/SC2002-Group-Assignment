import java.util.List;
import java.util.Scanner;

public class BattleUI {
    private final Scanner scanner = new Scanner(System.in);

    public void printRoundHeader(int roundCount, Player player, List<Combatant> aliveEnemies) {
        System.out.println("\n=== Round " + roundCount + " ===");
        System.out.println(player.getStatusLine());
        aliveEnemies.forEach(e -> System.out.println(e.getStatusLine()));
    }

    public void printMessage(String msg) {
        System.out.println(msg);
    }

    public void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    public int askPlayerAction(boolean hasItems, boolean specialReady, int specialCooldown) {
        System.out.println("\nYour turn. Choose an action:");
        System.out.println("1) Basic Attack");
        System.out.println("2) Defend");
        if (hasItems) {
            System.out.println("3) Item");
        }
        System.out.println((hasItems ? "4" : "3") + ") Special Skill"
            + (specialReady ? "" : " (cooldown " + specialCooldown + ")"));
        int max = hasItems ? 4 : 3;
        return askInteger("Enter choice", 1, max);
    }

    public int askItemChoice(List<Item> items) {
        System.out.println("Choose an item:");
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, items.get(i).getName());
        }
        System.out.printf("%d) Cancel%n", items.size() + 1);
        return askInteger("Enter choice", 1, items.size() + 1);
    }

    public Combatant promptEnemyTarget(List<Combatant> aliveEnemies) {
        if (aliveEnemies.isEmpty()) return null;
        System.out.println("Choose a target:");
        for (int i = 0; i < aliveEnemies.size(); i++) {
            System.out.printf("%d) %s (HP:%d/%d)%n", i + 1,
                aliveEnemies.get(i).getName(),
                aliveEnemies.get(i).getCurrentHp(),
                aliveEnemies.get(i).getMaxHp());
        }
        int choice = askInteger("Enter target", 1, aliveEnemies.size());
        return aliveEnemies.get(choice - 1);
    }

    public int askInteger(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.printf("Please enter a number between %d and %d.%n", min, max);
                    continue;
                }
                return value;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }
}