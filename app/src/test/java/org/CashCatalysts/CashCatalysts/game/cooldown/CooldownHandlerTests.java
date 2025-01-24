package org.CashCatalysts.CashCatalysts.game.cooldown;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class CooldownHandlerTests {
    private CooldownHandler cooldownHandler;

    @BeforeEach
    public void loadCooldownHandler() {
        try {
            DatabaseHandler databaseHandler = new DatabaseHandler(":memory:");
            cooldownHandler = new CooldownHandler(databaseHandler);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void createAndRetrieveCooldownTest() {
        Cooldown cooldown = CooldownHandler.createCooldown(LocalDateTime.now());
        int cooldownId = cooldownHandler.addCooldown(cooldown);
        Cooldown retrievedCooldown = cooldownHandler.getCooldown(cooldownId);
        Assertions.assertEquals(cooldown, retrievedCooldown);
        Assertions.assertNotNull(cooldown);
    }

    @Test
    public void getAllFinishedCooldownsTest() {
        Cooldown cooldown = CooldownHandler.createCooldown(LocalDateTime.now().minusMinutes(1));
        Cooldown cooldown2 = CooldownHandler.createCooldown(LocalDateTime.now().minusMinutes(2));
        Cooldown cooldown3 = CooldownHandler.createCooldown(LocalDateTime.now().plusMinutes(10));

        cooldownHandler.addCooldown(cooldown);
        cooldownHandler.addCooldown(cooldown2);
        cooldownHandler.addCooldown(cooldown3);

        LocalDateTime now = LocalDateTime.now();
        Assertions.assertEquals(2, cooldownHandler.getAllFinishedCooldowns(now).size());
    }

    @Test
    public void deleteCooldownTest() {
        Cooldown cooldown = CooldownHandler.createCooldown(LocalDateTime.now());
        int cooldownId = cooldownHandler.addCooldown(cooldown);
        cooldownHandler.deleteCooldown(cooldownId);
        Assertions.assertNull(cooldownHandler.getCooldown(cooldownId));
    }

    @Test
    public void updateCooldownTest() {
        Cooldown cooldown = CooldownHandler.createCooldown(LocalDateTime.now());
        int cooldownId = cooldownHandler.addCooldown(cooldown);
        Cooldown updatedCooldown = CooldownHandler.createCooldown(LocalDateTime.now().plusMinutes(1));
        cooldownHandler.updateCooldown(cooldownId, updatedCooldown.cooldownEnd());
        Assertions.assertEquals(updatedCooldown, cooldownHandler.getCooldown(cooldownId));
    }

    @Test
    public void createCooldownWithNullEndTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> CooldownHandler.createCooldown(null));
    }

    @Test
    public void addCooldownWithNullCooldownTest() {
        Assertions.assertThrows(RuntimeException.class, () -> cooldownHandler.addCooldown(null));
    }
}
