package com.udemy.sec03;

import com.udemy.sec02.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CFGetProducts {
    private static final Logger log = LoggerFactory.getLogger(CFGetProducts.class);

    public static void main(String[] args) {

        CompletableFuture<String> productOne;
        CompletableFuture<String> productTwo;
        CompletableFuture<String> productThree;
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            productOne = CompletableFuture.supplyAsync(() -> Client.getProduct(1), executorService);
            productTwo = CompletableFuture.supplyAsync(() -> Client.getProduct(4), executorService);
            productThree = CompletableFuture.supplyAsync(() -> Client.getProduct(3), executorService);
        }
        log.info("Product-1: {}", productOne.join());
        log.info("Product-2: {}", productTwo.join());
        log.info("Product-3: {}", productThree.join());
    }
}
