package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.game.Land;
import org.CashCatalysts.CashCatalysts.game.plants.Plant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LandsTable extends DbTable {
    public LandsTable(Connection connection) throws SQLException {
        super(connection);

        DbField[] fields = {
            new DbField("landId", "INTEGER", "PRIMARY KEY NOT NULL"),
            new DbField("userId", "INTEGER", "NOT NULL"),
            new DbField("plantType", "VARCHAR(24)", "NOT NULL"),
            new DbField("hasPot", "INTEGER"),
            new DbField("position", "INTEGER")
        };
        String[] constraints = {
            "FOREIGN KEY (userId) REFERENCES users(user_id)"
        };

        super.createTable("lands", fields, constraints);
    }

    public int addLand(Land land) throws SQLException {
        String sql = "INSERT INTO lands (userId, plantType, hasPot, position) VALUES(?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, land.getUserId());
        preparedStatement.setString(2, land.getPlantType().toString());
        preparedStatement.setInt(3, land.hasPot() ? 1 : 0);
        preparedStatement.setInt(4, land.getPosition());

        preparedStatement.executeUpdate();
        return super.getLastRowId();
    }

    public Land getLand(int userId, int position) throws SQLException {
        String sql = "SELECT * FROM lands WHERE userId = ? AND position = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, position);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }
        return new Land(
            resultSet.getInt("userId"),
            Plant.valueOf(resultSet.getString("plantType")),
            resultSet.getInt("hasPot") == 1,
            resultSet.getInt("position")
        );
    }

    public Land getLand(int landId) throws SQLException {
        String sql = "SELECT * FROM lands WHERE landId = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, landId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }
        return new Land(
            resultSet.getInt("userId"),
            Plant.valueOf(resultSet.getString("plantType")),
            resultSet.getInt("hasPot") == 1,
            resultSet.getInt("position")
        );
    }

    public List<Land> getLands(int userId) throws SQLException {
        String sql = "SELECT * FROM lands WHERE userId = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Land> lands = new ArrayList<>();
        while (resultSet.next()) {
            lands.add(new Land(
                resultSet.getInt("userId"),
                Plant.valueOf(resultSet.getString("plantType")),
                resultSet.getInt("hasPot") == 1,
                resultSet.getInt("position")
            ));
        }
        return lands;
    }

    public void updateLand(Land land) throws SQLException {
        String sql = "UPDATE lands SET plantType = ?, hasPot = ? WHERE userId = ? AND position = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, land.getPlantType().toString());
        preparedStatement.setInt(2, land.hasPot() ? 1 : 0);
        preparedStatement.setInt(3, land.getUserId());
        preparedStatement.setInt(4, land.getPosition());

        preparedStatement.executeUpdate();
    }

    public int getHighestLandId(int userId) throws SQLException {
        String sql = "SELECT MAX(position) FROM lands WHERE userId = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            return 0;
        }
        return resultSet.getInt(1);
    }
}
