package org.CashCatalysts.CashCatalysts.Database;

import java.util.*;

// Represents a single level with XP requirement and potential unlocked titles
class Level {
    private final int levelNumber;
    private final int xpRequired;

    public Level(int levelNumber, int xpRequired) {
        this.levelNumber = levelNumber;
        this.xpRequired = xpRequired;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int getXpRequired() {
        return xpRequired;
    }
}

// Handler to calculate levels and track unlocked titles
public class LevelSystemHandler {
    private static final int MAX_XP = 3750;

    // Example levels, the level number and XP required
    private List<Level> levels;

    // Titles unlocked, the titles can be unlocked in any order
    private Set<String> unlockedTitles;

    public LevelSystemHandler() {
        this.levels = new ArrayList<>();
        this.unlockedTitles = new HashSet<>();
        initializeLevels();
    }

    // Initialize level data, it can be replaced with database calls
    private void initializeLevels() {
        levels.add(new Level(1, 250));
        levels.add(new Level(2, 500));
        levels.add(new Level(3, 750));
        levels.add(new Level(4, 1000));
        levels.add(new Level(5, 1250));
        levels.add(new Level(6, 1500));
        levels.add(new Level(7, 1750));
        levels.add(new Level(8, 2000));
        levels.add(new Level(9, 2250));
        levels.add(new Level(10, 2500));
        levels.add(new Level(11, 2750));
        levels.add(new Level(12, 3000));
        levels.add(new Level(13, 3250));
        levels.add(new Level(14, 3500));
        levels.add(new Level(15, 3750));
    }

    // Calculate the current level based on XP
    public int calculateLevel(int currentXp) {
        if (currentXp < 0) {
            throw new IllegalArgumentException("XP cannot be negative.");
        }
        if (currentXp > MAX_XP) {
            currentXp = MAX_XP; // Cap XP at the maximum value
        }

        int level = 0;
        for (Level lvl : levels) {
            if (currentXp >= lvl.getXpRequired()) {
                level = lvl.getLevelNumber();
            } else {
                break;
            }
        }
        return level;
    }

    // Add a title to the unlocked titles list
    public void unlockTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
        unlockedTitles.add(title);
    }

    // Get the list of all unlocked titles
    public Set<String> getUnlockedTitles() {
        return new HashSet<>(unlockedTitles);
    }

    // Check if a title is already unlocked
    public boolean isTitleUnlocked(String title) {
        return unlockedTitles.contains(title);
    }

    public static void main(String[] args) {
        LevelSystemHandler handler = new LevelSystemHandler();

        // Example: Calculating level based on XP
        int xp = 2750;
        System.out.println("Current XP: " + xp);
        System.out.println("Current Level: " + handler.calculateLevel(xp));

        // Example: Unlocking titles
        handler.unlockTitle("Novice Grower");
        handler.unlockTitle("Star Seeker");

        System.out.println("Unlocked Titles: " + handler.getUnlockedTitles());

        // Check if a specific title is unlocked
        System.out.println("Is 'Star Seeker' unlocked? " + handler.isTitleUnlocked("Star Seeker"));
    }
}
