import java.util.ArrayList;
import java.util.List;

public enum Difficulty {
    EASY("Easy", "Initial Spawn: 3 Goblins") {
        @Override
        public List<Combatant> createInitialEnemies() {
            List<Combatant> list = new ArrayList<>();
            list.add(new Goblin("Goblin A"));
            list.add(new Goblin("Goblin B"));
            list.add(new Goblin("Goblin C"));
            return list;
        }
        @Override
        public List<Combatant> createBackupEnemies() {
            return new ArrayList<>();
        }
    },
    MEDIUM("Medium", "Initial Spawn: 1 Goblin + 1 Wolf | Backup: 2 Wolves") {
        @Override
        public List<Combatant> createInitialEnemies() {
            List<Combatant> list = new ArrayList<>();
            list.add(new Goblin("Goblin"));
            list.add(new Wolf("Wolf"));
            return list;
        }
        @Override
        public List<Combatant> createBackupEnemies() {
            List<Combatant> list = new ArrayList<>();
            list.add(new Wolf("Wolf A"));
            list.add(new Wolf("Wolf B"));
            return list;
        }
    },
    HARD("Hard", "Initial Spawn: 2 Goblins | Backup: 1 Goblin + 2 Wolves") {
        @Override
        public List<Combatant> createInitialEnemies() {
            List<Combatant> list = new ArrayList<>();
            list.add(new Goblin("Goblin A"));
            list.add(new Goblin("Goblin B"));
            return list;
        }
        @Override
        public List<Combatant> createBackupEnemies() {
            List<Combatant> list = new ArrayList<>();
            list.add(new Goblin("Goblin Backup"));
            list.add(new Wolf("Wolf A"));
            list.add(new Wolf("Wolf B"));
            return list;
        }
    };

    private final String displayName;
    private final String description;

    Difficulty(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public abstract List<Combatant> createInitialEnemies();
    public abstract List<Combatant> createBackupEnemies();

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
}