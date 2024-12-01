package com.udemy.sec02;

import com.udemy.sec02.externalservice.Client;
import com.udemy.utils.ConcurrencyLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;

import static java.util.concurrent.Executors.*;

public class ConcurrencyLimit {

    private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimit.class);

    public static void main(String[] args) throws Exception {
        ConcurrencyLimiter limiter = new ConcurrencyLimiter(newThreadPerTaskExecutor(Thread.ofVirtual().name("virtual-",1).factory()),1);
        //ConcurrencyLimiter limiter = new ConcurrencyLimiter(newFixedThreadPool(2),2);
        execute(limiter,20);
    }

    private static String printProductInfo(int id){
        String product = Client.getProduct(id);
        log.info("Product {} has been acquired. Info => {}", id, product);
        return product;
    }

    private static void execute(ConcurrencyLimiter concurrencyLimiter,int taskCount) throws Exception {
        try (concurrencyLimiter) {
            for (int i = 1; i <= taskCount; i++) {
                int finalI = i;
                concurrencyLimiter.submit(() -> printProductInfo(finalI));
            }
            log.info("Task Submitted...");
        }
    }
}
