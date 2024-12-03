package com.udemy.sec03;

import com.udemy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class AnyOfDemo {

    private static final Logger log = LoggerFactory.getLogger(AllOfDemo.class);

    public static void main(String[] args) {
        try(ExecutorService executors = Executors.newVirtualThreadPerTaskExecutor()){
            var cf1 = getDeltaAirfare(executors);
            var cf2 = getFrontierAirfare(executors);
            log.info("Result : {}",CompletableFuture.anyOf(cf1,cf2)
                    .join());
        }
    }

    private static CompletableFuture<String> getDeltaAirfare(ExecutorService executorService){
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(108,1808);
            CommonUtils.sleep(Duration.ofMillis(random));
            return "Delta-$ :"+random;
        },executorService);
    }

    private static CompletableFuture<String> getFrontierAirfare(ExecutorService executorService){
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(108,1808);
            CommonUtils.sleep(Duration.ofMillis(random));
            return "Frontier-$ :"+random;
        },executorService);
    }
}
