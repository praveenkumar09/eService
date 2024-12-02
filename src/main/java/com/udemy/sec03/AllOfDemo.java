package com.udemy.sec03;

import com.udemy.sec02.aggregator.AggregatorService;
import com.udemy.sec02.aggregator.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class AllOfDemo {
    private static final Logger log = LoggerFactory.getLogger(AllOfDemo.class);
    public static void main(String[] args) {
        var executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("virtual-",1).factory());
        var aggregator = new AggregatorServiceCF(executor);
        List<CompletableFuture<Product>> futureList = IntStream.rangeClosed(1, 50)
                .mapToObj(id -> CompletableFuture
                        .supplyAsync(() -> {
                            try {
                                return aggregator.getProduct(id);
                            } catch (ExecutionException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }, executor))
                .toList();

        CompletableFuture.allOf(futureList.toArray(CompletableFuture[]::new)).join();

        List<Product> productDTO = futureList.stream()
                .map(CompletableFuture::join)
                .toList();
        log.info("Product-List: {}",productDTO);
    }
}
