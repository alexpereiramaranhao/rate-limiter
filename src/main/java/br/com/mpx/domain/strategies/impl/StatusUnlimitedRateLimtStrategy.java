package br.com.mpx.domain.strategies.impl;

import br.com.mpx.domain.config.RateLimitConfig;
import br.com.mpx.domain.repository.RateLimiterRepository;
import br.com.mpx.domain.strategies.RateLimitStrategy;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusUnlimitedRateLimtStrategy implements RateLimitStrategy {
    private final RateLimiterRepository rateLimiterRepository;

    @Override
    public boolean isAllowed(String recipient, RateLimitConfig rateLimitConfig) {
        return rateLimiterRepository.verifyKeyForRecipient(recipient, rateLimitConfig);
    }
}
