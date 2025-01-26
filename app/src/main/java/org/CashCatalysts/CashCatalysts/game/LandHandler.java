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

    public void addLand() {
        try {
            Land newLand = new Land(userId, Plant.NONE, false, landsTable.getHighestLandId(userId) + 1);
            landsTable.addLand(newLand);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Land getLand(int position) {
        try {
            return landsTable.getLand(userId, position);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Land> getLands() {
        try {
            return landsTable.getLands(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPot(int position) {
        try {
            Land land = landsTable.getLand(userId, position);
            land.setHasPot(true);
            landsTable.updateLand(land);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
