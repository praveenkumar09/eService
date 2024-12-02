package com.udemy.sec03;

import com.udemy.sec02.aggregator.AggregatorService;
import com.udemy.sec02.aggregator.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class AggregatorDemoCF {
    private static final Logger log = LoggerFactory.getLogger(AggregatorDemoCF.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("virtual-",1).factory());
        var aggregator = new AggregatorServiceCF(executor);
        Product product = aggregator.getProduct(52);
        log.info("Product: {}",product);
    }
}
