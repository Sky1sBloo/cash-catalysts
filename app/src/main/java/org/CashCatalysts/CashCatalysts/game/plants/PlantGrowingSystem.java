package org.CashCatalysts.CashCatalysts.game.plants;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.game.Land;
import org.CashCatalysts.CashCatalysts.game.LandHandler;
import org.CashCatalysts.CashCatalysts.game.cooldown.Cooldown;
import org.CashCatalysts.CashCatalysts.game.cooldown.CooldownHandler;
import org.CashCatalysts.CashCatalysts.game.UserGameStats;

import java.sql.SQLException;
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

    /**
     * Initializes the system with handlers for plants, cooldowns, land, and user stats.
     */
    public PlantGrowingSystem(int userID, DatabaseHandler databaseHandler){
        this.plantsHandler = new PlantsHandler(userID, databaseHandler);
        this.cooldownHandler = new CooldownHandler(databaseHandler);
        this.landHandler = new LandHandler(userID, databaseHandler);
        this.userGameStats = new UserGameStats(userID);
    }

    /**
     * Waters the plant on the specified land, starting the growth cooldown.
     */
    public void waterPlant(int landPosition, int growthTime){
        Land land = landHandler.getLand(landPosition);
        if(!land.isHasPot() || land.getPlantType() == Plant.NONE){
            throw new IllegalArgumentException("Land must have a pot and a planted seed.");
        }
        LocalDateTime cooldownEnd = LocalDateTime.now().plusMinutes(growthTime);
        Cooldown  cooldown = CooldownHandler.createCooldown(cooldownEnd);
        int cooldownID = cooldownHandler.addCooldown(cooldown);
        cooldownHandler.updateCooldown(cooldown.id(), cooldownEnd);
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
    }

    /**
     * Plants a seed of the specified type in the given land.
     */
    public void plantSeed(int landPosition, Plant plantType) throws SQLException {
        if(plantType == Plant.NONE){
            throw new IllegalArgumentException(("Cannot have plant NONE type."));
        }

        Land land = landHandler.getLand(landPosition);
        if(!land.isHasPot()){
            throw new IllegalArgumentException("Land must have a pot to plant a seed.");
        }

        plantsHandler.removeSeed(plantType);
        plantsHandler.updateSeedsInventory();
        land.setPlantType(plantType);
        landHandler.getLandsTable().updateLand(land);
    }
}