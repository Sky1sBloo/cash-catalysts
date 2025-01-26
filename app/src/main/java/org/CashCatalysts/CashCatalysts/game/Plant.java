package org.CashCatalysts.CashCatalysts.game;

 public enum Plant{

    BANANA("saging"),
    PINEAPPLE("pinya"),
    STRAWBERRY("strawberry"),
    APPLE("apple"),
    FLOWERS("flowers"),
    SAMPAGUITA("sampaguita"),
    ORCHIDS("orchids"),
    SUNFLOWER("sunflower"),
    ROSE("rose");

    private final String tag;

    Plant(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
