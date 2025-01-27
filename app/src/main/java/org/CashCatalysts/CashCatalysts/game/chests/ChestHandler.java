package org.CashCatalysts.CashCatalysts.game.chests;

import org.CashCatalysts.CashCatalysts.Database.ChestsInventoryTable;
import org.CashCatalysts.CashCatalysts.game.chests.rewards.ChestDrop;
import org.CashCatalysts.CashCatalysts.game.plants.PlantsHandler;

import java.sql.SQLException;
import java.util.List;

public class ChestHandler {
    private final ChestsInventoryTable chestsInventoryTable;
    private final PlantsHandler plantsHandler;
    private int normalChestsAmount;
    private int rareChestsAmount;
    private int epicChestsAmount;
    private final int userId;

    public ChestHandler(int userId, ChestsInventoryTable chestsInventoryTable, PlantsHandler plantsHandler) {
        this.userId = userId;
        this.chestsInventoryTable = chestsInventoryTable;
        this.plantsHandler = plantsHandler;
    }

    public void addChestsInventory() {
        try {
            chestsInventoryTable.addChestsInventory(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void expendChest(ChestRarity rarity) {
        Chest chest = null;
        switch (rarity) {
            case NORMAL -> {
                if (normalChestsAmount <= 0) {
                    return;
                }
                normalChestsAmount--;
                chest = new Chest(ChestRarity.NORMAL);
            }
            case RARE -> {
                if (rareChestsAmount <= 0) {
                    return;
                }
                rareChestsAmount--;
                chest = new Chest(ChestRarity.RARE);
            }
            case EPIC -> {
                if (rareChestsAmount <= 0) {
                    return;
                }
                epicChestsAmount--;
                chest = new Chest(ChestRarity.EPIC);
            }
        }
        for (ChestDrop reward : chest.openChest()) {
            for (int i = 0; i < reward.amount(); i++) {
                plantsHandler.addSeed(reward.plant());
            }
        }
        plantsHandler.updateSeedsInventory();
        updateChestsAmount();
    }

    public void addChest(List<Chest> chests) {
        for (Chest chest : chests) {
            switch (chest.getRarity()) {
                case NORMAL -> normalChestsAmount++;
                case RARE -> rareChestsAmount++;
                case EPIC -> epicChestsAmount++;
            }
        }
        updateChestsAmount();
    }

    private void updateChestsAmount() {
        try {
            chestsInventoryTable.updateChestsAmount(new UserChestsInventory(userId, normalChestsAmount, rareChestsAmount, epicChestsAmount));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}