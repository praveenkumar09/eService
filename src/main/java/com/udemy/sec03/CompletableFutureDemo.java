package com.udemy.sec03;

import com.udemy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {

    private static final Logger log = LoggerFactory.getLogger(CompletableFutureDemo.class);

    public static void main(String[] args) {
        log.info("Main starts!");
        CompletableFuture<String> cf = slowTask();
        cf.thenAccept(v -> log.info("Value is {}", v));
        log.info("Main ends!");
    }

    private static CompletableFuture<String> fastTask(){
        log.info("Fast task starts!");
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.complete("hi");
        log.info("Fast task ends!");
        return completableFuture;
    }

    private static CompletableFuture<String> slowTask(){
        log.info("Fast task starts!");
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Thread.Builder.OfVirtual virtualThread = Thread.ofVirtual();
        virtualThread.start(() -> {
            CommonUtils.sleep(Duration.ofSeconds(1));
            completableFuture.complete("hi");
        });
        log.info("Fast task ends!");
        return completableFuture;
    }


}
