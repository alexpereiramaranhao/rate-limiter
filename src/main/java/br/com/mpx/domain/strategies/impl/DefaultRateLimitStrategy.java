package br.com.mpx.domain.strategies.impl;

import br.com.mpx.domain.config.RateLimitConfig;
import br.com.mpx.domain.repository.RateLimiterRepositoryImpl;
import br.com.mpx.domain.strategies.RateLimitStrategy;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultRateLimitStrategy implements RateLimitStrategy {

    private final RateLimiterRepositoryImpl rateLimiterRepository;

    @Override
    public boolean isAllowed(String recipient, RateLimitConfig rateLimitConfig) {
        return rateLimiterRepository.applyOperationForRecipient(recipient, rateLimitConfig);
    }
}
