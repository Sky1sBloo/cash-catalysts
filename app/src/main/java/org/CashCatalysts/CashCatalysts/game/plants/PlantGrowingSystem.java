package org.CashCatalysts.CashCatalysts.game.plants;

import org.CashCatalysts.CashCatalysts.game.Land;
import org.CashCatalysts.CashCatalysts.game.LandHandler;
import org.CashCatalysts.CashCatalysts.game.cooldown.Cooldown;
import org.CashCatalysts.CashCatalysts.game.cooldown.CooldownHandler;
import org.CashCatalysts.CashCatalysts.game.UserGameStats;
import org.CashCatalysts.CashCatalysts.game.gameaction.GameActionHandler;
import org.CashCatalysts.CashCatalysts.game.gameaction.GameActionType;

import java.sql.SQLException;
import java.time.Duration;
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
        //cooldownHandler.updateCooldown(cooldown.id(), cooldownEnd);
        land.setCooldownId(cooldownID);

        try {
            landHandler.getLandsTable().updateLand(land);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        gameActionHandler.addGameAction(gameActionHandler.createGameAction(GameActionType.USE_WATER, landPosition, LocalDate.now()));
    }

    public void cheatDuration(int landPosition){
        Land land = landHandler.getLand(landPosition);
        if(!land.hasPot() || land.getPlantType() == Plant.NONE){
            throw new IllegalArgumentException("Land must have a pot and a planted seed.");
        }
        LocalDateTime cooldownEnd = LocalDateTime.now();
        Cooldown  cooldown = CooldownHandler.createCooldown(cooldownEnd);
        int cooldownID = cooldownHandler.addCooldown(cooldown);
        //cooldownHandler.updateCooldown(cooldown.id(), cooldownEnd);
        land.setCooldownId(cooldownID);

        try {
            landHandler.getLandsTable().updateLand(land);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        gameActionHandler.addGameAction(gameActionHandler.createGameAction(GameActionType.USE_WATER, landPosition, LocalDate.now()));
    }

    /**
     * Harvests the plant from the specified land, if it is ready (cooldown completed).
     * Updates the plant inventory and the user's stars.
     */
    public void harvestPlant(int landPosition) {
        Land land = landHandler.getLand(landPosition);

        //Cooldown cooldown = cooldownHandler.getCooldown(landPosition); // todo: make lands table store cooldown id
        Cooldown cooldown = cooldownHandler.getCooldown(land.getCooldownId());
        if(cooldown == null || !cooldownHandler.cooldownIsFinished(cooldown.id(), LocalDateTime.now())){
            throw new IllegalStateException("Plant is not ready for harvest yet.");
        }

        plantsHandler.addPlant(land.getPlantType()) ;
        plantsHandler.updatePlantsInventory();
        userGameStats.getStar().add(10);

        land.setPlantType(Plant.NONE);
        land.setCooldownId(null);
        try {
            landHandler.getLandsTable().updateLand(land);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        cooldownHandler.deleteCooldown(cooldown.id());
        gameActionHandler.addGameAction(gameActionHandler.createGameAction(GameActionType.HARVEST_PLANT, landPosition, LocalDate.now()));
    }

    public boolean isPlantHarvestable(int landPosition) {
        Land land = landHandler.getLand(landPosition);
        if (land.getCooldownId() == null) {
            return false;
        }
        Cooldown cooldown = cooldownHandler.getCooldown(land.getCooldownId());
        return cooldown != null && cooldownHandler.cooldownIsFinished(cooldown.id(), LocalDateTime.now());
    }

    public Duration getPlantTimeRemainingDuration(int landPosition) {
        Land land = landHandler.getLand(landPosition);
        if (land.getCooldownId() == null) {
            return Duration.ZERO;
        }
        Cooldown cooldown = cooldownHandler.getCooldown(land.getCooldownId());
        if (cooldown == null) {
            return Duration.ZERO;
        }
        LocalDateTime now = LocalDateTime.now();
        if (cooldownHandler.cooldownIsFinished(cooldown.id(), now)) {
            return Duration.ZERO;
        }
        return Duration.between(now, cooldown.cooldownEnd());
    }

    public String getPlantTimeRemaining(int landPosition) {
        Land land = landHandler.getLand(landPosition);
        if (land.getCooldownId() == null) {
            return "Needs water";
        }
        Cooldown cooldown = cooldownHandler.getCooldown(land.getCooldownId());
        if (cooldown == null) {
            return "0:00";
        }
        LocalDateTime now = LocalDateTime.now();
        if (cooldownHandler.cooldownIsFinished(cooldown.id(), now)) {
            return "Harvestable";
        }
        long seconds = now.until(cooldown.cooldownEnd(), java.time.temporal.ChronoUnit.SECONDS);
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    /**
     * Plants a seed of the specified type in the given land.
     */
    public void plantSeed(int landPosition, Plant plantType) {
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
        try {
            landHandler.getLandsTable().updateLand(land);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        gameActionHandler.addGameAction(gameActionHandler.createGameAction(GameActionType.PLANT_SEED, landPosition, LocalDate.now()));
    }
}