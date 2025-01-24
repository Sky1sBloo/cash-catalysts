package org.CashCatalysts.CashCatalysts.game.cooldown;

import java.time.LocalDateTime;

public record Cooldown (Integer id, LocalDateTime cooldownEnd) {
    public Cooldown {
        if (cooldownEnd == null) {
            throw new IllegalArgumentException("cooldownEnd cannot be null");
        }
    }
}
