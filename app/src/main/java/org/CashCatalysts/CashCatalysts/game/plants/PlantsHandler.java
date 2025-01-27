package org.CashCatalysts.CashCatalysts.game.plants;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Database.PlantsInventoryTable;
import org.CashCatalysts.CashCatalysts.Database.SeedsInventoryTable;

import java.sql.SQLException;

/**
 * Handles the plants inventory of a user (including seeds)
 */
public class PlantsHandler {
    private final PlantsInventoryTable plantsInventoryTable;
    private final SeedsInventoryTable seedsInventoryTable;
    private final int userId;

    // For normal plants
    private int banana;
    private int pineapple;
    private int apple;
    private int sampaguita;
    private int orchids;
    private int sunflower;
    private int rose;

    // For seeds
    private int bananaSeed;
    private int pineappleSeed;
    private int appleSeed;
    private int sampaguitaSeed;
    private int orchidsSeed;
    private int sunflowerSeed;
    private int roseSeed;

    /**
     * Creates a new PlantsHandler
     * Creates a new plants inventory for the user if it doesn't exist
     */
    public PlantsHandler(int userId, DatabaseHandler databaseHandler) {
        this.plantsInventoryTable = databaseHandler.getPlantsInventoryTable();
        this.seedsInventoryTable = databaseHandler.getSeedsInventoryTable();
        this.userId = userId;

        try {
            if (plantsInventoryTable.getPlantsInventory(userId) == null) {
                plantsInventoryTable.addPlantsInventory(userId);
            }
            if (seedsInventoryTable.getSeedsInventory(userId) == null) {
                seedsInventoryTable.addSeedsInventory(userId);
            }
            UserPlantsInventory userPlantsInventory = plantsInventoryTable.getPlantsInventory(userId);
            banana = userPlantsInventory.banana();
            pineapple = userPlantsInventory.pineapple();
            apple = userPlantsInventory.apple();
            sampaguita = userPlantsInventory.sampaguita();
            orchids = userPlantsInventory.orchids();
            sunflower = userPlantsInventory.sunflower();
            rose = userPlantsInventory.rose();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPlant(Plant plant) {
        switch (plant) {
            case BANANA -> banana++;
            case PINEAPPLE -> pineapple++;
            case APPLE -> apple++;
            case SAMPAGUITA -> sampaguita++;
            case ORCHIDS -> orchids++;
            case SUNFLOWER -> sunflower++;
            case ROSE -> rose++;
            case NONE -> throw new IllegalArgumentException("Cannot add NONE plant");
        }
    }

    public void removePlant(Plant plant) {
        switch (plant) {
            case BANANA -> banana--;
            case PINEAPPLE -> pineapple--;
            case APPLE -> apple--;
            case SAMPAGUITA -> sampaguita--;
            case ORCHIDS -> orchids--;
            case SUNFLOWER -> sunflower--;
            case ROSE -> rose--;
            case NONE -> throw new IllegalArgumentException("Cannot remove NONE plant");
        }
    }

    /**
     * Updates the plants inventory in the database
     * Suggested to be called after adding or removing plants especially in bulk
     */
    public void updatePlantsInventory() {
        try {
            plantsInventoryTable.updatePlantsInventory(new UserPlantsInventory(userId, banana, pineapple, apple, sampaguita, orchids, sunflower, rose));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the plants inventory of the user
     * Note: This method doesn't use the instance variables of this class
     */
    public UserPlantsInventory getPlantsInventory() {
        try {
            return plantsInventoryTable.getPlantsInventory(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSeed(Plant seed) {
        switch (seed) {
            case BANANA -> bananaSeed++;
            case PINEAPPLE -> pineappleSeed++;
            case APPLE -> appleSeed++;
            case SAMPAGUITA -> sampaguitaSeed++;
            case ORCHIDS -> orchidsSeed++;
            case SUNFLOWER -> sunflowerSeed++;
            case ROSE -> roseSeed++;
            case NONE -> throw new IllegalArgumentException("Cannot add NONE seed");
        }
    }

    public void removeSeed(Plant seed) {
        switch (seed) {
            case BANANA -> bananaSeed--;
            case PINEAPPLE -> pineappleSeed--;
            case APPLE -> appleSeed--;
            case SAMPAGUITA -> sampaguitaSeed--;
            case ORCHIDS -> orchidsSeed--;
            case SUNFLOWER -> sunflowerSeed--;
            case ROSE -> roseSeed--;
            case NONE -> throw new IllegalArgumentException("Cannot remove NONE seed");
        }
    }
    /**
     * Updates the seeds inventory in the database
     * Suggested to be called after adding or removing seeds especially in bulk
     */
    public void updateSeedsInventory() {
        try {
            seedsInventoryTable.updateSeedsInventory(new UserPlantsInventory(userId, bananaSeed, pineappleSeed, appleSeed, sampaguitaSeed, orchidsSeed, sunflowerSeed, roseSeed));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the seeds inventory of the user
     * Note: This method doesn't use the instance variables of this class
     */
    public UserPlantsInventory getSeedsInventory() {
        try {
            return seedsInventoryTable.getSeedsInventory(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
