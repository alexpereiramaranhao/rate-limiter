package br.com.mpx.service;

import br.com.mpx.domain.config.RateLimitConfig;
import br.com.mpx.domain.config.RateLimiterProperties;
import br.com.mpx.domain.enumeration.NotificationType;
import br.com.mpx.domain.exceptions.RateLimitExceededException;
import br.com.mpx.domain.factories.RateLimitStrategyFactory;
import br.com.mpx.domain.service.RateLimiterService;
import br.com.mpx.domain.strategies.RateLimitStrategy;
import br.com.mpx.web.dto.NotificationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RateLimiterServiceTest {

    @Mock
    private RateLimitStrategyFactory strategyFactory;

    @Mock
    private RateLimitStrategy rateLimitStrategy;

    @Mock
    private RateLimiterProperties rateLimiterProperties;

    private RateLimitConfig rateLimitConfig;

    @InjectMocks
    private RateLimiterService rateLimiterService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rateLimitConfig = new RateLimitConfig(60, 5, NotificationType.STATUS);
        when(rateLimiterProperties.limits()).thenReturn(List.of(rateLimitConfig));
    }

    @Test
    void testSendNotification_WhenAllowed() {
        NotificationRequest request = new NotificationRequest();
        request.setRecipient("recipient@example.com");
        request.setType(NotificationType.STATUS);
        request.setFrom("from@example.com");
        request.setMessage("message");

        when(strategyFactory.getStrategy(rateLimitConfig)).thenReturn(rateLimitStrategy);
        when(rateLimitStrategy.isAllowed("recipient@example.com", rateLimitConfig)).thenReturn(true);

        rateLimiterService.sendNotification(request);

        verify(rateLimitStrategy, times(1)).isAllowed("recipient@example.com", rateLimitConfig);
    }

    @Test
    void testSendNotification_WhenNotAllowed() {
        NotificationRequest request = new NotificationRequest();
        request.setRecipient("recipient@example.com");
        request.setType(NotificationType.STATUS);
        request.setFrom("from@example.com");
        request.setMessage("message");

        when(strategyFactory.getStrategy(rateLimitConfig)).thenReturn(rateLimitStrategy);
        when(rateLimitStrategy.isAllowed("recipient@example.com", rateLimitConfig)).thenReturn(false);

        assertThrows(RateLimitExceededException.class, () -> rateLimiterService.sendNotification(request));
        verify(rateLimitStrategy, times(1)).isAllowed("recipient@example.com", rateLimitConfig);
    }

    @Test
    void testSendNotification_InvalidNotificationType() {
        NotificationRequest request = new NotificationRequest();
        request.setRecipient("recipient@example.com");
        request.setType(null);
        request.setFrom("from@example.com");
        request.setMessage("message");

        assertThrows(RateLimitExceededException.class, () -> rateLimiterService.sendNotification(request));
    }

    @Test
    void testSendNotification_NoRateLimitConfig() {
        NotificationRequest request = new NotificationRequest();
        request.setRecipient("recipient@example.com");
        request.setType(NotificationType.STATUS);
        request.setFrom("from@example.com");
        request.setMessage("message");

        when(rateLimiterProperties.limits()).thenReturn(List.of());

        assertThrows(RateLimitExceededException.class, () -> rateLimiterService.sendNotification(request));
    }
}