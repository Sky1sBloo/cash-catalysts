package org.CashCatalysts.CashCatalysts.game;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Database.LandsTable;

import java.sql.SQLException;
import java.util.List;

public class LandHandler {
    private final int userId;
    private final LandsTable landsTable;

    public LandHandler(int userId, DatabaseHandler databaseHandler) {
        this.userId = userId;
        this.landsTable = databaseHandler.getLandsTable();
    }

    /**
     * Add a land to the user
     */
    public void addLand() {
        try {
            Land newLand = new Land(userId, Plant.NONE, false, landsTable.getHighestLandId(userId) + 1);
            landsTable.addLand(newLand);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets land at a position
     */
    public Land getLand(int position) {
        try {
            return landsTable.getLand(userId, position);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get all lands of the user
     */
    public List<Land> getLands() {
        try {
            return landsTable.getLands(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a pot to a land
     */
    public void addPot(int position) {
        try {
            Land land = landsTable.getLand(userId, position);
            land.setHasPot(true);
            landsTable.updateLand(land);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove a pot from a land
     * Removes any plant on the land
     */
    public void removePot(int position) {
        try {
            Land land = landsTable.getLand(userId, position);
            land.setPlantType(Plant.NONE);
            land.setHasPot(false);
            landsTable.updateLand(land);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Plant a plant on a land
     * Does nothing if the land does not have a pot
     */
    public void plant(int position, Plant plant) {
        try {
            Land land = landsTable.getLand(userId, position);
            if (!land.isHasPot()) {
                return;
            }
            land.setPlantType(plant);
            landsTable.updateLand(land);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove a plant from a land
     */
    public void removePlant(int position) {
        try {
            Land land = landsTable.getLand(userId, position);
            land.setPlantType(Plant.NONE);
            landsTable.updateLand(land);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
