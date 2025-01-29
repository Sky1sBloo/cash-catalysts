package org.CashCatalysts.CashCatalysts.controllers;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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

    enum BuyableWith {
        GOLD,
        FLOWERS,
        FALSE
    }

    @FXML
    private BorderPane market_pane;

    @FXML
    private Button cheat_btn;

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

        cheat_btn.setOnAction(ignore -> cheatStats());
        refresh();
    }

    private void refresh() {
        loadStats();
        sell_banana_btn.setDisable(plantsHandler.getPlantsInventory().banana() == 0);
        sell_pineapple_btn.setDisable(plantsHandler.getPlantsInventory().pineapple() == 0);
        sell_strawberry_btn.setDisable(plantsHandler.getPlantsInventory().strawberry() == 0);
        sell_apple_btn.setDisable(plantsHandler.getPlantsInventory().apple() == 0);
        trade_epic_chest_btn.setDisable(isChestTradeable(ChestRarity.EPIC) == BuyableWith.FALSE);
        trade_rare_chest_btn.setDisable(isChestTradeable(ChestRarity.RARE) == BuyableWith.FALSE);
        trade_normal_chest_btn.setDisable(isChestTradeable(ChestRarity.NORMAL) == BuyableWith.FALSE);
        buy_pot.setDisable(!isPotTradeable());
    }

    private void loadStats() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../forms/StatsBar.fxml"));
        loader.setController(new StatsBarController(userGameStatsHandler, chestHandler));
        try {
            market_pane.setTop(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        banana_lbl.setText(String.valueOf(plantsHandler.getPlantsInventory().banana()));
        pineapple_lbl.setText(String.valueOf(plantsHandler.getPlantsInventory().pineapple()));
        strawberry_lbl.setText(String.valueOf(plantsHandler.getPlantsInventory().strawberry()));
        apple_lbl.setText(String.valueOf(plantsHandler.getPlantsInventory().apple()));
    }

    private void tradeChest(ChestRarity rarity) {
        if (!exchangeChest(rarity)) {
            return;
        }
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
        refresh();
    }

    private void buyPot() {
        if (!isPotTradeable()) {
            return;
        }
        userGameStatsHandler.getUserGameStats().getPots().add(1);
        userGameStatsHandler.getUserGameStats().getStar().exchange(50);
        userGameStatsHandler.updateUserGameStats();
        refresh();
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
        userGameStatsHandler.updateUserGameStats();
        plantsHandler.updatePlantsInventory();
        refresh();
    }

    private void cheatStats() {
        userGameStatsHandler.getUserGameStats().getGold().set(999);
        userGameStatsHandler.getUserGameStats().getStar().set(999);
        userGameStatsHandler.updateUserGameStats();
        refresh();
    }


    /**
     * Check if the chest is tradeable with flowers or gold
     * only check and not exchange
     * Todo: This should be separated in the future
     */
    private BuyableWith isChestTradeable(ChestRarity rarity) {
        switch (rarity) {
            case NORMAL -> {
                if (plantsHandler.getPlantsInventory().sampaguita() >= 3) {
                    return BuyableWith.FLOWERS;
                }
                if (userGameStatsHandler.getUserGameStats().getGold().getAmount() >= 50) {
                    return BuyableWith.GOLD;
                }
                return BuyableWith.FALSE;
            }
            case RARE -> {
                if (plantsHandler.getPlantsInventory().sampaguita() >= 1 &&
                        plantsHandler.getPlantsInventory().sunflower() >= 1 &&
                        plantsHandler.getPlantsInventory().orchids() >= 2) {
                    return BuyableWith.FLOWERS;
                }
                if (userGameStatsHandler.getUserGameStats().getGold().getAmount() >= 75) {
                    return BuyableWith.GOLD;
                }
                return BuyableWith.FALSE;
            }
            case EPIC -> {
                if (plantsHandler.getPlantsInventory().sampaguita() >= 3 &&
                        plantsHandler.getPlantsInventory().sunflower() >= 1 &&
                        plantsHandler.getPlantsInventory().orchids() >= 2 &&
                        plantsHandler.getPlantsInventory().rose() >= 1) {
                    return BuyableWith.FLOWERS;
                }
                if (userGameStatsHandler.getUserGameStats().getGold().getAmount() >= 150) {
                    return BuyableWith.GOLD;
                }
                return BuyableWith.FALSE;
            }
        }
        return BuyableWith.FALSE;
    }


    /**
     * Exchange the chest with flowers or gold
     * @return true if exchange is successful, false otherwise
     * Todo: This should be separated in the future
     */
    private boolean exchangeChest(ChestRarity rarity) {
        switch (rarity) {
            case NORMAL -> {
                if (isChestTradeable(ChestRarity.NORMAL) == BuyableWith.GOLD) {
                    userGameStatsHandler.getUserGameStats().getGold().exchange(50);
                    userGameStatsHandler.updateUserGameStats();
                } else if (isChestTradeable(ChestRarity.NORMAL) == BuyableWith.FLOWERS) {
                    for (int i = 0; i < 3; i++) {
                        plantsHandler.removePlant(Plant.SAMPAGUITA);
                    }
                    plantsHandler.updatePlantsInventory();
                } else {
                    return false;
                }
            }
            case RARE -> {
                if (isChestTradeable(ChestRarity.RARE) == BuyableWith.GOLD) {
                    userGameStatsHandler.getUserGameStats().getGold().exchange(75);
                    userGameStatsHandler.updateUserGameStats();
                } else if (isChestTradeable(ChestRarity.RARE) == BuyableWith.FLOWERS) {
                    for (int i = 0; i < 2; i++) {
                        plantsHandler.removePlant(Plant.ORCHIDS);
                    }
                    plantsHandler.removePlant(Plant.SAMPAGUITA);
                    plantsHandler.removePlant(Plant.SUNFLOWER);
                    plantsHandler.updatePlantsInventory();
                } else {
                    return false;
                }
            }
            case EPIC -> {
                if (isChestTradeable(ChestRarity.EPIC) == BuyableWith.GOLD) {
                    userGameStatsHandler.getUserGameStats().getGold().exchange(150);
                    userGameStatsHandler.updateUserGameStats();
                } else if (isChestTradeable(ChestRarity.EPIC) == BuyableWith.FLOWERS) {
                    for (int i = 0; i < 3; i++) {
                        plantsHandler.removePlant(Plant.SAMPAGUITA);
                    }
                    plantsHandler.removePlant(Plant.SUNFLOWER);
                    for (int i = 0; i < 2; i++) {
                        plantsHandler.removePlant(Plant.ORCHIDS);
                    }
                    plantsHandler.removePlant(Plant.ROSE);
                    plantsHandler.updatePlantsInventory();
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isPotTradeable() {
        return userGameStatsHandler.getUserGameStats().getStar().getAmount() >= 50;
    }
}
