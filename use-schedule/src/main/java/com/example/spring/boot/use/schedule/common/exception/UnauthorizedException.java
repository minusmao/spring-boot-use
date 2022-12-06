package com.example.spring.boot.use.schedule.common.exception;

/**
 * 身份认证异常
 *
 * @author minus
 * @since 2022-11-13 14:04:16
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
