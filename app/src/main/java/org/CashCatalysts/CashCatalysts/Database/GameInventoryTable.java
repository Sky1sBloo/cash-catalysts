package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.game.GameInventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameInventoryTable extends DbTable {
    private final UsersTable usersTable;
    public GameInventoryTable(Connection connection, UsersTable usersTable) throws SQLException {
        super(connection);
        this.usersTable = usersTable;

        DbField[] fields = {
            new DbField("userId", "INTEGER"),
            new DbField("gold", "INTEGER"),
            new DbField("star", "INTEGER"),
            new DbField("water", "INTEGER")
        };

        String[] constraints = {
            "FOREIGN KEY (userId) REFERENCES users(user_id)"
        };
        super.createTable("game_inventory", fields, constraints);
    }

    public void addGameInventory(int userId) throws SQLException {
        if (usersTable.getUser(userId) == null) {
            throw new SQLException("User does not exist");
        }
        String sql = "INSERT INTO game_inventory (userId, gold, star, water) VALUES(?, 0, 0, 0);";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        preparedStatement.executeUpdate();
    }

    public void updateGameInventory(GameInventory gameInventory) throws SQLException {
        String sql = "UPDATE game_inventory SET gold = ?, star = ?, water = ? WHERE userId = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, gameInventory.getGold().getAmount());
        preparedStatement.setInt(2, gameInventory.getStar().getAmount());
        preparedStatement.setInt(3, gameInventory.getWater().getAmount());
        preparedStatement.setInt(4, gameInventory.getUserId());

        preparedStatement.executeUpdate();
    }

    public GameInventory getGameInventory(int userId) throws SQLException {
        String sql = "SELECT * FROM game_inventory WHERE userId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }
        return new GameInventory(
            userId,
            resultSet.getInt("gold"),
            resultSet.getInt("star"),
            resultSet.getInt("water")
        );
    }
}
