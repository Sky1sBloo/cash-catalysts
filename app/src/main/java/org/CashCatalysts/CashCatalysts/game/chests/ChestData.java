package org.CashCatalysts.CashCatalysts.game.chests;
import java.util.*;

public interface ChestData {
    int getPackSize();
    Map<String, Integer> getDropRates();
    List<ChestData> getDropRates();
    int getXp();
}
