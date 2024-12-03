package com.udemy.sec03;

import com.udemy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class ThenCombineDemo {

    private static final Logger log = LoggerFactory.getLogger(AllOfDemo.class);

    public static void main(String[] args) {
        try(ExecutorService executors = Executors.newVirtualThreadPerTaskExecutor()){
            var cf1 = getDeltaAirfare(executors);
            var cf2 = getFrontierAirfare(executors);
            Airfare airfare = cf1.thenCombine(cf2, (a, b) -> {
                        return a.amount() <= b.amount() ? a : b;
                    }).thenApply(af -> new Airfare(af.airline(), af.amount()))
                    .join();
            log.info("CFVal : {}",airfare);
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
