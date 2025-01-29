package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.UserProfile.UsersHandler;
import org.CashCatalysts.CashCatalysts.game.Land;
import org.CashCatalysts.CashCatalysts.game.plants.Plant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LandsTableTests {
    private LandsTable landsTable;
    private UsersTable usersTable;
    private int userId;

    @BeforeEach
    public void loadDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        usersTable = new UsersTable(connection);
        userId = usersTable.registerUser(UsersHandler.createUser("testUser"));
        landsTable = new LandsTable(connection);
    }

    @Test
    public void testAddLand() throws SQLException {
        Land land = new Land(userId, Plant.STRAWBERRY, true, false, 1);
        landsTable.addLand(land);
        Land retrievedLand = landsTable.getLand(userId, landsTable.getHighestLandId(userId));
        Assertions.assertEquals(land.getUserId(), retrievedLand.getUserId());
        Assertions.assertEquals(land.getPlantType(), retrievedLand.getPlantType());
        Assertions.assertEquals(land.hasPot(), retrievedLand.hasPot());
        Assertions.assertEquals(land.getPosition(), retrievedLand.getPosition());
        Assertions.assertEquals(land.getPosition(), landsTable.getHighestLandId(userId));
    }

    @Test
    public void testUpdateLand() throws SQLException {
        Land land = new Land(userId, Plant.STRAWBERRY, true, false, landsTable.getHighestLandId(userId));
        int id = landsTable.addLand(land);
        land.setPlantType(Plant.APPLE);
        land.setHasPot(false);
        landsTable.updateLand(land);
        Land retrievedLandById = landsTable.getLand(id);
        Land retrievedLand = landsTable.getLand(userId, landsTable.getHighestLandId(userId));
        Assertions.assertEquals(land.getUserId(), retrievedLand.getUserId());
        Assertions.assertEquals(land.getPlantType(), retrievedLand.getPlantType());
        Assertions.assertEquals(land.hasPot(), retrievedLand.hasPot());
        Assertions.assertEquals(land.getPosition(), retrievedLand.getPosition());

        Assertions.assertEquals(land.getUserId(), retrievedLandById.getUserId());
        Assertions.assertEquals(land.getPlantType(), retrievedLandById.getPlantType());
        Assertions.assertEquals(land.hasPot(), retrievedLandById.hasPot());
        Assertions.assertEquals(land.getPosition(), retrievedLandById.getPosition());
    }

    @Test
    public void testMultipleUsersLand() throws SQLException {
        int userId2 = usersTable.registerUser(UsersHandler.createUser("testUser2"));
        Land land = new Land(userId, Plant.STRAWBERRY, true, false, landsTable.getHighestLandId(userId));
        landsTable.addLand(land);
        Land land2 = new Land(userId2, Plant.APPLE, false, false, landsTable.getHighestLandId(userId2));
        landsTable.addLand(land2);
        Land retrievedLand = landsTable.getLand(userId, landsTable.getHighestLandId(userId));
        Land retrievedLand2 = landsTable.getLand(userId + 1, landsTable.getHighestLandId(userId2));
        Assertions.assertEquals(land.getUserId(), retrievedLand.getUserId());
        Assertions.assertEquals(land.getPlantType(), retrievedLand.getPlantType());
        Assertions.assertEquals(land.hasPot(), retrievedLand.hasPot());
        Assertions.assertEquals(land.getPosition(), retrievedLand.getPosition());

        Assertions.assertEquals(land2.getUserId(), retrievedLand2.getUserId());
        Assertions.assertEquals(land2.getPlantType(), retrievedLand2.getPlantType());
        Assertions.assertEquals(land2.hasPot(), retrievedLand2.hasPot());
        Assertions.assertEquals(land2.getPosition(), retrievedLand2.getPosition());
    }

    @Test
    public void testNullOnNonexistentLand() throws SQLException {
        Land retrievedLand = landsTable.getLand(userId + 1, landsTable.getHighestLandId(userId));
        Assertions.assertNull(retrievedLand);
    }

    @Test
    public void testNullOnNonexistentLandById() throws SQLException {
        Land retrievedLand = landsTable.getLand(0);
        Assertions.assertNull(retrievedLand);
    }

    @Test
    public void testNullOnNonexistentLandByUser() throws SQLException {
        Land land = new Land(userId, Plant.STRAWBERRY, true, false, landsTable.getHighestLandId(userId));
        landsTable.addLand(land);
        Land retrievedLand = landsTable.getLand(userId + 1, landsTable.getHighestLandId(userId));
        Assertions.assertNull(retrievedLand);
    }
}
