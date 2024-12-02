package com.udemy.sec03;

import com.udemy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class SupplyAsync {


    private static Logger log = LoggerFactory.getLogger(SupplyAsync.class);

    public static void main(String[] args) {
        log.info("Main starts!");
        CompletableFuture<String> cf = slowTask();
        cf.thenAccept(v -> log.info("Value is {}", v));
        log.info("Main ends!");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static CompletableFuture<String> slowTask(){
        log.info("Slow task starts!");
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            CommonUtils.sleep(Duration.ofSeconds(1));
            return "hi";
        }, Executors.newVirtualThreadPerTaskExecutor());
        log.info("Slow task ends!");
        return cf;
    }

}
