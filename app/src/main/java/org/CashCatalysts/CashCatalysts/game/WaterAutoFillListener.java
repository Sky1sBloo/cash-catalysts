package org.CashCatalysts.CashCatalysts.game;

import org.CashCatalysts.CashCatalysts.game.cooldown.Cooldown;
import org.CashCatalysts.CashCatalysts.game.cooldown.CooldownHandler;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Class that handlers water autofill
 */
public class WaterAutoFillListener {
    private final UserGameStatsHandler userGameStatsHandler;
    private final CooldownHandler cooldownHandler;

    private final int WATER_FILL_RATE_MINUTES = 1;
    private final int MAX_WATER = 24;

    public WaterAutoFillListener(UserGameStatsHandler userGameStatsHandler, CooldownHandler cooldownHandler) {
        this.userGameStatsHandler = userGameStatsHandler;
        this.cooldownHandler = cooldownHandler;

        if (!hasWaterCooldown() || isAutoFillFinished(LocalDateTime.now())) {
            startAutoFill();
        }
    }

    public void startAutoFill() {
        int cooldownId = cooldownHandler.addCooldown(CooldownHandler.createCooldown(LocalDateTime.now().plusMinutes(1)));
        userGameStatsHandler.getUserGameStats().setWaterCooldownId(cooldownId);
    }

    public void stopAutoFill() {
        Integer cooldownId = userGameStatsHandler.getUserGameStats().getWaterCooldownId();
        if (cooldownId == null) {
            return;
        }
        userGameStatsHandler.getUserGameStats().clearWaterCooldown();
        cooldownHandler.deleteCooldown(cooldownId);
    }

    public boolean hasWaterCooldown() {
        return userGameStatsHandler.getUserGameStats().getWaterCooldownId() != null;
    }

    public boolean isAutoFillFinished(LocalDateTime currentTime) {
        if (userGameStatsHandler.getUserGameStats().getWaterCooldownId() == null) {
            return false;
        }
        return cooldownHandler.cooldownIsFinished(userGameStatsHandler.getUserGameStats().getWaterCooldownId(), currentTime);
    }

    public void calculatedAutoFill(LocalDateTime currentTime) {
        // Calculate the time since water fill rate is completed and add water per minute
        if (userGameStatsHandler.getUserGameStats().getWater().getAmount() >= MAX_WATER) {
            stopAutoFill();
            return;
        }
        Cooldown cooldown = cooldownHandler.getCooldown(userGameStatsHandler.getUserGameStats().getWaterCooldownId());
        if (cooldown == null) {
            return;
        }
        Duration duration = Duration.between(currentTime, cooldown.cooldownEnd());
        if (!duration.isNegative()) {
            return;
        }
        Duration timeSinceFill = duration.abs();
        userGameStatsHandler.getUserGameStats().getWater().add((int) (timeSinceFill.toMinutes() + 1));
        userGameStatsHandler.updateUserGameStats();
        System.out.println(userGameStatsHandler.getUserGameStats().getWater().getAmount());
        stopAutoFill();
        startAutoFill();
    }

    public Duration getNextFillTime(LocalDateTime currentTime) {
        if (userGameStatsHandler.getUserGameStats().getWaterCooldownId() == null) {
            return Duration.ZERO;
        }
        return Duration.between(currentTime, cooldownHandler.getCooldown(userGameStatsHandler.getUserGameStats().getWaterCooldownId()).cooldownEnd());
    }

    public String getTimeRemainingInMinutesSeconds(LocalDateTime currentTime) {
        Duration duration = getNextFillTime(currentTime);
        return String.format("%d:%02d", duration.toMinutes(), duration.toSecondsPart());
    }

    public int getMaxWaterFill() {
        return MAX_WATER;
    }
}
