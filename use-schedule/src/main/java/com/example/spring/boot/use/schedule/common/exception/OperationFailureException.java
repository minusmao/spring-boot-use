package com.example.spring.boot.use.schedule.common.exception;

/**
 * 操作失败异常
 *
 * @author minus
 * @since 2022/11/21 23:19
 */
public class OperationFailureException extends RuntimeException {
    public OperationFailureException(String message) {
        super(message);
    }
}
