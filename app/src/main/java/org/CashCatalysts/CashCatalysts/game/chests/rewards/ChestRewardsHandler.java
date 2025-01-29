package org.CashCatalysts.CashCatalysts.game.chests.rewards;

import org.CashCatalysts.CashCatalysts.datatypes.ApplicationRandom;
import org.CashCatalysts.CashCatalysts.game.plants.Plant;
import org.CashCatalysts.CashCatalysts.game.chests.ChestRarity;

import java.util.ArrayList;
import java.util.List;

public class ChestRewardsHandler {
    private static final ChestRewardRarity[] normalRewards = {
            new ChestRewardRarity(Plant.BANANA, 80),
            new ChestRewardRarity(Plant.PINEAPPLE, 60),
            new ChestRewardRarity(Plant.STRAWBERRY, 40),
            new ChestRewardRarity(Plant.APPLE, 20),
            new ChestRewardRarity(Plant.SAMPAGUITA, 51),
            new ChestRewardRarity(Plant.ORCHIDS, 20),
            new ChestRewardRarity(Plant.SUNFLOWER, 10),
            new ChestRewardRarity(Plant.ROSE, 5)
    };

    private static final ChestRewardRarity[] rareRewards = {
            new ChestRewardRarity(Plant.BANANA, 60),
            new ChestRewardRarity(Plant.PINEAPPLE, 50),
            new ChestRewardRarity(Plant.STRAWBERRY, 60),
            new ChestRewardRarity(Plant.APPLE, 40),
            new ChestRewardRarity(Plant.SAMPAGUITA, 30),
            new ChestRewardRarity(Plant.ORCHIDS, 40),
            new ChestRewardRarity(Plant.SUNFLOWER, 20),
            new ChestRewardRarity(Plant.ROSE, 10)
    };

    private static final ChestRewardRarity[] epicRewards = {
            new ChestRewardRarity(Plant.BANANA, 30),
            new ChestRewardRarity(Plant.PINEAPPLE, 40),
            new ChestRewardRarity(Plant.STRAWBERRY, 70),
            new ChestRewardRarity(Plant.APPLE, 30),
            new ChestRewardRarity(Plant.SAMPAGUITA, 10),
            new ChestRewardRarity(Plant.ORCHIDS, 60),
            new ChestRewardRarity(Plant.SUNFLOWER, 40),
            new ChestRewardRarity(Plant.ROSE, 20)
    };

    private final ChestRarity rarity;

    public ChestRewardsHandler(ChestRarity rarity) {
        this.rarity = rarity;
    }

    public List<ChestDrop> getRewards() {
        List<ChestDrop> rewards = new ArrayList<>();
        int amount = switch (rarity) {
            case NORMAL -> 3;
            case RARE -> 5;
            case EPIC -> 7;
        };

        ChestRewardRarity[] rewardsArray = switch (rarity) {
            case NORMAL -> normalRewards;
            case RARE -> rareRewards;
            case EPIC -> epicRewards;
        };

        for (ChestRewardRarity reward : rewardsArray) {
            if (ApplicationRandom.randomInt(0, 100) < reward.rarity()) {
                rewards.add(new ChestDrop(reward.plant(), amount));
            }
        }
        return rewards;
    }

    public int getXp() {
        return switch (rarity) {
            case NORMAL -> 30;
            case RARE -> 50;
            case EPIC -> 70;
        };
    }
}
