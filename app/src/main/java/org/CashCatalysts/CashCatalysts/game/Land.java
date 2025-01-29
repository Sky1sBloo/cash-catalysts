package org.CashCatalysts.CashCatalysts.game;

import org.CashCatalysts.CashCatalysts.game.plants.Plant;

public class Land {
    private Plant plantType;
    private boolean hasPot;
    private boolean isHarvestable;
    private final int userId;
    private final Integer position;  // To retain the position of the land in the grid


    public Land(int userId, Plant plantType, boolean hasPot, boolean isHarvestable, int position) {
        this.userId = userId;
        this.plantType = plantType;
        this.hasPot = hasPot;
        this.isHarvestable = isHarvestable;
        this.position = position;
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

    public boolean getHarvestable() {
        return isHarvestable;
    }

    public void setHarvestable(boolean harvestable) {
        isHarvestable = true;
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
