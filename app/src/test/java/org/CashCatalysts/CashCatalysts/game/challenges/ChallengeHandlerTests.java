package org.CashCatalysts.CashCatalysts.game.challenges;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.UserProfile.UsersHandler;
import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;
import org.CashCatalysts.CashCatalysts.budgets.BudgetHandler;
import org.CashCatalysts.CashCatalysts.game.UserGameStatsHandler;
import org.CashCatalysts.CashCatalysts.game.gameaction.GameActionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ChallengeHandlerTests {
    private ChallengeHandler challengeHandler;

    @BeforeEach
    public void setup() throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler(":memory:");
        UsersHandler usersHandler = new UsersHandler(databaseHandler);
        int userId = 0;
        if (usersHandler.getUser(userId) == null) {
            userId = usersHandler.registerUser(UsersHandler.createUser("user"));
        }
        TransactionHandler transactionHandler = new TransactionHandler(databaseHandler);
        BudgetHandler budgetHandler = new BudgetHandler(databaseHandler);
        UserStatsSystem userStatsSystem = new UserStatsSystem(transactionHandler, budgetHandler);
        UserGameStatsHandler userGameStatsHandler = new UserGameStatsHandler(userId, databaseHandler);
        GameActionHandler gameActionHandler = new GameActionHandler(databaseHandler, 0);
        challengeHandler = new ChallengeHandler(databaseHandler, userStatsSystem, gameActionHandler, userGameStatsHandler);
    }

    @Test
    public void generateDailyChallengesTest() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        challengeHandler.generateDailyChallenges(date);
        List<Challenge> challenges = challengeHandler.getUpcomingChallenges(date);
        Assertions.assertEquals(3, challenges.size());
    }

    @Test
    public void generateWeeklyChallengesTest() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        challengeHandler.generateWeeklyChallenges(date);
        List<Challenge> challenges = challengeHandler.getUpcomingChallenges(date);
        Assertions.assertEquals(3, challenges.size());
    }
}
