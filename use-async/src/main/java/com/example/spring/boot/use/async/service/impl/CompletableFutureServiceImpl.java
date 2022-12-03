package com.example.spring.boot.use.async.service.impl;

import com.example.spring.boot.use.async.service.CompletableFutureService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 测试异步编排
 * 参考文档：https://zhuanlan.zhihu.com/p/415851066
 *
 * @author minus
 * @since 2022/12/1 22:14
 */
@Service
public class CompletableFutureServiceImpl implements CompletableFutureService {

    private final Logger log = LoggerFactory.getLogger(CompletableFutureServiceImpl.class);

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private Executor taskExecutor;

    @Override
    public void testCompletableFuture() {
        testBaseFuture();   // 基础异步线程
        testWhenFuture();   // 计算完成回调
        testHandleFuture();    // handle最终处理
        testThenFuture();    // 线程串行化
        testBothFuture();    // 两个任务必须都完成，触发该任务
        testEitherFuture();    // 两个任务任意一个完成，触发该任务
        testAllAnyOf();    // 多任务组合
    }

    /**
     * 基础异步线程
     */
    @SneakyThrows
    private void testBaseFuture() {
        log.info("一、开启异步编程");

        // 1 - runAsync()
        log.info("runAsync():无入参、无返回值");
        CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(() -> {
            log.info("runAsync()开启异步任务...");
        }, executor);
        runAsyncFuture.get();    // get()方法会使当前线程阻塞，等待runAsyncFuture完成

        // 2 - supplyAsync()
        log.info("supplyAsync():无入参，可以获取返回值");
        CompletableFuture<String> supplyAsyncFuture = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync()开启异步任务...");
            return "我是返回值";
        }, executor);
        log.info("supplyAsync()获取异步任务返回值：" + supplyAsyncFuture.get());
    }

    /**
     * 计算完成回调
     */
    @SneakyThrows
    private void testWhenFuture() {
        log.info("二、计算完成回调");

        // 1 - whenComplete()
        log.info("whenComplete():处理正常和异常的计算结果，与前Future使用同一线程");
        CompletableFuture<Integer> whenCompleteFuture = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync()开启异步任务...");
            return 1;
        }, executor).whenComplete((res, exc) -> {
            log.info("whenComplete()异步任务完成了，执行结果是：" + res + "  异常是：" + exc);
        });
        log.info("whenComplete()不改变返回值：" + whenCompleteFuture.get());

        // 2 - whenCompleteAsync()
        log.info("whenCompleteAsync():处理正常和异常的计算结果，重新交给线程池处理");
        CompletableFuture<Integer> whenCompleteAsyncFuture = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync()开启异步任务...");
            return 1;
        }, executor).whenCompleteAsync((res, exc) -> {
            log.info("whenCompleteAsync()异步任务完成了，执行结果是：" + res + "  异常是：" + exc);
        });
        log.info("whenCompleteAsync()不改变返回值：" + whenCompleteAsyncFuture.get());

        // 3 - exceptionally()
        log.info("exceptionally():处理异常并改变返回结果");
        CompletableFuture<Integer> exceptionallyFuture = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync()开启异步任务...");
            if (Math.random() > 0.5) {
                throw new RuntimeException("发生异常");
            }
            return 1;
        }, executor).whenCompleteAsync((res, exc) -> {
            log.info("whenCompleteAsync()异步任务完成了，执行结果是：" + res + "  异常是：" + exc);
        }).exceptionally(throwable -> {
            System.out.println("exceptionally()进入了异常处理，捕获了" + throwable.getMessage() + "异常");
            return 5;
        });
        log.info("出现异常时，exceptionally()才会处理并改变返回结果：" + exceptionallyFuture.get());
    }

    /**
     * handle最终处理
     */
    @SneakyThrows
    private void testHandleFuture() {
        log.info("三、handle最终处理");

        // 1 - handleAsync()
        log.info("handleAsync():处理正常和异常结果，可以改变返回结果");
        CompletableFuture<Integer> handleFuture = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync()开启异步任务...");
            if (Math.random() > 0.5) {
                throw new RuntimeException("发生异常");
            }
            return 1;
        }, executor).handleAsync((res, thr) -> {
            log.info("handleAsync()异步任务完成了，执行结果是：" + res + "  异常是：" + thr);
            if (res != null) {
                return res * 2;
            }
            if (thr != null) {
                return 5;
            }
            return res;
        }, executor);
        log.info("handleAsync()改变了返回值：" + handleFuture.get());
    }

    /**
     * 线程串行化
     * thenApply() / thenApplyAsync() -> 获取上一个任务返回结果，处理并返回当前任务的返回值
     * thenAccept() / thenAcceptAsync() -> 获取上一个任务返回结果，处理但无返回值
     * thenRun() / thenRunAsync() -> 获取不到上一个任务返回结果，也无返回值，适用于任务只有先后顺序的场景
     * 与whenComplete()的区别是：前者拿不到异常信息，后者不能改变返回值
     */
    @SneakyThrows
    private void testThenFuture() {
        log.info("四、线程串行化");

        // 1 - thenApply()
        log.info("thenApply():处理正常和异常结果，可以改变返回结果");
        CompletableFuture<Integer> thenFuture = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync()开启异步任务...");
            return 1;
        }, taskExecutor).thenApplyAsync((res) -> {
            log.info("thenApply()处理上一步的结果是：" + res + "，开始处理并返回");
            return res * 2;
        }, taskExecutor);
        log.info("thenApply()改变了返回值：" + thenFuture.get());
    }

    /**
     * 两个任务必须都完成，触发该任务
     * runAfterBoth() / runAfterBothAsync() -> future1.runAfterBoth(future2, () -> {}) 无返回值
     * thenAcceptBoth() / thenAcceptBothAsync() -> future1.thenAcceptBoth(future2, (res1, res2) -> {}) 无返回值
     * thenCombine() / thenCombineAsync() -> future1.thenCombine(future2, (res1, res2) -> { return res3 }) 有返回值
     */
    @SneakyThrows
    private void testBothFuture() {
        log.info("五、两个任务必须都完成，触发该任务");

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync()开启异步任务1...");
            return 1;
        }, taskExecutor);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync()开启异步任务2...");
            return 2;
        }, taskExecutor);

        // 1 - runAfterBoth()
        log.info("runAfterBoth():没有传参（前两个任务的返回值），没有返回值");
        future1.runAfterBoth(future2, () -> {
            try {
                log.info("runAfterBoth()可以通过get()方式拿到返回值：{}，{}", future1.get(), future2.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        // 2 - thenAcceptBoth()
        log.info("thenAcceptBoth():有传参（前两个任务的返回值），没有返回值");
        future1.thenAcceptBoth(future2, (res1, res2) -> {
            log.info("thenAcceptBoth()通过传参拿到返回值：{}，{}", res1, res2);
        });

        // 3 - thenCombine()
        log.info("thenCombine():有传参（前两个任务的返回值），有返回值");
        CompletableFuture<String> thenCombineFuture = future1.thenCombine(future2, (res1, res2) -> {
            log.info("thenCombine()通过传参拿到返回值：{}，{}", res1, res2);
            return "res1 + res2 = " + (res1 + res2);
        });
        log.info("thenCombine()改变了返回值：" + thenCombineFuture.get());
    }

    /**
     * 两个任务任意一个完成，触发该任务
     * runAfterEither() / runAfterEitherAsync() -> future1.runAfterEither(future2, () -> {}) 无返回值
     * acceptEither() / acceptEitherAsync() -> future1.acceptEither(future2, (res) -> {}) 无返回值
     * applyToEither() / applyToEitherAsync() -> future1.acceptEither(future2, { return res3 }) 有返回值
     */
    @SneakyThrows
    private void testEitherFuture() {
        log.info("六、两个任务任意一个完成，触发该任务");

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync()开启异步任务1...");
            return 11;
        }, taskExecutor);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync()开启异步任务2...");
            return 22;
        }, taskExecutor);

        // 1 - runAfterEither()
        log.info("runAfterEither():没有传参（前两个任务的返回值），没有返回值");
        future1.runAfterEitherAsync(future2, () -> {
            log.info("runAfterEither()开启异步任务...");
        });

        // 2 - acceptEither()
        log.info("acceptEither():有传参（前两个任务最先完成的返回值），没有返回值");
        future1.acceptEitherAsync(future2, (res) -> {
            log.info("acceptEither()通过传参拿到返回值：{}", res);
        }, executor);

        // 3 - applyToEither()
        log.info("applyToEither():有传参（前两个任务最先完成的返回值），有返回值");
        CompletableFuture<String> applyToEitherFuture = future1.applyToEitherAsync(future2, (res) -> {
            log.info("applyToEither()通过传参拿到返回值：{}", res);
            return "最先完成的：" + res;
        }, taskExecutor);
        log.info("applyToEither()改变了返回值：" + applyToEitherFuture.get());
    }

    /**
     * 多任务组合
     * anyOf():只要有一个任务完成，并获得其返回值
     * allOf():等待所有任务完成
     */
    @SneakyThrows
    private void testAllAnyOf() {
        log.info("七、多任务组合");

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync()开启异步任务1：查询商品图片...");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "图片地址";
        }, executor);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync()开启异步任务2：查询商品属性...");
            return "黑色 256G";
        }, executor);
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync()开启异步任务3：查询商品品牌...");
            return "苹果手机";
        }, executor);

        // 1 - anyOf()
        log.info("anyOf():只要有一个任务完成，并获得其返回值");
        CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(future1, future2, future3);
        log.info("anyOf()返回值：" + anyOfFuture.get());

        // 2 - allOf()
        log.info("allOf():等待所有任务完成");
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(future1, future2, future3);
        log.info("allOf()返回值：" + allOfFuture.get());
    }

}
