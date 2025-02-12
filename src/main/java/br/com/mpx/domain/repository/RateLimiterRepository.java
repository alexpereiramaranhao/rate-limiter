package br.com.mpx.domain.repository;

import br.com.mpx.domain.config.RateLimitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RateLimiterRepository {
    private final StringRedisTemplate redisTemplate;

    public boolean applyOperationForRecipient(String recipient, RateLimitConfig rateLimitConfig){
        String key = String.format("rate_limit:%s:%s",recipient, rateLimitConfig.key());
        System.out.println(key);
        System.out.println(rateLimitConfig);
        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        Optional<Long> currentCounter = Optional.ofNullable(operations.increment(key, 1));

        if(currentCounter.orElse(1L) == 1) {
            redisTemplate.expire(key, Duration.ofSeconds(rateLimitConfig.timeWindow()));
        }

        return currentCounter.orElse(1L) <= rateLimitConfig.maxRequests();
    }

    public boolean verifyKeyForRecipient(String recipient, RateLimitConfig rateLimitConfig) {
        String key = String.format("rate_limit:%s:%s",recipient, rateLimitConfig.key());
        System.out.println(rateLimitConfig);
        if(redisTemplate.hasKey(key)) {
            return false;
        }

        redisTemplate.opsForValue().set(key, "1", Duration.ofSeconds(rateLimitConfig.timeWindow()));

        return true;
    }
}
