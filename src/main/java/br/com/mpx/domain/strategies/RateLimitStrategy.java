package br.com.mpx.domain.strategies;

import br.com.mpx.domain.config.RateLimitConfig;

public interface RateLimitStrategy {
    boolean isAllowed(String recipient, RateLimitConfig notificationType);
}
