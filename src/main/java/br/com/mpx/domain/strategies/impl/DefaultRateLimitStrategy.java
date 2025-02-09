package br.com.mpx.domain.strategies.impl;

import br.com.mpx.domain.enumeration.NotificationType;
import br.com.mpx.domain.strategies.RateLimitStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;

@RequiredArgsConstructor
public class DefaultRateLimitStrategy implements RateLimitStrategy {

    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean isAllowed(String recipient, NotificationType notificationType) {
        String key = String.format("rate_limit:%s:%s",recipient, notificationType.name());

        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        Optional<Long> currentCounter = Optional.ofNullable(operations.increment(key, 1));

        if(currentCounter.orElse(1L) == 1) {
            redisTemplate.expire(key, notificationType.getTimeWindow());
        }

        return currentCounter.orElse(1L) <= notificationType.getMaxRequest();
    }
}
