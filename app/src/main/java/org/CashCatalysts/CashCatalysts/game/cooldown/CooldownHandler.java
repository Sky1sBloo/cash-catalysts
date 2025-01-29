package org.CashCatalysts.CashCatalysts.game.cooldown;

import org.CashCatalysts.CashCatalysts.Database.CooldownsTable;
import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class for handing cooldowns
 */
public class CooldownHandler {
    private final CooldownsTable cooldownsTable;

    public CooldownHandler(DatabaseHandler databaseHandler) {
        cooldownsTable = databaseHandler.getCooldownsTable();
    }

    /**
     * Creates a new cooldown with a specified end time
     * Generally used for creating new cooldowns
     */
    public static Cooldown createCooldown(LocalDateTime cooldownEnd) {
        return new Cooldown(null, cooldownEnd);
    }

    /**
     * Adds a new cooldown to the database
     * @param cooldown The cooldown to add
     * @return The id of the new cooldown
     */
    public int addCooldown(Cooldown cooldown) {
        try {
            return cooldownsTable.addCooldown(cooldown);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add cooldown", e);
        }
    }

    /**
     * Retrieves a cooldown from the database
     * @param id The id of the cooldown
     * @return The cooldown with the specified id
     */
    public Cooldown getCooldown(Integer id) {
        if (id == null) {
            return null;
        }
        try {
            return cooldownsTable.getCooldown(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get cooldown", e);
        }
    }

    /**
     * Checks if specified cooldown id is finished
     */
    public boolean cooldownIsFinished(int id, LocalDateTime dateTime) {
        Cooldown cooldown = getCooldown(id);
        if (cooldown == null) {
            return true;
        }
        return dateTime.isAfter(cooldown.cooldownEnd());
    }

    /**
     * Retrieves all cooldowns that are finished
     */
    public List<Cooldown> getAllFinishedCooldowns(LocalDateTime dateTime) {
        try {
            return cooldownsTable.getAllFinishedCooldowns(dateTime);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all finished cooldowns", e);
        }
    }

    /**
     * Deletes a cooldown from the database
     * @param id The id of the cooldown to delete
     */
    public void deleteCooldown(int id) {
        try {
            cooldownsTable.deleteCooldown(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete cooldown", e);
        }
    }

    /**
     * Updates the end time of a cooldown
     * @param id The id of the cooldown
     * @param cooldownEnd The new end time of the cooldown
     */
    public void updateCooldown(int id, LocalDateTime cooldownEnd) {
        try {
            cooldownsTable.updateCooldown(id, cooldownEnd);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update cooldown", e);
        }
    }
}
