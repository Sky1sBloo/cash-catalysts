package org.CashCatalysts.CashCatalysts.game.chests;
import org.CashCatalysts.CashCatalysts.game.chests.rewards.ChestDrop;
import org.CashCatalysts.CashCatalysts.game.chests.rewards.ChestRewardsHandler;

import java.util.*;

public class Chest {
    private final ChestRewardsHandler rewardsHandler;
    private final ChestRarity rarity;

    public Chest(ChestRarity rarity) {
        rewardsHandler = new ChestRewardsHandler(rarity);
        this.rarity = rarity;
    }

    public List<ChestDrop> openChest() {
        return rewardsHandler.getRewards();
    }

    public ChestRarity getRarity() {
        return rarity;
    }
}
