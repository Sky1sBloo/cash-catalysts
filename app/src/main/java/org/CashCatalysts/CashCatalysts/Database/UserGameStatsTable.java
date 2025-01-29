package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.game.UserGameStats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserGameStatsTable extends DbTable {
    private final UsersTable usersTable;

    public UserGameStatsTable(Connection connection, UsersTable usersTable) throws SQLException {
        super(connection);
        this.usersTable = usersTable;

        DbField[] fields = {
                new DbField("userId", "INTEGER"),
                new DbField("gold", "INTEGER", "NOT NULL"),
                new DbField("star", "INTEGER", "NOT NULL"),
                new DbField("water", "INTEGER", "NOT NULL"),
                new DbField("normal_chests_amount", "INTEGER", "NOT NULL"),
                new DbField("rare_chests_amount", "INTEGER", "NOT NULL"),
                new DbField("epic_chests_amount", "INTEGER", "NOT NULL"),
                new DbField("pots", "INTEGER", "NOT NULL"),
                new DbField("water_cooldown_id", "INTEGER")
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
        String sql = "INSERT INTO game_inventory (userId, gold, star, water, normal_chests_amount, rare_chests_amount, epic_chests_amount, pots, water_cooldown_id) VALUES(?, 0, 0, 0, 0, 0, 0, 0, null);";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        preparedStatement.executeUpdate();
    }

    public void updateGameInventory(UserGameStats userGameStats) throws SQLException {
        String sql = "UPDATE game_inventory SET gold = ?, star = ?, water = ?, normal_chests_amount = ?, rare_chests_amount = ?, epic_chests_amount = ?, pots = ?, water_cooldown_id = ? WHERE userId = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userGameStats.getGold().getAmount());
        preparedStatement.setInt(2, userGameStats.getStar().getAmount());
        preparedStatement.setInt(3, userGameStats.getWater().getAmount());
        preparedStatement.setInt(4, userGameStats.getNormalChests().getAmount());
        preparedStatement.setInt(5, userGameStats.getRareChests().getAmount());
        preparedStatement.setInt(6, userGameStats.getEpicChests().getAmount());
        preparedStatement.setInt(7, userGameStats.getPots().getAmount());
        if (userGameStats.getWaterCooldownId() == null) {
            preparedStatement.setNull(8, java.sql.Types.INTEGER);
        } else {
            preparedStatement.setInt(8, userGameStats.getWaterCooldownId());
        }
        preparedStatement.setInt(9, userGameStats.getUserId());

        preparedStatement.executeUpdate();
    }

    public UserGameStats getGameInventory(int userId) throws SQLException {
        String sql = "SELECT * FROM game_inventory WHERE userId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }
        Integer waterCooldownId = resultSet.getInt("water_cooldown_id");
        if (resultSet.wasNull()) {
            waterCooldownId = null;
        }
        return new UserGameStats(
                userId,
                resultSet.getInt("gold"),
                resultSet.getInt("star"),
                resultSet.getInt("water"),
                resultSet.getInt("normal_chests_amount"),
                resultSet.getInt("rare_chests_amount"),
                resultSet.getInt("epic_chests_amount"),
                resultSet.getInt("pots"),
                waterCooldownId
        );
    }
}
