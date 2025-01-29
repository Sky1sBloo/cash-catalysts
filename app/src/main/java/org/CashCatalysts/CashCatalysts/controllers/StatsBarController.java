package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;
import org.CashCatalysts.CashCatalysts.game.UserGameStats;
import org.CashCatalysts.CashCatalysts.game.UserGameStatsHandler;
import org.CashCatalysts.CashCatalysts.game.chests.ChestHandler;
import org.CashCatalysts.CashCatalysts.game.chests.ChestRarity;
import org.CashCatalysts.CashCatalysts.game.chests.rewards.ChestDrop;

import java.io.IOException;
import java.util.List;

public class StatsBarController {
    private final UserGameStatsHandler userGameStatsHandler;
    private final ChestHandler chestHandler;
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
    @FXML
    private Button claim_chest_btn;

    public StatsBarController(UserGameStatsHandler userGameStatsHandler, ChestHandler chestHandler) {
        this.userGameStatsHandler = userGameStatsHandler;
        this.chestHandler = chestHandler;
    }

    public void initialize() {
        claim_chest_btn.setOnAction(ignore -> claimChest());
        refresh();
    }

    private void refresh() {
        UserGameStats userGameStats = userGameStatsHandler.getUserGameStats();
        gold_lbl.setText(String.valueOf(userGameStats.getGold().getAmount()));
        star_lbl.setText(String.valueOf(userGameStats.getStar().getAmount()));
        normal_chest_lbl.setText(String.valueOf(userGameStats.getNormalChests().getAmount()));
        rare_chest_lbl.setText(String.valueOf(userGameStats.getRareChests().getAmount()));
        epic_chest_lbl.setText(String.valueOf(userGameStats.getEpicChests().getAmount()));
    }

    private void claimChest() {
        UserGameStats userGameStats = userGameStatsHandler.getUserGameStats();
        for (int i = 0; i < userGameStats.getNormalChests().getAmount(); i++) {
            List<ChestDrop> drops = chestHandler.expendChest(ChestRarity.NORMAL);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../forms/ChestRewardForm.fxml"));
            ChestRewardFormController controller = new ChestRewardFormController(drops);
            loader.setController(controller);
            Dialog<Void> dialog = new Dialog<>();
            try {
                dialog.setDialogPane(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            dialog.initStyle(StageStyle.UTILITY);
            dialog.showAndWait();
        }

        for (int i = 0; i < userGameStats.getRareChests().getAmount(); i++) {
            List<ChestDrop> drops = chestHandler.expendChest(ChestRarity.RARE);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../forms/ChestRewardForm.fxml"));
            ChestRewardFormController controller = new ChestRewardFormController(drops);
            loader.setController(controller);
            Dialog<Void> dialog = new Dialog<>();
            try {
                dialog.setDialogPane(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            dialog.initStyle(StageStyle.UTILITY);
            dialog.showAndWait();
        }

        for (int i = 0; i < userGameStats.getEpicChests().getAmount(); i++) {
            List<ChestDrop> drops = chestHandler.expendChest(ChestRarity.RARE);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../forms/ChestRewardForm.fxml"));
            ChestRewardFormController controller = new ChestRewardFormController(drops);
            loader.setController(controller);
            Dialog<Void> dialog = new Dialog<>();
            try {
                dialog.setDialogPane(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            dialog.initStyle(StageStyle.UTILITY);
            dialog.showAndWait();
        }
        refresh();
    }
}
