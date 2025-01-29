package org.CashCatalysts.CashCatalysts.datatypes;

import java.util.Random;

public class ApplicationRandom {
    private static final Random random = new Random();

    public static int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
