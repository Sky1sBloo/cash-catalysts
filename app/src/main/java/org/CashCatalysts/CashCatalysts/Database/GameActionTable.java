package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.game.gameaction.GameAction;
import org.CashCatalysts.CashCatalysts.game.gameaction.GameActionType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Generally used for storing stats like when you plant a seed, when you harvest a plant, etc.
 */
public class GameActionTable extends DbTable {
    public GameActionTable(Connection connection) throws SQLException {
        super(connection);

        DbField[] fields = {
                new DbField("id", "INTEGER PRIMARY KEY AUTOINCREMENT"),
                new DbField("user_id", "INTEGER", "NOT NULL"),
                new DbField("type", "VARCHAR(32)", "NOT NULL"),
                new DbField("action_id", "INTEGER"),
                new DbField("action_date", "DATE", "NOT NULL")
        };
        String[] foreignKey = {"FOREIGN KEY (user_id) REFERENCES users(user_id)"};
        super.createTable("game_actions", fields, foreignKey);
    }

    /**
     * Adds a game action to the database
     */
    public void addGameAction(GameAction action) throws SQLException {
        String sql = "INSERT INTO game_actions (user_id, type, action_id, action_date) VALUES(?, ?, ?, ?);";
        PreparedStatement insertStatement = connection.prepareStatement(sql);
        insertStatement.setInt(1, action.userId());
        insertStatement.setString(2, action.type().name());
        insertStatement.setInt(3, action.actionId());
        insertStatement.setDate(4, Date.valueOf(action.actionDate()));
        insertStatement.executeUpdate();
    }

    public List<GameAction> getGameActions(int userId) throws SQLException {
        List<GameAction> gameActions = new ArrayList<>();
        String sql = "SELECT * FROM game_actions WHERE user_id = ?";
        PreparedStatement getActionsStatement = connection.prepareStatement(sql);
        getActionsStatement.setInt(1, userId);
        ResultSet rs = getActionsStatement.executeQuery();

        while (rs.next()) {
            gameActions.add(new GameAction(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    GameActionType.valueOf(rs.getString("type")),
                    rs.getInt("action_id"),
                    rs.getDate("action_date").toLocalDate()
            ));
        }
        return gameActions;
    }

    public List<GameAction> getGameActionsBetween(int userId, LocalDate startDate, LocalDate endDate) throws SQLException {
        List<GameAction> gameActions = new ArrayList<>();
        String sql = "SELECT * FROM game_actions WHERE user_id = ? AND action_date BETWEEN ? AND ?";
        PreparedStatement getActionsStatement = connection.prepareStatement(sql);
        getActionsStatement.setInt(1, userId);
        getActionsStatement.setDate(2, Date.valueOf(startDate));
        getActionsStatement.setDate(3, Date.valueOf(endDate));
        ResultSet rs = getActionsStatement.executeQuery();

        while (rs.next()) {
            gameActions.add(new GameAction(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    GameActionType.valueOf(rs.getString("type")),
                    rs.getInt("action_id"),
                    rs.getDate("action_date").toLocalDate()
            ));
        }
        return gameActions;
    }

    public List<GameAction> getGameActionsWithActionTypeBetween(int userId, GameActionType type, LocalDate startDate, LocalDate endDate) throws SQLException {
        List<GameAction> gameActions = new ArrayList<>();
        String sql = "SELECT * FROM game_actions WHERE user_id = ? AND type = ? AND action_date BETWEEN ? AND ?";
        PreparedStatement getActionsStatement = connection.prepareStatement(sql);
        getActionsStatement.setInt(1, userId);
        getActionsStatement.setString(2, type.name());
        getActionsStatement.setDate(3, Date.valueOf(startDate));
        getActionsStatement.setDate(4, Date.valueOf(endDate));
        ResultSet rs = getActionsStatement.executeQuery();

        while (rs.next()) {
            gameActions.add(new GameAction(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    GameActionType.valueOf(rs.getString("type")),
                    rs.getInt("action_id"),
                    rs.getDate("action_date").toLocalDate()
            ));
        }
        return gameActions;
    }
}
