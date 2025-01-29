package org.CashCatalysts.CashCatalysts.game;

import org.CashCatalysts.CashCatalysts.game.plants.Plant;

public class Land {
    private Plant plantType;
    private boolean hasPot;
    private final int userId;
    private final Integer position;  // To retain the position of the land in the grid
    private Integer cooldownId;


    public Land(int userId, Plant plantType, boolean hasPot, int position) {
        this.userId = userId;
        this.plantType = plantType;
        this.hasPot = hasPot;
        this.position = position;
        cooldownId = null;
    }

    public Land(int userId, Plant plantType, boolean hasPot, int position, Integer cooldownId) {
        this.userId = userId;
        this.plantType = plantType;
        this.hasPot = hasPot;
        this.position = position;
        this.cooldownId = cooldownId;
    }

    public Plant getPlantType() {
        return plantType;
    }

    public void setPlantType(Plant plantType) {
        this.plantType = plantType;
    }

    public boolean hasPot() {
        return hasPot;
    }

    public void setHasPot(boolean hasPot) {
        this.hasPot = hasPot;
    }

    public Integer getCooldownId() {
        return cooldownId;
    }

    public void setCooldownId(Integer cooldownId) {
        this.cooldownId = cooldownId;
    }

    public int getPosition() {
        return position;
    }

    public Integer getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Land{" +
               "plantType=" + plantType +
               ", hasPot=" + hasPot +
               '}';
    }
}
