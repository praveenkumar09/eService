package com.udemy.sec04;

import com.udemy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CFRunAsyncDemo {

    private static final Logger log = LoggerFactory.getLogger(CFRunAsyncDemo.class);

    public static void main(String[] args) {
        log.info("Main started..");
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        slowTask(executorService)
                //.thenRun(() -> log.info("Task executed from parent after child completion"));
                        .thenRunAsync(() -> log.info("Task executed from parent in async"));
        log.info("Main ended..");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static CompletableFuture<Void> slowTask(ExecutorService executorService){
        log.info("Slow task started..");
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            CommonUtils.sleep(Duration.ofSeconds(1));
            log.info("Task executed successfully..");
        }, executorService);
        log.info("Slow task completed..");
        return voidCompletableFuture;
    }

}
