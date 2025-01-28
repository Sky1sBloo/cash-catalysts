package org.CashCatalysts.CashCatalysts.game.challenges;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
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
        challengeHandler = new ChallengeHandler(databaseHandler);
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
