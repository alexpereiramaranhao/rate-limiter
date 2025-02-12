package br.com.mpx.domain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("configs")
public record RateLimiterProperties(List<RateLimitConfig> limits) {

}

