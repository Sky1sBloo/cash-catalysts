package org.CashCatalysts.CashCatalysts.game.chests;

import org.CashCatalysts.CashCatalysts.Database.ChestsInventoryTable;

import java.sql.SQLException;
import java.util.List;

public class ChestHandler {
    private final ChestsInventoryTable chestsInventoryTable;
    private int normalChestsAmount;
    private int rareChestsAmount;
    private int epicChestsAmount;
    private final int userId;

    public ChestHandler(int userId, ChestsInventoryTable chestsInventoryTable) {
        this.userId = userId;
        this.chestsInventoryTable = chestsInventoryTable;
    }

    public void addChestsInventory() {
        try {
            chestsInventoryTable.addChestsInventory(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void expendChest(ChestRarity rarity) {
        switch (rarity) {
            case NORMAL -> normalChestsAmount--;
            case RARE -> rareChestsAmount--;
            case EPIC -> epicChestsAmount--;
        }
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
            chestsInventoryTable.updateChestsAmount(userId, new UserChestsInventory(normalChestsAmount, rareChestsAmount, epicChestsAmount));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}