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
    private int strawberry;
    private int apple;
    private int sampaguita;
    private int orchids;
    private int sunflower;
    private int rose;

    // For seeds
    private int bananaSeed;
    private int pineappleSeed;
    private int strawberrySeed;
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
            strawberry = userPlantsInventory.strawberry();
            apple = userPlantsInventory.apple();
            sampaguita = userPlantsInventory.sampaguita();
            orchids = userPlantsInventory.orchids();
            sunflower = userPlantsInventory.sunflower();
            rose = userPlantsInventory.rose();

            UserPlantsInventory userSeedsInventory = seedsInventoryTable.getSeedsInventory(userId);
            bananaSeed = userSeedsInventory.banana();
            pineappleSeed = userSeedsInventory.pineapple();
            strawberrySeed = userSeedsInventory.strawberry();
            appleSeed = userSeedsInventory.apple();
            sampaguitaSeed = userSeedsInventory.sampaguita();
            orchidsSeed = userSeedsInventory.orchids();
            sunflowerSeed = userSeedsInventory.sunflower();
            roseSeed = userSeedsInventory.rose();

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
            case BANANA -> {
                if (banana == 0) {
                    throw new IllegalArgumentException("Cannot remove a banana plant when there are no banana plants");
                }
                banana--;
            }
            case PINEAPPLE -> {
                if (pineapple == 0) {
                    throw new IllegalArgumentException("Cannot remove a pineapple plant when there are no pineapple plants");
                }
                pineapple--;
            }
            case STRAWBERRY -> {
                if (strawberry == 0) {
                    throw new IllegalArgumentException("Cannot remove a strawberry plant when there are no strawberry plants");
                }
                strawberry--;
            }
            case APPLE -> {
                if (apple == 0) {
                    throw new IllegalArgumentException("Cannot remove an apple plant when there are no apple plants");
                }
                apple--;
            }
            case SAMPAGUITA -> {
                if (sampaguita == 0) {
                    throw new IllegalArgumentException("Cannot remove a sampaguita plant when there are no sampaguita plants");
                }
                sampaguita--;
            }
            case ORCHIDS -> {
                if (orchids == 0) {
                    throw new IllegalArgumentException("Cannot remove an orchids plant when there are no orchids plants");
                }
                orchids--;
            }
            case SUNFLOWER -> {
                if (sunflower == 0) {
                    throw new IllegalArgumentException("Cannot remove a sunflower plant when there are no sunflower plants");
                }
                sunflower--;
            }
            case ROSE -> {
                if (rose == 0) {
                    throw new IllegalArgumentException("Cannot remove a rose plant when there are no rose plants");
                }
                rose--;
            }
            case NONE -> throw new IllegalArgumentException("Cannot remove NONE plant");
        }
    }

    /**
     * Updates the plants inventory in the database
     * Suggested to be called after adding or removing plants especially in bulk
     */
    public void updatePlantsInventory() {
        try {
            plantsInventoryTable.updatePlantsInventory(new UserPlantsInventory(userId, banana, pineapple, strawberry, apple, sampaguita, orchids, sunflower, rose));
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
            case STRAWBERRY -> strawberrySeed++;
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
            case BANANA -> {
                if (bananaSeed == 0) {
                    throw new IllegalArgumentException("Cannot remove a banana seed when there are no banana seeds");
                }
                bananaSeed--;
            }
            case PINEAPPLE -> {
                if (pineappleSeed == 0) {
                    throw new IllegalArgumentException("Cannot remove a pineapple seed when there are no pineapple seeds");
                }
                pineappleSeed--;
            }
            case STRAWBERRY -> {
                if (strawberrySeed == 0) {
                    throw new IllegalArgumentException("Cannot remove a strawberry seed when there are no strawberry seeds");
                }
                strawberrySeed--;
            }
            case APPLE -> {
                if (appleSeed == 0) {
                    throw new IllegalArgumentException("Cannot remove an apple seed when there are no apple seeds");
                }
                appleSeed--;
            }
            case SAMPAGUITA -> {
                if (sampaguitaSeed == 0) {
                    throw new IllegalArgumentException("Cannot remove a sampaguita seed when there are no sampaguita seeds");
                }
                sampaguitaSeed--;
            }
            case ORCHIDS -> {
                if (orchidsSeed == 0) {
                    throw new IllegalArgumentException("Cannot remove an orchids seed when there are no orchids seeds");
                }
                orchidsSeed--;
            }
            case SUNFLOWER -> {
                if (sunflowerSeed == 0) {
                    throw new IllegalArgumentException("Cannot remove a sunflower seed when there are no sunflower seeds");
                }
                sunflowerSeed--;
            }
            case ROSE -> {
                if (roseSeed == 0) {
                    throw new IllegalArgumentException("Cannot remove a rose seed when there are no rose seeds");
                }
                roseSeed--;
            }
            case NONE -> throw new IllegalArgumentException("Cannot remove NONE seed");
        }
    }
    /**
     * Updates the seeds inventory in the database
     * Suggested to be called after adding or removing seeds especially in bulk
     */
    public void updateSeedsInventory() {
        try {
            seedsInventoryTable.updateSeedsInventory(new UserPlantsInventory(userId, bananaSeed, pineappleSeed, strawberrySeed, appleSeed, sampaguitaSeed, orchidsSeed, sunflowerSeed, roseSeed));
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
