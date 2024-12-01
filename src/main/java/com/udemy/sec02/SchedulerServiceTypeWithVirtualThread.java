package com.udemy.sec02;

import com.udemy.utils.CommonUtils;
import com.udemy.utils.ConcurrencyLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.*;

public class SchedulerServiceTypeWithVirtualThread {

    private static final Logger log = LoggerFactory.getLogger(SchedulerServiceTypeWithVirtualThread.class);

    public static void main(String[] args) {
        scheduled();
    }

    private static void scheduled() {
        /*
          A thread pool which can be used to run task at regular intervals.
          Call a remote service,every minute.
         */
        try (var scheduler = newSingleThreadScheduledExecutor(Thread.ofVirtual().factory());
              var executor = newVirtualThreadPerTaskExecutor()) {
            scheduler.scheduleAtFixedRate(() -> {
                executor.execute(() -> {
                    String productInfo = ConcurrencyLimit.printProductInfo(1);
                });
            }, 0, 1, TimeUnit.SECONDS);
            CommonUtils.sleep(Duration.ofSeconds(10));
        }
    }
}
