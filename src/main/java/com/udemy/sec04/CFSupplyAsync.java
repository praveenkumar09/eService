package com.udemy.sec04;

import com.udemy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CFSupplyAsync {

    private static final Logger log = LoggerFactory.getLogger(CFSupplyAsync.class);

    public static void main(String[] args) {
        log.info("Main started..");
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        CompletableFuture<String> stringCompletableFuture = slowTask(executorService);
        CompletableFuture<String> stringCompletableFuture1 = stringCompletableFuture
                //.thenAccept(cfOB -> log.info("Output: {}",cfOB));
                .thenApplyAsync(str -> {
                    log.info("Info : {}", str.toUpperCase());
                    return str.toUpperCase();
                }, executorService);
        stringCompletableFuture1
                .thenAccept(str -> log.info("Info 2: {}", str));
        log.info("Main finished..");
        CommonUtils.sleep(Duration.ofSeconds(5));
    }

    private static CompletableFuture<String> slowTask(ExecutorService executorService){
        log.info("Slow task started..");
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("Task executing from child in async");
            CommonUtils.sleep(Duration.ofSeconds(2));
            return "Hello World";
        }, executorService);
        log.info("Slow task completed..");
        return stringCompletableFuture;
    }

}
