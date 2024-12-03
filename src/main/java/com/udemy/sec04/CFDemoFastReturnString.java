package com.udemy.sec04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor;

public class CFDemoFastReturnString {

    private static final Logger log = LoggerFactory.getLogger(CFDemoFastReturnString.class);

    public static void main(String[] args) {
        log.info("Main thread started");
        ExecutorService executorService = newVirtualThreadPerTaskExecutor();
        CompletableFuture<String> returnString = fastTask(executorService);
        log.info("Return String: {}", returnString.join());
        log.info("Main thread ended");
    }

    private static CompletableFuture<String> fastTask(ExecutorService executorService){
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("Hello World");
        return future;
    }
}
