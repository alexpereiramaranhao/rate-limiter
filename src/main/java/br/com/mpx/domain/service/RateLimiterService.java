package br.com.mpx.domain.service;

import br.com.mpx.domain.enumeration.NotificationType;
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

    public void sendNotification(NotificationRequest notificationRequest){
        this.verifyPermissionsToRequest(notificationRequest.getRecipient(), notificationRequest.getType());
        //It's all ok. Send notification
    }

    private void verifyPermissionsToRequest(String recipient, NotificationType notificationType) {
        RateLimitStrategy rateLimitStrategy = strategyFactory.getStrategy(notificationType);

        if(!rateLimitStrategy.isAllowed(recipient, notificationType)) {
            throw  new RateLimitExceededException(String.format("Rate limit exceeded for recipient: %s", recipient));
        }

    }
}
