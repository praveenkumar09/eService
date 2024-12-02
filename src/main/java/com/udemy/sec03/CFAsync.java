package com.udemy.sec03;

import com.udemy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class CFAsync {

    private static final Logger log = LoggerFactory.getLogger(CFAsync.class);

    public static void main(String[] args) {
        log.info("Main started");
        runAsync()
                .thenRun(() -> log.info("Async completed"))
                        .exceptionally((e) -> {
                            log.info("Error: {}",e.getMessage());
                            return null;
                        });
        log.info("Main ended");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static CompletableFuture<Void> runAsync(){
        log.info("Slow task starts!");

        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            CommonUtils.sleep(Duration.ofSeconds(1));
            //log.info("Async task completed!");
            throw new RuntimeException("oops");
        }, Executors.newVirtualThreadPerTaskExecutor());
        log.info("Slow task ends!");
        return cf;
    }
}
