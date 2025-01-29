package org.CashCatalysts.CashCatalysts.controllers;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import org.CashCatalysts.CashCatalysts.game.Land;
import org.CashCatalysts.CashCatalysts.game.LandHandler;
import org.CashCatalysts.CashCatalysts.game.UserGameStatsHandler;
import org.CashCatalysts.CashCatalysts.game.WaterAutoFillListener;
import org.CashCatalysts.CashCatalysts.game.plants.PlantGrowingSystem;
import org.CashCatalysts.CashCatalysts.game.plants.PlantsHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class GardenController {
    private final UserGameStatsHandler userGameStatsHandler;
    private final PlantsHandler plantsHandler;
    private final PlantGrowingSystem plantGrowingSystem;
    private final LandHandler landHandler;
    private final WaterAutoFillListener waterAutoFillListener;



    @FXML
    private GridPane land_pane;

    @FXML
    private Label next_fill_lbl;
    @FXML
    private ProgressBar water_bar;
    @FXML
    private Button cheat_water;

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

    public GardenController(UserGameStatsHandler userGameStatsHandler, PlantsHandler plantsHandler, PlantGrowingSystem plantGrowingSystem, LandHandler landHandler, WaterAutoFillListener waterAutoFillListener) {
        this.userGameStatsHandler = userGameStatsHandler;
        this.plantsHandler = plantsHandler;
        this.plantGrowingSystem = plantGrowingSystem;
        this.landHandler = landHandler;
        this.waterAutoFillListener = waterAutoFillListener;
    }

    public void initialize() {
        cheat_water.setOnAction(ignore -> cheatWater());
        loadInventoryContents();
        loadLandContents();

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new javafx.animation.KeyFrame(javafx.util.Duration.seconds(1), event -> {
            waterAutoFillListener.calculatedAutoFill(java.time.LocalDateTime.now());
            loadInventoryContents();
        }));
        timeline.play();
    }

    private void loadLandContents() {
        List<Land> lands = landHandler.getLands();
        for (int i = 0; i < lands.size(); i++) {
            Land land = lands.get(i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../forms/Land.fxml"));
            LandController landController = new LandController(landHandler,
                    userGameStatsHandler,
                    plantsHandler,
                    plantGrowingSystem,
                    land.getPosition(),
                    this::loadInventoryContents);
            loader.setController(landController);
            try {
                land_pane.add(loader.load(), i % 4, i / 4);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadInventoryContents() {
        water_bar.setProgress((double) userGameStatsHandler.getUserGameStats().getWater().getAmount() / waterAutoFillListener.getMaxWaterFill());
        next_fill_lbl.setText(waterAutoFillListener.getTimeRemainingInMinutesSeconds(LocalDateTime.now()));

        plantsHandler.updatePlantsInventory();
        plantsHandler.updateSeedsInventory();
        gold_lbl.setText(String.valueOf(userGameStatsHandler.getUserGameStats().getGold().getAmount()));
        star_lbl.setText(String.valueOf(userGameStatsHandler.getUserGameStats().getStar().getAmount()));
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
        pot_lbl.setText(String.valueOf(userGameStatsHandler.getUserGameStats().getPots().getAmount()));
    }

    private void cheatWater() {
        userGameStatsHandler.getUserGameStats().getWater().set(24);
        loadInventoryContents();
    }
}
