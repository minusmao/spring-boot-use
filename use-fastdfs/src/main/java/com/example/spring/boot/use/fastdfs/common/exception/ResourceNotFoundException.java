package com.example.spring.boot.use.fastdfs.common.exception;

/**
 * 资源未找到异常
 *
 * @author minus
 * @since 2022-11-13 14:04:16
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
