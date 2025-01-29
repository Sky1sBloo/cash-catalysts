package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.CashCatalysts.CashCatalysts.game.Land;
import org.CashCatalysts.CashCatalysts.game.LandHandler;
import org.CashCatalysts.CashCatalysts.game.UserGameStatsHandler;
import org.CashCatalysts.CashCatalysts.game.plants.Plant;
import org.CashCatalysts.CashCatalysts.game.plants.PlantGrowingSystem;
import org.CashCatalysts.CashCatalysts.game.plants.PlantsHandler;

public class LandController {
    private final LandHandler landHandler;
    private final UserGameStatsHandler userGameStatsHandler;
    private final PlantsHandler plantsHandler;
    private final PlantGrowingSystem plantGrowingSystem;
    private final int landPosition;
    //
    private final Runnable reloadStatsCallback;

    @FXML
    private ComboBox<Plant> seed_selection;
    @FXML
    private Label plant_type;
    @FXML
    private Pane pot_pane;
    @FXML
    private Pane plant_pane;
    @FXML
    private Button add_pot_btn;
    @FXML
    private Button plant_btn;
    @FXML
    private Button harvest_btn;

    public LandController(LandHandler landHandler, UserGameStatsHandler userGameStatsHandler, PlantsHandler plantsHandler, PlantGrowingSystem plantGrowingSystem, int landPosition, Runnable reloadStatsCallback) {
        this.landHandler = landHandler;
        this.userGameStatsHandler = userGameStatsHandler;
        this.plantsHandler = plantsHandler;
        this.plantGrowingSystem = plantGrowingSystem;
        this.landPosition = landPosition;
        this.reloadStatsCallback = reloadStatsCallback;
    }

    public void initialize() {
        seed_selection.getItems().addAll(Plant.values());
        seed_selection.getSelectionModel().selectLast();
        add_pot_btn.setOnAction(ignore -> addPot());
        plant_btn.setOnAction(ignore -> plantSeed());

        refresh();
    }

    private void refresh() {
        loadPlantContents();
    }

    private void loadPlantContents() {
        Land land = landHandler.getLand(landPosition);
        if (land.hasPot()) {
            plant_type.setText(land.getPlantType().toString());
            pot_pane.setVisible(false);
            plant_pane.setVisible(true);
            harvest_btn.setDisable(!land.getHarvestable());
        } else {
            pot_pane.setVisible(true);
            plant_pane.setVisible(false);
        }
    }

    private void addPot() {
        if (userGameStatsHandler.getUserGameStats().getPots().getAmount() <= 0) {
            return;
        }
        userGameStatsHandler.getUserGameStats().getPots().exchange(1);
        userGameStatsHandler.updateUserGameStats();
        Land land = landHandler.getLand(landPosition);
        landHandler.addPot(land.getPosition());
        refresh();
        reloadStatsCallback.run();
    }

    private void plantSeed() {
        Plant seed = seed_selection.getValue();
        if (seed == Plant.NONE) {
            // Todo: How added seed
        }
        try {
            plantGrowingSystem.plantSeed(landPosition, seed);
            //plantsHandler.removeSeed(seed);
        } catch (IllegalArgumentException ignore) {
            // Todo: how error that no more seeds
            return;
        }

        plantsHandler.updateSeedsInventory();
        /*
        landHandler.plant(landPosition, seed); */
        refresh();
        reloadStatsCallback.run();
    }
}
