package org.CashCatalysts.CashCatalysts.game.chests.rewards;

import org.CashCatalysts.CashCatalysts.game.Plant;
import org.CashCatalysts.CashCatalysts.game.chests.ChestRarity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChestRewardsHandler {
    private static final ChestRewardRarity[] normalRewards = {
            new ChestRewardRarity(Plant.BANANA, 30),
            new ChestRewardRarity(Plant.PINEAPPLE, 5),
            new ChestRewardRarity(Plant.STRAWBERRY, 5),
            new ChestRewardRarity(Plant.APPLE, 30),
            new ChestRewardRarity(Plant.SAMPAGUITA, 21),
            new ChestRewardRarity(Plant.ORCHIDS, 2),
            new ChestRewardRarity(Plant.SUNFLOWER, 5),
            new ChestRewardRarity(Plant.ROSE, 2)
    };

    private static final ChestRewardRarity[] rareRewards = {
            new ChestRewardRarity(Plant.BANANA, 20),
            new ChestRewardRarity(Plant.PINEAPPLE, 10),
            new ChestRewardRarity(Plant.STRAWBERRY, 10),
            new ChestRewardRarity(Plant.APPLE, 20),
            new ChestRewardRarity(Plant.SAMPAGUITA, 20),
            new ChestRewardRarity(Plant.ORCHIDS, 5),
            new ChestRewardRarity(Plant.SUNFLOWER, 10),
            new ChestRewardRarity(Plant.ROSE, 5)
    };

    private static final ChestRewardRarity[] epicRewards = {
            new ChestRewardRarity(Plant.BANANA, 15),
            new ChestRewardRarity(Plant.PINEAPPLE, 13),
            new ChestRewardRarity(Plant.STRAWBERRY, 12),
            new ChestRewardRarity(Plant.APPLE, 15),
            new ChestRewardRarity(Plant.SAMPAGUITA, 15),
            new ChestRewardRarity(Plant.ORCHIDS, 10),
            new ChestRewardRarity(Plant.SUNFLOWER, 10),
            new ChestRewardRarity(Plant.ROSE, 10)
    };

    private final ChestRarity rarity;

    public ChestRewardsHandler(ChestRarity rarity) {
        this.rarity = rarity;
    }

    public List<ChestDrop> getRewards() {
        List<ChestDrop> rewards = new ArrayList<>();
        ChestRewardRarity[] rewardsArray = switch (rarity) {
            case NORMAL -> normalRewards;
            case RARE -> rareRewards;
            case EPIC -> epicRewards;
        };
        Random random = new Random();
        for (ChestRewardRarity reward : rewardsArray) {
            if (random.nextInt(100) < reward.rarity()) {
                rewards.add(new ChestDrop(reward.plant(), 1));
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
