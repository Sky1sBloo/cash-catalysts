package org.CashCatalysts.CashCatalysts.game;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.UserProfile.UsersHandler;
import org.CashCatalysts.CashCatalysts.game.plants.Plant;
import org.CashCatalysts.CashCatalysts.game.plants.PlantsHandler;
import org.CashCatalysts.CashCatalysts.game.plants.UserPlantsInventory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class PlantsHandlerTests {
    private int userId;
    private PlantsHandler plantsHandler;
    @BeforeEach
    public void setUp() throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler(":memory:");
        UsersHandler usersHandler = new UsersHandler(databaseHandler);
        userId = usersHandler.registerUser(UsersHandler.createUser("testUser"));
        plantsHandler = new PlantsHandler(userId, databaseHandler);
    }

    @Test
    public void testAddPlant() {
        plantsHandler.addPlant(Plant.BANANA);
        plantsHandler.addPlant(Plant.BANANA);
        plantsHandler.addPlant(Plant.PINEAPPLE);
        plantsHandler.addPlant(Plant.APPLE);
        plantsHandler.addPlant(Plant.SAMPAGUITA);
        plantsHandler.addPlant(Plant.ORCHIDS);
        plantsHandler.addPlant(Plant.SUNFLOWER);
        plantsHandler.addPlant(Plant.ROSE);
        plantsHandler.updatePlantsInventory();
        UserPlantsInventory userPlantsInventory = plantsHandler.getPlantsInventory();
        UserPlantsInventory expectedUserPlantsInventory = new UserPlantsInventory(userId, 2, 1, 1, 1, 1, 1, 1);
        Assertions.assertEquals(expectedUserPlantsInventory, userPlantsInventory);
    }

    @Test
    public void testRemovePlant() {
        plantsHandler.addPlant(Plant.BANANA);
        plantsHandler.addPlant(Plant.BANANA);
        plantsHandler.addPlant(Plant.PINEAPPLE);
        plantsHandler.addPlant(Plant.APPLE);
        plantsHandler.addPlant(Plant.SAMPAGUITA);
        plantsHandler.addPlant(Plant.ORCHIDS);
        plantsHandler.addPlant(Plant.SUNFLOWER);
        plantsHandler.addPlant(Plant.ROSE);
        plantsHandler.removePlant(Plant.BANANA);
        plantsHandler.removePlant(Plant.PINEAPPLE);
        plantsHandler.removePlant(Plant.APPLE);
        plantsHandler.removePlant(Plant.SAMPAGUITA);
        plantsHandler.removePlant(Plant.ORCHIDS);
        plantsHandler.removePlant(Plant.SUNFLOWER);
        plantsHandler.removePlant(Plant.ROSE);
        plantsHandler.updatePlantsInventory();
        UserPlantsInventory userPlantsInventory = plantsHandler.getPlantsInventory();
        UserPlantsInventory expectedUserPlantsInventory = new UserPlantsInventory(userId, 1, 0, 0, 0, 0, 0, 0);
        Assertions.assertEquals(expectedUserPlantsInventory, userPlantsInventory);
    }

    @Test
    public void testRemovePlantWithNone() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> plantsHandler.removePlant(Plant.NONE));
    }

    @Test
    public void testAddPlantWithNone() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> plantsHandler.addPlant(Plant.NONE));
    }

    @Test
    public void addSeedsTest() {
        plantsHandler.addSeed(Plant.BANANA);
        plantsHandler.addSeed(Plant.BANANA);
        plantsHandler.addSeed(Plant.PINEAPPLE);
        plantsHandler.addSeed(Plant.APPLE);
        plantsHandler.addSeed(Plant.SAMPAGUITA);
        plantsHandler.addSeed(Plant.ORCHIDS);
        plantsHandler.addSeed(Plant.SUNFLOWER);
        plantsHandler.addSeed(Plant.ROSE);
        plantsHandler.updateSeedsInventory();
        UserPlantsInventory userPlantsInventory = plantsHandler.getSeedsInventory();
        UserPlantsInventory expectedUserPlantsInventory = new UserPlantsInventory(userId, 2, 1, 1, 1, 1, 1, 1);
        Assertions.assertEquals(expectedUserPlantsInventory, userPlantsInventory);
    }

    @Test
    public void addSeedsTestWithNone() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> plantsHandler.addSeed(Plant.NONE));
    }

    @Test
    public void deleteSeedsTest() {
        plantsHandler.addSeed(Plant.BANANA);
        plantsHandler.addSeed(Plant.BANANA);
        plantsHandler.addSeed(Plant.PINEAPPLE);
        plantsHandler.addSeed(Plant.APPLE);
        plantsHandler.addSeed(Plant.SAMPAGUITA);
        plantsHandler.addSeed(Plant.ORCHIDS);
        plantsHandler.addSeed(Plant.SUNFLOWER);
        plantsHandler.addSeed(Plant.ROSE);
        plantsHandler.removeSeed(Plant.BANANA);
        plantsHandler.removeSeed(Plant.PINEAPPLE);
        plantsHandler.removeSeed(Plant.APPLE);
        plantsHandler.removeSeed(Plant.SAMPAGUITA);
        plantsHandler.removeSeed(Plant.ORCHIDS);
        plantsHandler.removeSeed(Plant.SUNFLOWER);
        plantsHandler.removeSeed(Plant.ROSE);
        plantsHandler.updateSeedsInventory();
        UserPlantsInventory userPlantsInventory = plantsHandler.getSeedsInventory();
        UserPlantsInventory expectedUserPlantsInventory = new UserPlantsInventory(userId, 1, 0, 0, 0, 0, 0, 0);
        Assertions.assertEquals(expectedUserPlantsInventory, userPlantsInventory);
    }
}
