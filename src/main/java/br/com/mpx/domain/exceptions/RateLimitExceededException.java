package br.com.mpx.domain.exceptions;

import br.com.mpx.RateLimiterApplication;

public class RateLimitExceededException extends  RuntimeException{

    public RateLimitExceededException(String message) {
        super(message);
    }
}
