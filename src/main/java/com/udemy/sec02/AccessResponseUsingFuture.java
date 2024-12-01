package com.udemy.sec02;

import com.udemy.sec02.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AccessResponseUsingFuture {
    private static final Logger log = LoggerFactory.getLogger(AccessResponseUsingFuture.class);

    public static void main(String[] args) {
        try (ExecutorService execService = Executors.newVirtualThreadPerTaskExecutor()) {
                Future<String> futureProduct = execService.submit(() -> Client.getProduct(1));
                log.info("Product-{}: {}",1, futureProduct.get());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
