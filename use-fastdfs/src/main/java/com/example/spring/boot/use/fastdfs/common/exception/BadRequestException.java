package com.example.spring.boot.use.fastdfs.common.exception;

/**
 * 客户端错误输入异常
 *
 * @author minus
 * @since 2022-11-13 14:04:16
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
