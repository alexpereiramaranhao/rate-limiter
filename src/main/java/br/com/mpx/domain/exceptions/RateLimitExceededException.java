package br.com.mpx.domain.exceptions;

public class RateLimitExceededException extends  RuntimeException{

    public RateLimitExceededException(String message) {
        super(message);
    }
}
