package br.com.mpx.domain.factories;

import br.com.mpx.domain.config.RateLimitConfig;
import br.com.mpx.domain.enumeration.NotificationType;
import br.com.mpx.domain.repository.RateLimiterRepositoryImpl;
import br.com.mpx.domain.strategies.RateLimitStrategy;
import br.com.mpx.domain.strategies.impl.DefaultRateLimitStrategy;
import br.com.mpx.domain.strategies.impl.StatusUnlimitedRateLimtStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RateLimitStrategyFactory {
    private final Map<NotificationType, RateLimitStrategy> strategies = new HashMap<>();
    private final RateLimiterRepositoryImpl rateLimiterRepository;

    public RateLimitStrategyFactory(RateLimiterRepositoryImpl rateLimiterRepository) {
        this.rateLimiterRepository = rateLimiterRepository;
        this.strategies.put(NotificationType.STATUS, new DefaultRateLimitStrategy(rateLimiterRepository));
        this.strategies.put(NotificationType.MARKETING, new DefaultRateLimitStrategy(rateLimiterRepository));
        this.strategies.put(NotificationType.STATUS_LIMITED, new StatusUnlimitedRateLimtStrategy(rateLimiterRepository));
    }

    public RateLimitStrategy getStrategy(RateLimitConfig limitConfig) {
        return strategies.getOrDefault(limitConfig.key(), new StatusUnlimitedRateLimtStrategy(rateLimiterRepository));
    }
}
