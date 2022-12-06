package com.example.spring.boot.use.schedule.common.exception;

/**
 *
 * 权限鉴权异常
 *
 * @author minus
 * @since 2022-11-13 14:04:16
 */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
