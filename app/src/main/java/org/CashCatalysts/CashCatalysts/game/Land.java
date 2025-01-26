package org.CashCatalysts.CashCatalysts.game;

public class Land {
    private Plant plantType;
    private boolean hasPot;

    public Land(Plant plantType, boolean hasPot) {
        this.plantType = plantType;
        this.hasPot = hasPot;
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

    @Override
    public String toString() {
        return "Land{" +
               "plantType=" + plantType +
               ", hasPot=" + hasPot +
               '}';
    }
}
