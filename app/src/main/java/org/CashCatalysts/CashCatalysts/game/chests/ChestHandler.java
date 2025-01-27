package org.CashCatalysts.CashCatalysts.game.chests;
import java.util.*;

public class ChestHandler {
    // @param chest should  be editable if the drop rates for chest rarities are done
    ChestData chest = EpicType.EPIC;
    int chestXp = chest.getXp();
    int packSize = chest.getPackSize();
    List<String> chestRewards = randomizeRewards(chest);


    public static List<String> randomizeRewards(ChestData chest){
        ArrayList<String> chestRewards = new ArrayList<>();
        Map<String, Integer> dropRates = chest.getDropRates();
        String randomReward;
        int packSize = chest.getPackSize();

        for(int loopIncrement = 0; loopIncrement < packSize; loopIncrement++){
            randomReward = randomizationFunction(dropRates);
            chestRewards.add(randomReward);
        }
        return chestRewards;

    }

    public static String randomizationFunction(Map<String, Integer> dropRates){
        Random random = new Random();
        int randomNumber = random.nextInt(101);
        int cumulativeWeight = 0;
        String chosenReward = null;

        for (Map.Entry<String, Integer> dropRate : dropRates.entrySet()) {
            cumulativeWeight += dropRate.getValue(); //prevents only the last iterated item if they have the same drop rate
            if(randomNumber < cumulativeWeight){
                chosenReward = dropRate.getKey();
                break;
            }
        }
        return chosenReward;
    }
}