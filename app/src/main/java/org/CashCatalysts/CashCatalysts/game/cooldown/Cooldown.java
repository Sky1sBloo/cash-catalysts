package org.CashCatalysts.CashCatalysts.game.cooldown;

import java.time.LocalDateTime;

public record Cooldown (Integer id, LocalDateTime cooldownEnd) {
    public Cooldown {
        if (cooldownEnd == null) {
            throw new IllegalArgumentException("cooldownEnd cannot be null");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Cooldown other) {
            return cooldownEnd.equals(other.cooldownEnd);
        }
        return false;
    }
}
