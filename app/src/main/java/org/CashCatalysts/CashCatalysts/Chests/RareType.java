package org.CashCatalysts.CashCatalysts.Chests;
import java.util.*;

public enum RareType implements ChestData{
    RARE;

    @Override
    public int getPackSize(){
        return 2;
    }

    @Override
    public  Map<String, Integer> getDropRates(){
        Map<String, Integer> dropRates = new HashMap<>();
        dropRates.put("saging seeds", 20);
        dropRates.put("pinya seeds", 10);
        dropRates.put("strawberry seeds", 10);
        dropRates.put("apple seeds", 20);
        dropRates.put("sampaguita seeds", 20);
        dropRates.put("orchids seeds", 5);
        dropRates.put("sunflower seeds", 10);
        dropRates.put("rose seeds", 5);
        return dropRates;
    }

    @Override
    public int getXp(){
        return 50;
    }
}