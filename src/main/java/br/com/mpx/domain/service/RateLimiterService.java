package br.com.mpx.domain.service;

import br.com.mpx.domain.config.RateLimitConfig;
import br.com.mpx.domain.config.RateLimiterProperties;
import br.com.mpx.domain.exceptions.RateLimitExceededException;
import br.com.mpx.domain.factories.RateLimitStrategyFactory;
import br.com.mpx.domain.strategies.RateLimitStrategy;
import br.com.mpx.web.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RateLimiterService {

    private  final RateLimitStrategyFactory strategyFactory;
    private final RateLimiterProperties rateLimiterProperties;

    public void sendNotification(NotificationRequest notificationRequest){
        System.out.println("ratelimiteproperties");
        System.out.println(rateLimiterProperties);

        var rateLimitConfig = rateLimiterProperties.limits().stream().
                filter(limit ->
                    limit.key().equals(notificationRequest.getType())
                ).findFirst().orElseThrow(() -> new RateLimitExceededException("Invalid notification type"));

        this.verifyPermissionsToRequest(notificationRequest.getRecipient(), rateLimitConfig);
        //It's all ok. Send notification
    }

    private void verifyPermissionsToRequest(String recipient, RateLimitConfig rateLimitConfig) {
        System.out.println("ratelimiteproperties");

        RateLimitStrategy rateLimitStrategy = strategyFactory.getStrategy(rateLimitConfig);

        if(!rateLimitStrategy.isAllowed(recipient, rateLimitConfig)) {
            throw  new RateLimitExceededException(String.format("Rate limit exceeded for recipient: %s", recipient));
        }

    }
}
