package org.CashCatalysts.CashCatalysts.game.plants;

 public enum Plant{

    BANANA("saging"),
    PINEAPPLE("pineapple"),
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
