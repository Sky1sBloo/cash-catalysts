package org.CashCatalysts.CashCatalysts.game.chests;

import org.CashCatalysts.CashCatalysts.game.UserGameStatsHandler;
import org.CashCatalysts.CashCatalysts.game.chests.rewards.ChestDrop;
import org.CashCatalysts.CashCatalysts.game.plants.PlantsHandler;

import java.util.ArrayList;
import java.util.List;

public class ChestHandler {
    private final UserGameStatsHandler userGameStatsHandler;
    private final PlantsHandler plantsHandler;

    public ChestHandler(UserGameStatsHandler userGameStatsHandler, PlantsHandler plantsHandler) {
        this.userGameStatsHandler = userGameStatsHandler;
        this.plantsHandler = plantsHandler;
    }

    public List<ChestDrop> expendChest(ChestRarity rarity) {
        List<ChestDrop> chestDrops = new ArrayList<>();
        Chest chest = null;
        switch (rarity) {
            case NORMAL -> {
                if (userGameStatsHandler.getUserGameStats().getNormalChests().getAmount() <= 0) {
                    return chestDrops;
                }
                userGameStatsHandler.getUserGameStats().getNormalChests().exchange(1);
                chest = new Chest(ChestRarity.NORMAL);
            }
            case RARE -> {
                if (userGameStatsHandler.getUserGameStats().getRareChests().getAmount() <= 0) {
                    return chestDrops;
                }
                userGameStatsHandler.getUserGameStats().getRareChests().exchange(1);
                chest = new Chest(ChestRarity.RARE);
            }
            case EPIC -> {
                if (userGameStatsHandler.getUserGameStats().getEpicChests().getAmount() <= 0) {
                    return chestDrops;
                }
                userGameStatsHandler.getUserGameStats().getEpicChests().exchange(1);
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
                case NORMAL -> userGameStatsHandler.getUserGameStats().getNormalChests().add(1);
                case RARE -> userGameStatsHandler.getUserGameStats().getRareChests().add(1);
                case EPIC -> userGameStatsHandler.getUserGameStats().getEpicChests().add(1);
            }
        }
        updateChestsAmount();
    }

    public void addChest(Chest chest) {
        switch (chest.getRarity()) {
            case NORMAL -> userGameStatsHandler.getUserGameStats().getNormalChests().add(1);
            case RARE -> userGameStatsHandler.getUserGameStats().getRareChests().add(1);
            case EPIC -> userGameStatsHandler.getUserGameStats().getEpicChests().add(1);
        }
        updateChestsAmount();
    }

    private void updateChestsAmount() {
        userGameStatsHandler.updateUserGameStats();
        /*
        try {
            chestsInventoryTable.updateChestsAmount(new UserChestsInventory(userId, normalChestsAmount, rareChestsAmount, epicChestsAmount));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } */
    }
}