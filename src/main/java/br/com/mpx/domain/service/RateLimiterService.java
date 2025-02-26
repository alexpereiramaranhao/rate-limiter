package br.com.mpx.domain.service;

import br.com.mpx.web.dto.NotificationRequest;


public interface RateLimiterService {

    public void sendNotification(NotificationRequest notificationRequest);

}
