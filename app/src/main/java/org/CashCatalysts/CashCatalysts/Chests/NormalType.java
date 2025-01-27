package org.CashCatalysts.CashCatalysts.Chests;
import java.util.*;
public  enum NormalType implements ChestData{
    NORMAL;

    @Override
    public int getPackSize(){
        return 1;
    }

    @Override
    public  Map<String, Integer> getDropRates(){
        Map<String, Integer> dropRates = new HashMap<>();
        dropRates.put("saging seeds", 30);
        dropRates.put("pinya seeds", 5);
        dropRates.put("strawberry seeds", 5);
        dropRates.put("apple seeds", 30);
        dropRates.put("sampaguita seeds", 21);
        dropRates.put("orchids seeds", 2);
        dropRates.put("sunflower seeds", 5);
        dropRates.put("rose seeds", 2);
        return dropRates;
    }

    @Override
    public int getXp(){
        return 30;
    }
}