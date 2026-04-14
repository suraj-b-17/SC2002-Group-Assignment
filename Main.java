import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Turn-Based Combat Arena ===");
        boolean continueRunning = true;
        GameSettings settings = null;

        while (continueRunning) {
            if (settings == null) {
                settings = showHomeScreen();
            }
            BattleEngine battle = new BattleEngine(settings.createPlayer(), settings.difficulty);
            BattleOutcome outcome = battle.startBattle();
            printCompletionScreen(outcome, battle.getRoundCount(), battle);
            continueRunning = handlePostGame(settings);
            if (continueRunning && settings.newGameRequested) {
                settings = null;
            }
        }
        System.out.println("Thanks for playing. Goodbye!");
    }

    private static GameSettings showHomeScreen() {
        System.out.println("\n--- Player Classes ---");
        System.out.println("1) Warrior   HP:260 ATK:40 DEF:20 SPD:30  Special: Shield Bash");
        System.out.println("              Shield Bash: BasicAttack damage + stun target for 2 turns. Cooldown: 3");
        System.out.println("2) Wizard    HP:200 ATK:50 DEF:10 SPD:20  Special: Arcane Blast");
        System.out.println("              Arcane Blast: BasicAttack damage to ALL enemies. Each kill: +10 ATK. Cooldown: 3");
        Player player = chooseCharacter();

        System.out.println("\n--- Enemy Types ---");
        System.out.println("Goblin  HP:55  ATK:35 DEF:15 SPD:25");
        System.out.println("Wolf    HP:40  ATK:45 DEF:5  SPD:35");

        System.out.println("\n--- Items (choose 2, duplicates allowed) ---");
        System.out.println("1) Potion      - Heal 100 HP (max HP capped)");
        System.out.println("2) Power Stone - Trigger special skill for free (no cooldown change)");
        System.out.println("3) Smoke Bomb  - Enemy attacks deal 0 damage this turn and next");
        List<Item> items = chooseItems();

        System.out.println("\n--- Difficulty ---");
        System.out.println("1) Easy   - Initial: 3 Goblins");
        System.out.println("2) Medium - Initial: 1 Goblin + 1 Wolf | Backup: 2 Wolves");
        System.out.println("3) Hard   - Initial: 2 Goblins           | Backup: 1 Goblin + 2 Wolves");
        Difficulty difficulty = chooseDifficulty();

        System.out.println("\n--- Level Preview: " + difficulty.getDisplayName() + " ---");
        System.out.println("Initial enemies:");
        difficulty.createInitialEnemies().forEach(e -> System.out.println("  " + e.getStatusLine()));
        if (!difficulty.createBackupEnemies().isEmpty()) {
            System.out.println("Backup enemies:");
            difficulty.createBackupEnemies().forEach(e -> System.out.println("  " + e.getStatusLine()));
        }

        System.out.println("\n--- Your Setup ---");
        System.out.println("Hero: " + player.getName());
        System.out.println("Difficulty: " + difficulty.getDisplayName());
        System.out.print("Items: ");
        items.forEach(i -> System.out.print(i.getName() + " "));
        System.out.println();

        return new GameSettings(player, items, difficulty);
    }

    private static Player chooseCharacter() {
        int choice = askInteger("Choose your hero (1-2)", 1, 2);
        return choice == 1 ? new Warrior() : new Wizard();
    }

    private static List<Item> chooseItems() {
        List<Item> selected = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            System.out.printf("Item selection %d:%n", i);
            int choice = askInteger("Choose item (1-3)", 1, 3);
            switch (choice) {
                case 1: selected.add(new Potion()); break;
                case 2: selected.add(new PowerStone()); break;
                default: selected.add(new SmokeBomb()); break;
            }
        }
        return selected;
    }

    private static Difficulty chooseDifficulty() {
        int choice = askInteger("Select difficulty (1-3)", 1, 3);
        return Difficulty.values()[choice - 1];
    }

    private static void printCompletionScreen(BattleOutcome outcome, int rounds, BattleEngine battle) {
        if (outcome == BattleOutcome.VICTORY) {
            System.out.println("\n=== Victory! ===");
            System.out.println("Congratulations, you have defeated all your enemies.");
            System.out.println("Remaining HP: " + battle.getPlayer().getHp()
                + "/" + battle.getPlayer().getMaxHp()
                + " | Total Rounds: " + rounds);
        } else {
            System.out.println("\n=== Defeat ===");
            System.out.println("Defeated. Don't give up, try again!");
            System.out.println("Enemies remaining: " + battle.getAliveEnemies().size()
                + " | Total Rounds Survived: " + rounds);
        }
    }

    private static boolean handlePostGame(GameSettings settings) {
        System.out.println("\n1) Replay with same settings");
        System.out.println("2) Start new game");
        System.out.println("3) Exit");
        int choice = askInteger("Enter choice", 1, 3);
        if (choice == 1) { settings.newGameRequested = false; return true; }
        if (choice == 2) { settings.newGameRequested = true; return true; }
        return false;
    }

    private static int askInteger(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.printf("Please enter a number between %d and %d.%n", min, max);
                } else {
                    return value;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    static class GameSettings {
        final Player player;
        final List<Item> items;
        final Difficulty difficulty;
        boolean newGameRequested = false;

        GameSettings(Player player, List<Item> items, Difficulty difficulty) {
            this.player = player;
            this.items = items;
            this.difficulty = difficulty;
        }

        Player createPlayer() {
            Player clone = player instanceof Warrior ? new Warrior() : new Wizard();
            for (Item item : items) {
                if (item instanceof Potion) clone.addItem(new Potion());
                else if (item instanceof PowerStone) clone.addItem(new PowerStone());
                else if (item instanceof SmokeBomb) clone.addItem(new SmokeBomb());
            }
            return clone;
        }
    }
}