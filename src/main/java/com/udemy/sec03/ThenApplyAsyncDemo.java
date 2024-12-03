package com.udemy.sec03;

import com.udemy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class ThenApplyAsyncDemo {

    private static final Logger log = LoggerFactory.getLogger(AllOfDemo.class);

    public static void main(String[] args) {
        try(ExecutorService executors = Executors.newVirtualThreadPerTaskExecutor()){
            var cf1 = getDeltaAirfare(executors)
                    .thenApplyAsync(deltaOb -> {
                        log.info("Delta Ob: {}", deltaOb);
                        CompletableFuture<Airfare> frontierAirfare = getFrontierAirfare(executors);
                        Airfare frontierOb = frontierAirfare.join();
                        return (deltaOb.amount() <= frontierOb.amount()) ? deltaOb : frontierOb;
                    },executors);
            log.info("Result: {}", cf1.join());
        }
    }

    private static CompletableFuture<Airfare> getDeltaAirfare(ExecutorService executorService){
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(108,1808);
            CommonUtils.sleep(Duration.ofMillis(random));
            return new Airfare("Delta", random);
        },executorService);
    }

    private static CompletableFuture<Airfare> getFrontierAirfare(ExecutorService executorService){
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(108,1808);
            CommonUtils.sleep(Duration.ofMillis(random));
            return new Airfare("Frontier", random);
        },executorService);
    }
}
