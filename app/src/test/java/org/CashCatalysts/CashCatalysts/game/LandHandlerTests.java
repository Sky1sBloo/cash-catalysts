package org.CashCatalysts.CashCatalysts.game;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.UserProfile.UsersHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class LandHandlerTests {
    private LandHandler landHandler;

    @BeforeEach
    public void setup() throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler(":memory:");
        UsersHandler usersHandler = new UsersHandler(databaseHandler);
        int userId = usersHandler.registerUser(UsersHandler.createUser("Test"));
        usersHandler.login(usersHandler.getUser(userId));
        landHandler = new LandHandler(userId, databaseHandler);
    }

    @Test
    public void testAddLand() {
        landHandler.addLand();
        landHandler.addLand();
        Assertions.assertEquals(2, landHandler.getLands().size());
    }

    @Test
    public void testAddPot() {
        landHandler.addLand();
        landHandler.addPot(1);
        Assertions.assertTrue(landHandler.getLand(1).isHasPot());
    }

    @Test
    public void testRemovePot() {
        landHandler.addLand();
        landHandler.addPot(1);
        landHandler.removePot(1);
        Assertions.assertFalse(landHandler.getLand(1).isHasPot());
    }

    @Test
    public void testAddPlant() {
        landHandler.addLand();
        landHandler.addPot(1);
        landHandler.plant(1, Plant.ROSE);
        Assertions.assertEquals(Plant.ROSE, landHandler.getLand(1).getPlantType());
        landHandler.removePlant(1);
        Assertions.assertEquals(Plant.NONE, landHandler.getLand(1).getPlantType());
    }

    @Test
    public void testRemovePlant() {
        landHandler.addLand();
        landHandler.addPot(1);
        landHandler.plant(1, Plant.ROSE);
        landHandler.removePlant(1);
        Assertions.assertEquals(Plant.NONE, landHandler.getLand(1).getPlantType());
    }

    @Test
    public void testPlantNoPot() {
        landHandler.addLand();
        landHandler.plant(1, Plant.ROSE);
        Assertions.assertEquals(Plant.NONE, landHandler.getLand(1).getPlantType());
    }
}
