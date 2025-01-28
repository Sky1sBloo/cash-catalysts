package org.CashCatalysts.CashCatalysts.game.challenges;

import org.CashCatalysts.CashCatalysts.Database.ChallengesTable;
import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.datatypes.ApplicationRandom;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ChallengeHandler {
    private final ChallengesTable challengesTable;

    public ChallengeHandler(DatabaseHandler databaseHandler) {
        this.challengesTable = databaseHandler.getChallengesTable();
    }

    /**
     * Generates 3 daily challenges for the given date
     * Also deletes all challenges before the given date
     */
    public void generateDailyChallenges(LocalDate date) {
        ChallengeCondition[] challengeConditions = new ChallengeCondition[3];
        for (int i = 0; i < 3; i++) {
            ChallengeCondition condition = generateDailyChallengeCondition();
            if (Arrays.stream(challengeConditions).noneMatch(c -> c == condition)) {
                challengeConditions[i] = condition;
            } else {
                i--;
                continue;
            }
            Challenge challenge = generateChallengeByCondition(condition, date);
            addChallenge(challenge);
        }
        try {
            challengesTable.deleteAllChallengesBeforeDate(date);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates 3 weekly challenges for the given date
     */
    public void generateWeeklyChallenges(LocalDate date) {
        ChallengeCondition[] challengeConditions = new ChallengeCondition[3];
        for (int i = 0; i < 3; i++) {
            ChallengeCondition condition = generateWeeklyChallengeCondition();
            if (Arrays.stream(challengeConditions).noneMatch(c -> c == condition)) {
                challengeConditions[i] = condition;
            } else {
                i--;
                continue;
            }
            Challenge challenge = generateChallengeByCondition(condition, date);
            addChallenge(challenge);
        }
    }

    public void checkChallengeCompletion(LocalDate date) {
        try {
            for (Challenge challenge : challengesTable.getAllChallengesAfterDate(date)) {
                if (isChallengeCompleted(challenge)) {
                    deleteChallenge(challenge.id());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Challenge> getUpcomingChallenges(LocalDate date) {
        try {
            return challengesTable.getAllChallengesAfterDate(date);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private int addChallenge(Challenge challenge) {
        try {
            return challengesTable.addChallenge(challenge);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteChallenge(int id) {
        try {
            challengesTable.deleteChallenge(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isChallengeCompleted(Challenge challenge) {
        // Check if the challenge is completed
        return false;
    }

    private ChallengeCondition generateDailyChallengeCondition() {
        int randomChoice = ApplicationRandom.randomInt(0, 100);
        if (randomChoice < 25) {
            return ChallengeCondition.SAVE_AND_EARN;
        } else if (randomChoice < 50) {
            return ChallengeCondition.DAILY_HARVESTER;
        } else if (randomChoice < 75) {
            return ChallengeCondition.WATER_SAVER;
        } else {
            return ChallengeCondition.CROP_SELLER;
        }
    }

    private ChallengeCondition generateWeeklyChallengeCondition() {
        int randomChoice = ApplicationRandom.randomInt(0, 100);
        if (randomChoice < 33) {
            return ChallengeCondition.BUDGET_BOSS;
        } else if (randomChoice < 66) {
            return ChallengeCondition.SAVINGS_STREAK;
        } else {
            return ChallengeCondition.GOLDEN_HARVEST;
        }
    }

    private Challenge generateChallengeByCondition(ChallengeCondition condition, LocalDate startDate) {
        switch (condition) {
            case ChallengeCondition.SAVE_AND_EARN -> {
                return new Challenge(null,
                        ChallengeCondition.SAVE_AND_EARN,
                        "Save and Earn",
                        "Save 50 today",
                        startDate,
                        startDate.plusDays(1),
                        generateDailyChallengeReward());
            }
            case ChallengeCondition.DAILY_HARVESTER -> {
                return new Challenge(null,
                        ChallengeCondition.DAILY_HARVESTER,
                        "Daily Harvester",
                        "Harvest 5 crops today",
                        startDate,
                        startDate.plusDays(1),
                        generateDailyChallengeReward());
            }
            case ChallengeCondition.WATER_SAVER -> {
                return new Challenge(null,
                        ChallengeCondition.WATER_SAVER,
                        "Water Saver",
                        "Use at least 12 water today",
                        startDate,
                        startDate.plusDays(1),
                        generateDailyChallengeReward());
            }
            case ChallengeCondition.CROP_SELLER -> {
                return new Challenge(null,
                        ChallengeCondition.CROP_SELLER,
                        "Crop Seller",
                        "Sell 6 crops today",
                        startDate,
                        startDate.plusDays(1),
                        generateDailyChallengeReward());
            }
            case ChallengeCondition.BUDGET_BOSS -> {
                return new Challenge(null,
                        ChallengeCondition.BUDGET_BOSS,
                        "Budget Boss",
                        "Stay within weekly budget at the end of the week",
                        startDate,
                        startDate.plusWeeks(1),
                        generateWeeklyChallengeReward());
            }
            case ChallengeCondition.SAVINGS_STREAK -> {
                return new Challenge(null,
                        ChallengeCondition.SAVINGS_STREAK,
                        "Savings Streak",
                        "Save 500 in a week",
                        startDate,
                        startDate.plusDays(7),
                        generateWeeklyChallengeReward());
            }
            case ChallengeCondition.GOLDEN_HARVEST -> {
                return new Challenge(null,
                        ChallengeCondition.GOLDEN_HARVEST,
                        "Golden Harvest",
                        "Harvest 50 crops in a week",
                        startDate,
                        startDate.plusDays(7),
                        generateWeeklyChallengeReward());
            }
            default -> throw new IllegalArgumentException("Invalid challenge condition");
        }
    }

    /**
     * Generates random rewards for daily
     */
    private ChallengeReward generateDailyChallengeReward() {
        int goldCount = 0;
        int starCount = 0;
        int xpCount = 0;
        int normalChestCount = 0;

        int randomChoice = ApplicationRandom.randomInt(0, 100);
        if (randomChoice < 25) {
            goldCount = ApplicationRandom.randomInt(5, 10);
        } else if (randomChoice < 50) {
            starCount = ApplicationRandom.randomInt(1, 3);
        } else if (randomChoice < 75) {
            xpCount = ApplicationRandom.randomInt(10, 50);
        } else {
            normalChestCount = 1;
        }
        return new ChallengeReward(goldCount, starCount, xpCount, normalChestCount, 0, 0);
    }

    /**
     * Generates random rewards for weekly
     */
    private ChallengeReward generateWeeklyChallengeReward() {
        int goldCount = 0;
        int starCount = 0;
        int xpCount = 0;
        int normalChestCount = 0;
        int rareChestCount = 0;
        int epicChestCount = 0;

        int randomChoice = ApplicationRandom.randomInt(0, 100);
        if (randomChoice < 20) {
            goldCount = ApplicationRandom.randomInt(15, 30);
        } else if (randomChoice < 40) {
            starCount = ApplicationRandom.randomInt(3, 6);
        } else if (randomChoice < 60) {
            xpCount = ApplicationRandom.randomInt(50, 100);
        } else if (randomChoice < 80) {
            normalChestCount = 1;
        } else if (randomChoice < 95) {
            rareChestCount = 1;
        } else if (randomChoice < 100) {
            epicChestCount = 1;
        }
        return new ChallengeReward(goldCount, starCount, xpCount, normalChestCount, rareChestCount, epicChestCount);
    }
}
