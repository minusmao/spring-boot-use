package com.example.spring.boot.use.async.service;

/**
 * 异步线程池
 *
 * @author minus
 * @since 2022/12/1 0:36
 */
public interface TestService {

    void testAsync();

    void testCompletableFuture();

}
