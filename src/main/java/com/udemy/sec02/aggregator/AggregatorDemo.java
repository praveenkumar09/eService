package com.udemy.sec02.aggregator;

import com.udemy.sec02.AccessResponseUsingFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class AggregatorDemo {
    private static final Logger log = LoggerFactory.getLogger(AggregatorDemo.class);

    public static void main(String[] args) {
        var executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("virtual-",1).factory());
        var aggregator = new AggregatorService(executor);
        List<Future<Product>> futureList = IntStream.rangeClosed(1, 50)
                .mapToObj(id -> executor.submit(() -> aggregator.getProduct(id)))
                .toList();
        List<Product> productDTO = futureList.stream()
                .map(f -> {
                    try {
                        return f.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        log.info("Product-List: {}",productDTO);
    }
}
