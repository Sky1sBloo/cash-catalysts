package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import org.CashCatalysts.CashCatalysts.game.UserGameStatsHandler;
import org.CashCatalysts.CashCatalysts.game.challenges.Challenge;
import org.CashCatalysts.CashCatalysts.game.challenges.ChallengeHandler;
import org.CashCatalysts.CashCatalysts.game.challenges.ChallengeReward;
import org.CashCatalysts.CashCatalysts.game.plants.PlantsHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ChallengesController {
    private final ChallengeHandler challengeHandler;
    private final UserGameStatsHandler userGameStatsHandler;

    @FXML
    private VBox daily_challenge_list;
    @FXML
    private VBox weekly_challenge_list;


    public ChallengesController(ChallengeHandler challengeHandler, UserGameStatsHandler userGameStatsHandler) {
        this.challengeHandler = challengeHandler;
        this.userGameStatsHandler = userGameStatsHandler;
    }


    public void initialize() {
        refresh();
    }

    private void refresh() {
        try {
            loadDailyChallenges();
            loadWeeklyChallenges();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        challengeHandler.checkChallengeCompletion(LocalDate.now());
    }

    /**
     * Load the daily challenges for the current date
     * If there are less than 3 daily challenges, generate new ones
     */
    private void loadDailyChallenges() throws IOException {
        List<Challenge> dailyChallenges = challengeHandler.getDailyChallengesOnDate(LocalDate.now());
        if (dailyChallenges.size() < 3) {
            challengeHandler.generateDailyChallenges(LocalDate.now());
            dailyChallenges = challengeHandler.getDailyChallengesOnDate(LocalDate.now());
        }
        daily_challenge_list.getChildren().clear();
        for (Challenge challenge : dailyChallenges) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../forms/ChallengesCard.fxml"));
            loader.setController(new ChallengesCardController(challenge, this::claimChallengeRewards));
            daily_challenge_list.getChildren().add(loader.load());
        }
    }

    private void loadWeeklyChallenges() throws IOException {
        List<Challenge> weeklyChallenges = challengeHandler.getWeeklyChallengesOnDate(LocalDate.now());
        if (weeklyChallenges.size() < 3) {
            challengeHandler.generateWeeklyChallenges(LocalDate.now());
            weeklyChallenges = challengeHandler.getWeeklyChallengesOnDate(LocalDate.now());
        }
        weekly_challenge_list.getChildren().clear();
        for (Challenge challenge : weeklyChallenges) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../forms/ChallengesCard.fxml"));
            loader.setController(new ChallengesCardController(challenge, this::claimChallengeRewards));
            weekly_challenge_list.getChildren().add(loader.load());
        }
    }

    private void claimChallengeRewards(Challenge challenge) {
        ChallengeReward reward = challenge.reward();
        if (reward.gold() > 0) {
            userGameStatsHandler.getUserGameStats().getGold().add(reward.gold());
        }
        if (reward.star() > 0) {
            userGameStatsHandler.getUserGameStats().getStar().add(reward.star());
        }
        if (reward.xp() > 0) {
            // TODO: Add this
        }
        if (reward.normalChest() > 0) {
            userGameStatsHandler.getUserGameStats().getNormalChests().add(reward.normalChest());
        }
        if (reward.rareChest() > 0) {
            userGameStatsHandler.getUserGameStats().getRareChests().add(reward.rareChest());
        }
        if (reward.epicChest() > 0) {
            userGameStatsHandler.getUserGameStats().getEpicChests().add(reward.epicChest());
        }
        userGameStatsHandler.updateUserGameStats();
        refresh();
    }
}
