package br.com.mpx.domain.repository;

import br.com.mpx.domain.config.RateLimitConfig;

public interface RateLimiterRepository {

    boolean applyOperationForRecipient(String recipient, RateLimitConfig rateLimitConfig);
    boolean verifyKeyForRecipient(String recipient, RateLimitConfig rateLimitConfig);
}
