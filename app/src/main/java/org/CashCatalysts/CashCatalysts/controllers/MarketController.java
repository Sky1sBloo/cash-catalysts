package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.stage.StageStyle;
import org.CashCatalysts.CashCatalysts.game.UserGameStatsHandler;
import org.CashCatalysts.CashCatalysts.game.chests.Chest;
import org.CashCatalysts.CashCatalysts.game.chests.ChestHandler;
import org.CashCatalysts.CashCatalysts.game.chests.ChestRarity;
import org.CashCatalysts.CashCatalysts.game.chests.rewards.ChestDrop;

import java.io.IOException;
import java.util.List;

public class MarketController {
    private final ChestHandler chestHandler;
    private final UserGameStatsHandler userGameStatsHandler;

    @FXML
    private Button trade_normal_chest_btn;
    @FXML
    private Button trade_rare_chest_btn;
    @FXML
    private Button trade_epic_chest_btn;
    @FXML
    private Button buy_pot;

    public MarketController(ChestHandler chestHandler, UserGameStatsHandler userGameStatsHandler) {
        this.chestHandler = chestHandler;
        this.userGameStatsHandler = userGameStatsHandler;
    }

    public void initialize() {
        trade_normal_chest_btn.setOnAction(ignore -> tradeChest(ChestRarity.NORMAL));
        trade_rare_chest_btn.setOnAction(ignore -> tradeChest(ChestRarity.RARE));
        trade_epic_chest_btn.setOnAction(ignore -> tradeChest(ChestRarity.EPIC));
        buy_pot.setOnAction(ignore -> buyPot());
    }

    private void tradeChest(ChestRarity rarity) {
        chestHandler.addChest(new Chest(rarity));
        List<ChestDrop> drops = chestHandler.expendChest(rarity);

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

    private void buyPot() {
        userGameStatsHandler.getUserGameStats().getPots().add(1);
        userGameStatsHandler.updateUserGameStats();
    }
}
