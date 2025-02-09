package br.com.mpx.web.controller;

import br.com.mpx.domain.service.RateLimiterService;
import br.com.mpx.web.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class RateLimiterController {

    private final RateLimiterService rateLimitingService;

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest requestBody) {
        rateLimitingService.sendNotification(requestBody);
        return ResponseEntity.ok("Notification sent successfully");
    }
}
