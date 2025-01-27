package org.CashCatalysts.CashCatalysts.Chests;
import java.util.*;

public enum EpicType implements ChestData{
    EPIC;

    @Override
    public int getPackSize(){
        return 3;
    }

    @Override
    public  Map<String, Integer> getDropRates(){
        Map<String, Integer> dropRates = new HashMap<>();
        dropRates.put("saging seeds", 15);
        dropRates.put("pinya seeds", 13);
        dropRates.put("strawberry seeds", 12);
        dropRates.put("apple seeds", 15);
        dropRates.put("sampaguita seeds", 15);
        dropRates.put("orchids seeds", 10);
        dropRates.put("sunflower seeds", 10);
        dropRates.put("rose seeds", 10);
        return dropRates;
    }

    @Override
    public int getXp(){
        return 70;
    }
}
