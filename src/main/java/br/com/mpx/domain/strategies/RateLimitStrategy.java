package br.com.mpx.domain.strategies;

import br.com.mpx.domain.enumeration.NotificationType;

public interface RateLimitStrategy {
    boolean isAllowed(String recipient, NotificationType notificationType);
}
