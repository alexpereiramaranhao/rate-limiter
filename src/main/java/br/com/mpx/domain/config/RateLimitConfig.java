package br.com.mpx.domain.config;

import br.com.mpx.domain.enumeration.NotificationType;

public record RateLimitConfig(long timeWindow, int maxRequests, NotificationType key) {

}
