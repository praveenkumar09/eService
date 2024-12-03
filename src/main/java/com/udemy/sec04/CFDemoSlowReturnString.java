package com.udemy.sec04;

import com.udemy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static java.lang.Thread.sleep;
import static java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor;

public class CFDemoSlowReturnString {

    private static final Logger log = LoggerFactory.getLogger(CFDemoSlowReturnString.class);
    private static CompletableFuture<String> future;

    public static void main(String[] args) {
        log.info("Main thread started");
        ExecutorService executorService = newVirtualThreadPerTaskExecutor();
        CompletableFuture<String> returnString = slowTask(executorService);
        returnString.thenAccept((str) -> log.info("Value returned: {}", str));
        //log.info("Return String: {}", returnString.join());
        log.info("Main thread ended");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static CompletableFuture<String> slowTask(ExecutorService executorService){
        CompletableFuture<String> future = new CompletableFuture<>();
        executorService.submit(() -> {
                CommonUtils.sleep(Duration.ofSeconds(1));
                future.complete("Hello World");
        });
        return future;
    }
}
