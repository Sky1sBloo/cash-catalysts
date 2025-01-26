package org.CashCatalysts.CashCatalysts.game;

 public enum Plant{

    BANANA("saging"),
    PINEAPPLE("pinya"),
    STRAWBERRY("strawberry"),
    APPLE("apple"),
    SAMPAGUITA("sampaguita"),
    ORCHIDS("orchids"),
    SUNFLOWER("sunflower"),
    ROSE("rose"),
    NONE("none");

    private final String tag;

    Plant(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
