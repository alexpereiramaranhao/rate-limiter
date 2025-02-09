package br.com.mpx.domain.strategies.impl;

import br.com.mpx.domain.enumeration.NotificationType;
import br.com.mpx.domain.strategies.RateLimitStrategy;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

@RequiredArgsConstructor
public class StatusUnlimitedRateLimtStrategy implements RateLimitStrategy {
    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean isAllowed(String recipient, NotificationType notificationType) {

        String key = String.format("rate_limit:%s:%s",recipient, notificationType.name());

        if(redisTemplate.hasKey(key)) {
            return false;
        }

        redisTemplate.opsForValue().set(key, "1", notificationType.getTimeWindow());

        return true;
    }
}
