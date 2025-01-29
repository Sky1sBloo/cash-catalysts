package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.game.plants.UserPlantsInventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SeedsInventoryTable extends DbTable {
    public SeedsInventoryTable(Connection connection) {
        super(connection);

        DbField[] fields = {
                new DbField("userId", "INTEGER"),
                new DbField("banana", "INTEGER"),
                new DbField("pineapple", "INTEGER"),
                new DbField("strawberry", "INTEGER"),
                new DbField("apple", "INTEGER"),
                new DbField("sampaguita", "INTEGER"),
                new DbField("orchids", "INTEGER"),
                new DbField("sunflower", "INTEGER"),
                new DbField("rose", "INTEGER"),
        };
    }

    public void addSeedsInventory(int userId) throws SQLException {
        String sql = "INSERT INTO plants_inventory (userId, banana, pineapple, strawberry, apple, sampaguita, orchids, sunflower, rose) VALUES(?, 0, 0, 0, 0, 0, 0, 0, 0);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        preparedStatement.executeUpdate();
    }

    public void updateSeedsInventory(UserPlantsInventory userPlantsInventory) throws SQLException {
        String sql = "UPDATE plants_inventory SET banana = ?, pineapple = ?, strawberry = ?, apple = ?, sampaguita = ?, orchids = ?, sunflower = ?, rose = ? WHERE userId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userPlantsInventory.banana());
        preparedStatement.setInt(2, userPlantsInventory.pineapple());
        preparedStatement.setInt(3, userPlantsInventory.strawberry());
        preparedStatement.setInt(4, userPlantsInventory.apple());
        preparedStatement.setInt(5, userPlantsInventory.sampaguita());
        preparedStatement.setInt(6, userPlantsInventory.orchids());
        preparedStatement.setInt(7, userPlantsInventory.sunflower());
        preparedStatement.setInt(8, userPlantsInventory.rose());
        preparedStatement.setInt(9, userPlantsInventory.userId());

        preparedStatement.executeUpdate();
    }

    public UserPlantsInventory getSeedsInventory(int userId) throws SQLException {
        String sql = "SELECT * FROM plants_inventory WHERE userId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }

        return new UserPlantsInventory(
                resultSet.getInt("userId"),
                resultSet.getInt("banana"),
                resultSet.getInt("pineapple"),
                resultSet.getInt("strawberry"),
                resultSet.getInt("apple"),
                resultSet.getInt("sampaguita"),
                resultSet.getInt("orchids"),
                resultSet.getInt("sunflower"),
                resultSet.getInt("rose")
        );
    }
}
