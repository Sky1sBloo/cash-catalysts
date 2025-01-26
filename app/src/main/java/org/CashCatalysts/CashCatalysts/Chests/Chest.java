package org.CashCatalysts.CashCatalysts.Chests;
import java.util.*;

public record Chest(ArrayList<String> chestRewards, Integer chestXp){

    @Override
    public String toString(){
        String rewardsString = String.join(", ", chestRewards);
        return "XP: " + chestXp + ", Rewards: " + rewardsString;
    }
}
