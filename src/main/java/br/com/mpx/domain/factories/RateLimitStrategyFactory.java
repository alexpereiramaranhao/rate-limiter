package br.com.mpx.domain.factories;

import br.com.mpx.domain.enumeration.NotificationType;
import br.com.mpx.domain.strategies.RateLimitStrategy;
import br.com.mpx.domain.strategies.impl.DefaultRateLimitStrategy;
import br.com.mpx.domain.strategies.impl.StatusUnlimitedRateLimtStrategy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RateLimitStrategyFactory {
    private final Map<NotificationType, RateLimitStrategy> strategies = new HashMap<>();
    private final StringRedisTemplate redisTemplate;

    public RateLimitStrategyFactory(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        strategies.put(NotificationType.STATUS, new DefaultRateLimitStrategy(redisTemplate));
        strategies.put(NotificationType.MARKETING, new DefaultRateLimitStrategy(redisTemplate));
        strategies.put(NotificationType.STATUS_UNLIMITED, new StatusUnlimitedRateLimtStrategy(redisTemplate));
    }

    public RateLimitStrategy getStrategy(NotificationType notificationType) {
        return strategies.getOrDefault(notificationType, new StatusUnlimitedRateLimtStrategy(redisTemplate));
    }
}
