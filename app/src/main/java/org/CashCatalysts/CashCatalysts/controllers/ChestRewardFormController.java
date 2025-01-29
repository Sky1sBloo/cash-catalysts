package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.CashCatalysts.CashCatalysts.game.chests.rewards.ChestDrop;

import java.util.List;

public class ChestRewardFormController {
    private final List<ChestDrop> chestDrops;

    @FXML
    private VBox rewards_list;

    public ChestRewardFormController(List<ChestDrop> chestDrops) {
        this.chestDrops = chestDrops;
    }

    public void initialize() {
        loadChestDropsList();
    }

    private void loadChestDropsList() {
        for (ChestDrop chestDrop : chestDrops) {
            rewards_list.getChildren().add(new Label(chestDrop.plant().name() + " x" + chestDrop.amount()));
        }
    }
}
