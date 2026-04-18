import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BattleEngine {
    private final Player player;
    private final Difficulty difficulty;
    private final List<Combatant> enemies = new ArrayList<>();
    private final List<Combatant> backupEnemies = new ArrayList<>();
    // Changed from hardcoded SpeedBasedTurnOrder to accept any strategy
    private final TurnOrderStrategy turnOrderStrategy;
    private final BattleUI ui;
    private int roundCount = 0;
    private boolean backupSpawned = false;

    // Updated constructor to accept TurnOrderStrategy
    public BattleEngine(Player player, Difficulty difficulty, TurnOrderStrategy turnOrderStrategy) {
        this.player = player;
        this.difficulty = difficulty;
        this.turnOrderStrategy = turnOrderStrategy;
        this.ui = new BattleUI();
        enemies.addAll(difficulty.createInitialEnemies());
        backupEnemies.addAll(difficulty.createBackupEnemies());
    }

    public BattleOutcome startBattle() {
        while (true) {
            roundCount++;
            ui.printRoundHeader(roundCount, player, getAliveEnemies());
            List<Combatant> turnOrder = turnOrderStrategy.determineOrder(getActiveCombatants());
            for (Combatant combatant : turnOrder) {
                if (combatant.isDefeated()) continue;
                combatant.startTurn();
                if (!combatant.canAct()) {
                    ui.printf("%s is stunned and cannot act this turn.%n", combatant.getName());
                    combatant.endTurn();
                    continue;
                }
                if (combatant instanceof Player) {
                    performPlayerTurn((Player) combatant);
                } else {
                    performEnemyTurn(combatant);
                }
                combatant.endTurn();
                checkWaveState();
                if (isBattleOver()) break;
            }
            if (isBattleOver()) break;
        }
        return player.isDefeated() ? BattleOutcome.DEFEAT : BattleOutcome.VICTORY;
    }

    public int getRoundCount() { return roundCount; }
    public Player getPlayer() { return player; }

    public List<Combatant> getAliveEnemies() {
        return enemies.stream().filter(Combatant::isAlive).collect(Collectors.toList());
    }

    private List<Combatant> getActiveCombatants() {
        List<Combatant> active = new ArrayList<>();
        if (!player.isDefeated()) active.add(player);
        active.addAll(getAliveEnemies());
        return active;
    }

    private void performPlayerTurn(Player player) {
        while (true) {
            int choice = ui.askPlayerAction(
                player.hasItems(),
                player.isSpecialReady(),
                player.getSpecialCooldown()
            );

            if (!player.hasItems() && choice == 3) choice = 4;

            if (choice == 1) {
                Combatant target = ui.promptEnemyTarget(getAliveEnemies());
                if (target == null) { ui.printMessage("No valid target. Choose again."); continue; }
                new BasicAttackAction(target).execute(this, player);
                break;
            }
            if (choice == 2) {
                new DefendAction().execute(this, player);
                break;
            }
            if (choice == 3) {
                if (performItemUse(player)) break;
                continue;
            }
            if (choice == 4) {
                if (!player.isSpecialReady()) { ui.printMessage("Special skill is on cooldown."); continue; }
                new SpecialSkillAction(false).execute(this, player);
                break;
            }
        }
    }

    private boolean performItemUse(Player player) {
        int choice = ui.askItemChoice(player.getItems());
        if (choice == player.getItems().size() + 1) return false;
        Item item = player.removeItem(choice - 1);
        new ItemAction(item).execute(this, player);
        return true;
    }

    public void usePowerStone(Player player) {
        ui.printf("%s uses Power Stone to trigger a free special skill.%n", player.getName());
        performSpecialSkill(player, true);
    }

    public void performSpecialSkill(Player player, boolean freeUse) {
        if (player.needsTarget()) {
            Combatant target = ui.promptEnemyTarget(getAliveEnemies());
            if (target == null) { ui.printMessage("No valid target. Special skill canceled."); return; }
            player.executeSpecialSkill(this, target, freeUse);
        } else {
            player.executeSpecialSkill(this, null, freeUse);
        }
    }

    private void performEnemyTurn(Combatant enemy) {
        if (player.isDefeated()) return;
        Action action = enemy.chooseAction(List.of(player));
        action.execute(this, enemy);
        if (player.isDefeated()) {
            ui.printMessage(player.getName() + " has been defeated.");
        }
    }

    private void checkWaveState() {
        if (!backupSpawned && getAliveEnemies().isEmpty() && !backupEnemies.isEmpty()) {
            backupSpawned = true;
            spawnBackupEnemies();
        }
    }

    private void spawnBackupEnemies() {
        enemies.addAll(backupEnemies);
        ui.printMessage("\nBackup enemies arrive!");
        backupEnemies.forEach(e -> ui.printf("- %s appears. (HP:%d ATK:%d DEF:%d SPD:%d)%n",
            e.getName(), e.getMaxHp(), e.getAttack(), e.getDefense(), e.getSpeed()));
        backupEnemies.clear();
    }

    public boolean isBattleOver() {
        return player.isDefeated() || (getAliveEnemies().isEmpty() && backupEnemies.isEmpty());
    }

    public Combatant promptTarget() {
        return ui.promptEnemyTarget(getAliveEnemies());
    }
}