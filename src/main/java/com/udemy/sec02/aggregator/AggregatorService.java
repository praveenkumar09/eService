package com.udemy.sec02.aggregator;

import com.udemy.sec02.externalservice.Client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class AggregatorService {

    private final ExecutorService executorService;

    public AggregatorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public Product getProduct(int id) {
        var product = executorService.submit(() -> Client.getProduct(id));
        var rating = executorService.submit(() -> Client.getRating(id));
        try {
            return new Product(id, product.get(), rating.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
