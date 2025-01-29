package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;
import org.CashCatalysts.CashCatalysts.game.UserGameStatsHandler;
import org.CashCatalysts.CashCatalysts.game.chests.Chest;
import org.CashCatalysts.CashCatalysts.game.chests.ChestHandler;
import org.CashCatalysts.CashCatalysts.game.chests.ChestRarity;
import org.CashCatalysts.CashCatalysts.game.chests.rewards.ChestDrop;
import org.CashCatalysts.CashCatalysts.game.plants.Plant;
import org.CashCatalysts.CashCatalysts.game.plants.PlantsHandler;

import java.io.IOException;
import java.util.List;

public class MarketController {
    private final ChestHandler chestHandler;
    private final UserGameStatsHandler userGameStatsHandler;
    private final PlantsHandler plantsHandler;

    @FXML
    private Button trade_normal_chest_btn;
    @FXML
    private Button trade_rare_chest_btn;
    @FXML
    private Button trade_epic_chest_btn;
    @FXML
    private Button buy_pot;

    @FXML
    private Label banana_lbl;
    @FXML
    private Label pineapple_lbl;
    @FXML
    private Label strawberry_lbl;
    @FXML
    private Label apple_lbl;

    @FXML
    private Button sell_banana_btn;
    @FXML
    private Button sell_pineapple_btn;
    @FXML
    private Button sell_strawberry_btn;
    @FXML
    private Button sell_apple_btn;

    public MarketController(ChestHandler chestHandler, UserGameStatsHandler userGameStatsHandler, PlantsHandler plantsHandler) {
        this.chestHandler = chestHandler;
        this.userGameStatsHandler = userGameStatsHandler;
        this.plantsHandler = plantsHandler;
    }

    public void initialize() {
        trade_normal_chest_btn.setOnAction(ignore -> tradeChest(ChestRarity.NORMAL));
        trade_rare_chest_btn.setOnAction(ignore -> tradeChest(ChestRarity.RARE));
        trade_epic_chest_btn.setOnAction(ignore -> tradeChest(ChestRarity.EPIC));
        buy_pot.setOnAction(ignore -> buyPot());

        sell_banana_btn.setOnAction(ignore -> sellPlant(Plant.BANANA));
        sell_pineapple_btn.setOnAction(ignore -> sellPlant(Plant.PINEAPPLE));
        sell_strawberry_btn.setOnAction(ignore -> sellPlant(Plant.STRAWBERRY));
        sell_apple_btn.setOnAction(ignore -> sellPlant(Plant.APPLE));
        refresh();
    }

    private void refresh() {
        loadStats();
        sell_banana_btn.setDisable(plantsHandler.getPlantsInventory().banana() == 0);
        sell_pineapple_btn.setDisable(plantsHandler.getPlantsInventory().pineapple() == 0);
        sell_strawberry_btn.setDisable(plantsHandler.getPlantsInventory().strawberry() == 0);
        sell_apple_btn.setDisable(plantsHandler.getPlantsInventory().apple() == 0);
    }

    private void loadStats() {
        banana_lbl.setText(String.valueOf(plantsHandler.getPlantsInventory().banana()));
        pineapple_lbl.setText(String.valueOf(plantsHandler.getPlantsInventory().pineapple()));
        strawberry_lbl.setText(String.valueOf(plantsHandler.getPlantsInventory().strawberry()));
        apple_lbl.setText(String.valueOf(plantsHandler.getPlantsInventory().apple()));
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

    private void sellPlant(Plant plant) {
        plantsHandler.removePlant(plant);
        int sellPrice = switch (plant) {
            case BANANA -> 5;
            case PINEAPPLE -> 10;
            case STRAWBERRY -> 15;
            case APPLE -> 20;
            default -> throw new IllegalArgumentException("Invalid plant type.");
        };

        userGameStatsHandler.getUserGameStats().getGold().add(sellPrice);
        userGameStatsHandler.updateUserGameStats();
        plantsHandler.updatePlantsInventory();
        refresh();
    }
}
