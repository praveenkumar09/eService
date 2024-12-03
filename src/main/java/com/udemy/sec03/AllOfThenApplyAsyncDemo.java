package com.udemy.sec03;

import com.udemy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class AllOfThenApplyAsyncDemo {

    private static final Logger log = LoggerFactory.getLogger(AllOfThenApplyAsyncDemo.class);

    public static void main(String[] args) {
        try(ExecutorService executors = Executors.newVirtualThreadPerTaskExecutor()){
            var deltaCF = getDeltaAirfare(executors);
            var frontierCF = getFrontierAirfare(executors);
            var maCF = getMAAirfare(executors);
            CompletableFuture<Integer> resultCF = CompletableFuture.allOf(deltaCF, frontierCF, maCF)
                    .thenApplyAsync( airfare -> {
                        Airfare deltaAirfare = deltaCF.join();
                        Airfare frontierAirfare = frontierCF.join();
                        Airfare maAirfare = maCF.join();
                        int minTemp = Math.min(deltaAirfare.amount(), frontierAirfare.amount());
                        return Math.min(minTemp, maAirfare.amount());
                    }, executors);
            log.info("All of {} airfares", resultCF.join());
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

    private static CompletableFuture<Airfare> getMAAirfare(ExecutorService executorService){
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(108,1808);
            CommonUtils.sleep(Duration.ofMillis(random));
            return new Airfare("MA", random);
        },executorService);
    }
}
