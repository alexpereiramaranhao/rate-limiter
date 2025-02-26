package br.com.mpx.domain.service;

import br.com.mpx.domain.config.RateLimitConfig;
import br.com.mpx.domain.config.RateLimiterProperties;
import br.com.mpx.domain.exceptions.RateLimitExceededException;
import br.com.mpx.domain.factories.RateLimitStrategyFactory;
import br.com.mpx.domain.strategies.RateLimitStrategy;
import br.com.mpx.web.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class RateLimiterServiceImpl implements RateLimiterService{

    private  final RateLimitStrategyFactory strategyFactory;
    private final RateLimiterProperties rateLimiterProperties;

    @Override
    public void sendNotification(NotificationRequest notificationRequest){
        log.debug("Prepare to send notification: {}", notificationRequest);

        var rateLimitConfig = rateLimiterProperties.limits().stream().
                filter(limit ->
                    limit.key().equals(notificationRequest.getType())
                ).findFirst().orElseThrow(() -> new RateLimitExceededException("Invalid notification type"));

        this.verifyPermissionsToRequest(notificationRequest.getRecipient(), rateLimitConfig);
        //It's all ok. Send notification

        log.debug("Notification sent successfully");
    }

    private void verifyPermissionsToRequest(String recipient, RateLimitConfig rateLimitConfig) {
        RateLimitStrategy rateLimitStrategy = strategyFactory.getStrategy(rateLimitConfig);

        if(!rateLimitStrategy.isAllowed(recipient, rateLimitConfig)) {
            log.debug("Rate limit exceeded for recipient: {}", recipient);
            throw  new RateLimitExceededException(String.format("Rate limit exceeded for recipient: %s", recipient));
        }

        log.debug("Rate limit not exceeded for recipient: {}", recipient);
    }
}
