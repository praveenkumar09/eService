package com.udemy.utils;

import com.udemy.sec02.ConcurrencyLimit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.*;

public class ConcurrencyLimiter implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimit.class);

    private final ExecutorService executor;
    private final Semaphore semaphore;
    private final Queue<Callable<?>> queue;

    public ConcurrencyLimiter(ExecutorService executor, int limit) {
        this.executor = executor;
        this.semaphore = new Semaphore(limit);
        this.queue = new ConcurrentLinkedQueue<>();
    }

    public <T> Future<T> submit(Callable<T> callable) throws Exception {
        return executor.submit(() -> wrapCallable(callable));
    }

    public <T> Future<T> submitQueue(Callable<T> callable) throws Exception {
        this.queue.add(callable);
        return executor.submit(() -> execute());
    }

    private <T> T wrapCallable(Callable<T> callable) throws Exception {
        try {
            semaphore.acquire();
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }

    private <T> T execute() throws Exception {
        try {
            semaphore.acquire();
            return (T) Objects.requireNonNull(this.queue.poll()).call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }

    @Override
    public void close() throws Exception {
        this.executor.close();
    }
}
