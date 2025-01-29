package org.CashCatalysts.CashCatalysts.game.chests;

import org.CashCatalysts.CashCatalysts.Database.ChestsInventoryTable;
import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.game.chests.rewards.ChestDrop;
import org.CashCatalysts.CashCatalysts.game.plants.PlantsHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChestHandler {
    private final ChestsInventoryTable chestsInventoryTable;
    private final PlantsHandler plantsHandler;
    private int normalChestsAmount;
    private int rareChestsAmount;
    private int epicChestsAmount;
    private final int userId;

    public ChestHandler(int userId, DatabaseHandler databaseHandler, PlantsHandler plantsHandler) {
        this.userId = userId;
        this.chestsInventoryTable = databaseHandler.getChestsInventoryTable();
        this.plantsHandler = plantsHandler;
    }

    public void addChestsInventory() {
        try {
            chestsInventoryTable.addChestsInventory(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ChestDrop> expendChest(ChestRarity rarity) {
        List<ChestDrop> chestDrops = new ArrayList<>();
        Chest chest = null;
        switch (rarity) {
            case NORMAL -> {
                if (normalChestsAmount <= 0) {
                    return chestDrops;
                }
                normalChestsAmount--;
                chest = new Chest(ChestRarity.NORMAL);
            }
            case RARE -> {
                if (rareChestsAmount <= 0) {
                    return chestDrops;
                }
                rareChestsAmount--;
                chest = new Chest(ChestRarity.RARE);
            }
            case EPIC -> {
                if (rareChestsAmount <= 0) {
                    return chestDrops;
                }
                epicChestsAmount--;
                chest = new Chest(ChestRarity.EPIC);
            }
        }
        for (ChestDrop reward : chest.openChest()) {
            for (int i = 0; i < reward.amount(); i++) {
                plantsHandler.addSeed(reward.plant());
            }
            chestDrops.add(reward);
        }
        plantsHandler.updateSeedsInventory();
        updateChestsAmount();
        return chestDrops;
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

    public void addChest(Chest chest) {
        switch (chest.getRarity()) {
            case NORMAL -> normalChestsAmount++;
            case RARE -> rareChestsAmount++;
            case EPIC -> epicChestsAmount++;
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