package com.udemy.sec03;

import com.udemy.sec02.aggregator.Product;
import com.udemy.sec02.externalservice.Client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class AggregatorServiceCF {

    private final ExecutorService executorService;

    public AggregatorServiceCF(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public Product getProduct(int id) throws ExecutionException, InterruptedException {
        var product = CompletableFuture.supplyAsync(() -> Client.getProduct(id), executorService)
                .exceptionally(ex -> "Product not found");
        var rating = CompletableFuture.supplyAsync(() -> Client.getRating(id), executorService)
                .exceptionally(ex -> -1);
        return new Product(id, product.get(), rating.get());
    }
}
