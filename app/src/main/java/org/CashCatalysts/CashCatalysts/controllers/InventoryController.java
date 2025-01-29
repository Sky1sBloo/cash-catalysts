package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.CashCatalysts.CashCatalysts.game.UserGameStats;
import org.CashCatalysts.CashCatalysts.game.UserGameStatsHandler;
import org.CashCatalysts.CashCatalysts.game.plants.PlantsHandler;

public class InventoryController {
    private final UserGameStats userGameStats;
    private final PlantsHandler plantsHandler;

    @FXML
    private Label gold_lbl;
    @FXML
    private Label star_lbl;
    @FXML
    private Label banana_lbl;
    @FXML
    private Label pineapple_lbl;
    @FXML
    private Label strawberry_lbl;
    @FXML
    private Label apple_lbl;
    @FXML
    private Label sampaguita_lbl;
    @FXML
    private Label orchids_lbl;
    @FXML
    private Label sunflower_lbl;
    @FXML
    private Label rose_lbl;
    @FXML
    private Label pot_lbl;

    @FXML
    private Label banana_seed_lbl;
    @FXML
    private Label pineapple_seed_lbl;
    @FXML
    private Label strawberry_seed_lbl;
    @FXML
    private Label apple_seed_lbl;
    @FXML
    private Label sampaguita_seed_lbl;
    @FXML
    private Label orchids_seed_lbl;
    @FXML
    private Label sunflower_seed_lbl;
    @FXML
    private Label rose_seed_lbl;

    public InventoryController(UserGameStatsHandler userGameStatsHandler, PlantsHandler plantsHandler) {
        this.userGameStats = userGameStatsHandler.getUserGameStats();
        this.plantsHandler = plantsHandler;
    }

    public void initialize() {
        loadInventoryContents();
    }

    private void loadInventoryContents() {
        gold_lbl.setText(String.valueOf(userGameStats.getGold().getAmount()));
        star_lbl.setText(String.valueOf(userGameStats.getStar().getAmount()));
        banana_lbl.setText(String.valueOf(plantsHandler.getPlantsInventory().banana()));
        pineapple_lbl.setText(String.valueOf(plantsHandler.getPlantsInventory().pineapple()));
        strawberry_lbl.setText(String.valueOf(plantsHandler.getPlantsInventory().strawberry()));
        apple_lbl.setText(String.valueOf(plantsHandler.getPlantsInventory().apple()));
        sampaguita_lbl.setText(String.valueOf(plantsHandler.getPlantsInventory().sampaguita()));
        orchids_lbl.setText(String.valueOf(plantsHandler.getPlantsInventory().orchids()));
        sunflower_lbl.setText(String.valueOf(plantsHandler.getPlantsInventory().sunflower()));
        rose_lbl.setText(String.valueOf(plantsHandler.getPlantsInventory().rose()));
        banana_seed_lbl.setText(String.valueOf(plantsHandler.getSeedsInventory().banana()));
        pineapple_seed_lbl.setText(String.valueOf(plantsHandler.getSeedsInventory().pineapple()));
        strawberry_seed_lbl.setText(String.valueOf(plantsHandler.getSeedsInventory().strawberry()));
        apple_seed_lbl.setText(String.valueOf(plantsHandler.getSeedsInventory().apple()));
        sampaguita_seed_lbl.setText(String.valueOf(plantsHandler.getSeedsInventory().sampaguita()));
        orchids_seed_lbl.setText(String.valueOf(plantsHandler.getSeedsInventory().orchids()));
        sunflower_seed_lbl.setText(String.valueOf(plantsHandler.getSeedsInventory().sunflower()));
        rose_seed_lbl.setText(String.valueOf(plantsHandler.getSeedsInventory().rose()));
    }
}
