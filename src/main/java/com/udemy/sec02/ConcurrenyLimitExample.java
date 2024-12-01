package com.udemy.sec02;

import com.udemy.sec02.externalservice.Client;
import com.udemy.utils.ConcurrencyLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.Executors.newThreadPerTaskExecutor;

public class ConcurrenyLimitExample {

    private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimit.class);

    public static void main(String[] args) throws Exception {
        ConcurrencyLimiter limiter = new ConcurrencyLimiter(newFixedThreadPool(3,Thread.ofVirtual().name("virtual-",1).factory()),3);
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
                concurrencyLimiter.submitQueue(() -> printProductInfo(finalI));
            }
            log.info("Task Submitted...");
        }
    }
}
