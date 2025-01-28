package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.game.challenges.Challenge;
import org.CashCatalysts.CashCatalysts.game.challenges.ChallengeReward;
import org.CashCatalysts.CashCatalysts.game.challenges.ChallengeCondition;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChallengesTable extends DbTable {
    public ChallengesTable(Connection connection) throws SQLException {
        super(connection);

        DbField[] fields = {
                new DbField("id", "INTEGER", "PRIMARY KEY AUTOINCREMENT"),
                new DbField("condition", "INTEGER", "NOT NULL"),
                new DbField("name", "VARCHAR(128)", "NOT NULL"),
                new DbField("description", "VARCHAR(255)", "NOT NULL"),
                new DbField("start_date", "DATE", "NOT NULL"),
                new DbField("end_date", "DATE", "NOT NULL"),
                new DbField("is_completed", "BOOLEAN", "NOT NULL DEFAULT 0"),
                new DbField("gold_reward", "INTEGER", "NOT NULL"),
                new DbField("star_reward", "INTEGER", "NOT NULL"),
                new DbField("xp_reward", "INTEGER", "NOT NULL"),
                new DbField("normal_chest_reward", "INTEGER", "NOT NULL"),
                new DbField("rare_chest_reward", "INTEGER", "NOT NULL"),
                new DbField("epic_chest_reward", "INTEGER", "NOT NULL")
        };

        super.createTable("challenges", fields);
    }

    public int addChallenge(Challenge challenge) throws SQLException {
        String sql = "INSERT INTO challenges (condition, name, description, start_date, end_date, " +
                "gold_reward, star_reward, xp_reward, normal_chest_reward, rare_chest_reward, epic_chest_reward) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, challenge.condition().toInt());
        statement.setString(2, challenge.name());
        statement.setString(3, challenge.description());
        statement.setDate(4, Date.valueOf(challenge.startDate()));
        statement.setDate(5, Date.valueOf(challenge.endDate()));
        statement.setInt(6, challenge.reward().gold());
        statement.setInt(7, challenge.reward().star());
        statement.setInt(8, challenge.reward().xp());
        statement.setInt(9, challenge.reward().normalChest());
        statement.setInt(10, challenge.reward().rareChest());
        statement.setInt(11, challenge.reward().epicChest());

        statement.executeUpdate();

        return getLastRowId();
    }

    public List<Challenge> getAllChallengesAfterDate(LocalDate date) throws SQLException {
        List<Challenge> challenges = new ArrayList<>();
        String sql = "SELECT * FROM challenges WHERE start_date >= ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, Date.valueOf(date));

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            challenges.add(new Challenge(
                    resultSet.getInt("id"),
                    ChallengeCondition.fromInt(resultSet.getInt("condition")),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getDate("start_date").toLocalDate(),
                    resultSet.getDate("end_date").toLocalDate(),
                    new ChallengeReward(resultSet.getInt("gold_reward"),
                            resultSet.getInt("star_reward"),
                            resultSet.getInt("xp_reward"),
                            resultSet.getInt("normal_chest_reward"),
                            resultSet.getInt("rare_chest_reward"),
                            resultSet.getInt("epic_chest_reward")),
                    resultSet.getBoolean("is_completed")
            ));
        }
        return challenges;
    }

    public void makeChallengeCompleted(int id) throws SQLException {
        String sql = "UPDATE challenges SET is_completed = 1 WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public void deleteChallenge(int id) throws SQLException {
        String sql = "DELETE FROM challenges WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public void deleteAllChallengesBeforeDate(LocalDate date) throws SQLException {
        String sql = "DELETE FROM challenges WHERE end_date < ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, Date.valueOf(date));
        statement.executeUpdate();
    }
}
