package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.CashCatalysts.CashCatalysts.game.UserGameStats;
import org.CashCatalysts.CashCatalysts.game.UserGameStatsHandler;

public class StatsBarController {
    private final UserGameStats userGameStats;
    @FXML
    private Label gold_lbl;
    @FXML
    private Label star_lbl;
    @FXML
    private Label normal_chest_lbl;
    @FXML
    private Label rare_chest_lbl;
    @FXML
    private Label epic_chest_lbl;

    public StatsBarController(UserGameStatsHandler userGameStatsHandler) {
        userGameStats = userGameStatsHandler.getUserGameStats();
    }

    public void initialize() {
        gold_lbl.setText(String.valueOf(userGameStats.getGold().getAmount()));
        star_lbl.setText(String.valueOf(userGameStats.getStar().getAmount()));
        normal_chest_lbl.setText(String.valueOf(userGameStats.getNormalChests().getAmount()));
        rare_chest_lbl.setText(String.valueOf(userGameStats.getRareChests().getAmount()));
        epic_chest_lbl.setText(String.valueOf(userGameStats.getEpicChests().getAmount()));
    }
}
