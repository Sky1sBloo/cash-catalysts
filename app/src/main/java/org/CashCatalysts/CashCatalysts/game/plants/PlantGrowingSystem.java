package org.CashCatalysts.CashCatalysts.game.plants;

import org.CashCatalysts.CashCatalysts.game.Land;
import org.CashCatalysts.CashCatalysts.game.LandHandler;
import org.CashCatalysts.CashCatalysts.game.cooldown.Cooldown;
import org.CashCatalysts.CashCatalysts.game.cooldown.CooldownHandler;
import org.CashCatalysts.CashCatalysts.game.UserGameStats;
import org.CashCatalysts.CashCatalysts.game.gameaction.GameActionHandler;
import org.CashCatalysts.CashCatalysts.game.gameaction.GameActionType;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Handles plant lifecycle actions: watering, harvesting, and planting seeds.
 * Manages cooldowns, plant inventory, and land state.
 */
public class PlantGrowingSystem {
    private final PlantsHandler plantsHandler;
    private final CooldownHandler cooldownHandler;
    private final LandHandler landHandler;
    private final UserGameStats userGameStats;
    private final GameActionHandler gameActionHandler;

    /**
     * Initializes the system with handlers for plants, cooldowns, land, and user stats.
     */
    public PlantGrowingSystem(PlantsHandler plantsHandler, CooldownHandler cooldownHandler, LandHandler landHandler, UserGameStats userGameStats, GameActionHandler gameActionHandler){
        this.plantsHandler = plantsHandler;
        this.cooldownHandler = cooldownHandler;
        this.landHandler = landHandler;
        this.userGameStats = userGameStats;
        this.gameActionHandler = gameActionHandler;
    }

    /**
     * Waters the plant on the specified land, starting the growth cooldown.
     */
    public void waterPlant(int landPosition, int growthTime){
        Land land = landHandler.getLand(landPosition);
        if(!land.hasPot() || land.getPlantType() == Plant.NONE){
            throw new IllegalArgumentException("Land must have a pot and a planted seed.");
        }
        LocalDateTime cooldownEnd = LocalDateTime.now().plusMinutes(growthTime);
        Cooldown  cooldown = CooldownHandler.createCooldown(cooldownEnd);
        int cooldownID = cooldownHandler.addCooldown(cooldown);
        cooldownHandler.updateCooldown(cooldown.id(), cooldownEnd);
        gameActionHandler.addGameAction(gameActionHandler.createGameAction(GameActionType.USE_WATER, landPosition, LocalDate.now()));
    }

    /**
     * Harvests the plant from the specified land, if it is ready (cooldown completed).
     * Updates the plant inventory and the user's stars.
     */
    public void harvestPlant(int landPosition) throws SQLException {
        Land land = landHandler.getLand(landPosition);

        Cooldown cooldown = cooldownHandler.getCooldown(landPosition);
        if(cooldown == null || !cooldownHandler.cooldownIsFinished(cooldown.id(), LocalDateTime.now())){
            throw new IllegalStateException("Plant is not ready for harvest yet.");
        }

        plantsHandler.addPlant(land.getPlantType());
        plantsHandler.updatePlantsInventory();
        userGameStats.getStar().add(10);

        land.setPlantType(Plant.NONE);
        landHandler.getLandsTable().updateLand(land);

        cooldownHandler.deleteCooldown(cooldown.id());
        gameActionHandler.addGameAction(gameActionHandler.createGameAction(GameActionType.HARVEST_PLANT, landPosition, LocalDate.now()));
    }

    /**
     * Plants a seed of the specified type in the given land.
     */
    public void plantSeed(int landPosition, Plant plantType) throws SQLException {
        if(plantType == Plant.NONE){
            throw new IllegalArgumentException(("Cannot have plant NONE type."));
        }

        Land land = landHandler.getLand(landPosition);
        if(!land.hasPot()){
            throw new IllegalArgumentException("Land must have a pot to plant a seed.");
        }

        plantsHandler.removeSeed(plantType);
        plantsHandler.updateSeedsInventory();
        land.setPlantType(plantType);
        landHandler.getLandsTable().updateLand(land);
        gameActionHandler.addGameAction(gameActionHandler.createGameAction(GameActionType.PLANT_SEED, landPosition, LocalDate.now()));
    }
}