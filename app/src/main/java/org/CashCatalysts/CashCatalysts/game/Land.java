package org.CashCatalysts.CashCatalysts.game;

public class Land {
    private Plant plantType;
    private boolean hasPot;
    private final int userId;
    private final Integer position;  // To retain the position of the land in the grid


    public Land(int userId, Plant plantType, boolean hasPot, int position) {
        this.userId = userId;
        this.plantType = plantType;
        this.hasPot = hasPot;
        this.position = position;
    }

    public Plant getPlantType() {
        return plantType;
    }

    public void setPlantType(Plant plantType) {
        this.plantType = plantType;
    }

    public boolean isHasPot() {
        return hasPot;
    }

    public void setHasPot(boolean hasPot) {
        this.hasPot = hasPot;
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
