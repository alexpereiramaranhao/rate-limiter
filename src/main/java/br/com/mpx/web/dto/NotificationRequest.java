package br.com.mpx.web.dto;

import br.com.mpx.domain.enumeration.NotificationType;
import lombok.Data;

@Data
public class NotificationRequest {
    private String from;
    private String recipient;
    private String message;
    private NotificationType type;
}
