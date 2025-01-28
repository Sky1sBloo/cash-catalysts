package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.CashCatalysts.CashCatalysts.game.challenges.Challenge;
import org.CashCatalysts.CashCatalysts.game.challenges.ChallengeReward;

import java.util.function.Consumer;

public class ChallengesCardController {
    private final Challenge challenge;
    private final Consumer<Challenge> claimChallengeRewards;

    @FXML
    private Label challenge_title;
    @FXML
    private Label challenge_description;
    @FXML
    private Label challenge_deadline;
    @FXML
    private Label challenge_is_completed;
    @FXML
    private Label challenge_rewards;
    @FXML
    private Button claim_challenge_btn;
    @FXML
    private Button cheat_complete_btn;

    public ChallengesCardController(Challenge challenge, Consumer<Challenge> claimChallengeRewards) {
        this.challenge = challenge;
        this.claimChallengeRewards = claimChallengeRewards;
    }

    public void initialize() {
        claim_challenge_btn.setDisable(challenge.isCompleted());
        claim_challenge_btn.setOnAction(e -> claimChallengeRewards.accept(challenge));
        cheat_complete_btn.setOnAction(e -> claimChallengeRewards.accept(challenge));
        challenge_title.setText(challenge.name());
        challenge_description.setText(challenge.description());
        challenge_deadline.setText(challenge.endDate().toString());
        challenge_is_completed.setText(challenge.isCompleted() ? "Completed" : "Not completed");
        ChallengeReward reward = challenge.reward();
        String rewardString = getRewardString(reward);
        challenge_rewards.setText(rewardString);
    }

    private static String getRewardString(ChallengeReward reward) {
        String rewardString;
        if (reward.gold() > 0) {
            rewardString = reward.gold() + " gold";
        } else if (reward.star() > 0) {
            rewardString = reward.star() + " star";
        } else if (reward.xp() > 0) {
            rewardString = reward.xp() + " xp";
        } else if (reward.normalChest() > 0) {
            rewardString = reward.normalChest() + " normal chest";
        } else if (reward.rareChest() > 0) {
            rewardString = reward.rareChest() + " rare chest";
        } else if (reward.epicChest() > 0) {
            rewardString = reward.epicChest() + " epic chest";
        } else {
            rewardString = "No reward";
        }
        return rewardString;
    }
}
