package br.com.mpx.domain.enumeration;

import lombok.Getter;

import java.time.Duration;

@Getter
public enum NotificationType {
    STATUS(Duration.ofMinutes(1), 2),
    MARKETING(Duration.ofHours(1),3),
    STATUS_UNLIMITED(Duration.ofMinutes(2),-1),
    NEWS(Duration.ofDays(1), 1);


    private final Duration timeWindow;
    private final int maxRequest;

    NotificationType(Duration timeWindow, int maxRequest) {
        this.timeWindow = timeWindow;
        this.maxRequest = maxRequest;
    }


}
