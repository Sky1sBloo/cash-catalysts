package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.game.chests.UserChestsInventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChestsInventoryTable extends DbTable {
    public ChestsInventoryTable(Connection connection) {
        super(connection);
    }

    public void addChestsInventory(int userId) throws SQLException {
        String sql = "INSERT INTO game_inventory (userId, normal_chests_amount, rare_chests_amount, epic_chests_amount) VALUES(?, 0, 0, 0);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        preparedStatement.executeUpdate();
    }

    public UserChestsInventory getChestsAmount(int userId) throws SQLException {
        String sql = "SELECT normal_chests_amount, rare_chests_amount, epic_chests_amount WHERE userId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }

        return new UserChestsInventory(
                userId,
                resultSet.getInt("normal_chests_amount"),
                resultSet.getInt("rare_chests_amount"),
                resultSet.getInt("epic_chests_amount")
        );
    }

    public void updateChestsAmount(UserChestsInventory userChestsInventory) throws SQLException {
        String sql = "UPDATE game_inventory SET normal_chests_amount = ?, rare_chests_amount = ?, epic_chests_amount = ? WHERE userId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userChestsInventory.normalChestsAmount());
        preparedStatement.setInt(2, userChestsInventory.rareChestsAmount());
        preparedStatement.setInt(3, userChestsInventory.epicChestsAmount());
        preparedStatement.setInt(4, userChestsInventory.userId());

        preparedStatement.executeUpdate();
    }
}
