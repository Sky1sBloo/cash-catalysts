package org.CashCatalysts.CashCatalysts.Chests;
import java.util.*;

public interface ChestData {
    int getPackSize();
    Map<String, Integer> getDropRates();
    int getXp();
}
