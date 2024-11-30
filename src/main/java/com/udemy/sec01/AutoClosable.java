package com.udemy.sec01;

import com.udemy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AutoClosable {

    private static final Logger log = LoggerFactory.getLogger(AutoClosable.class);

    public static void main(String[] args) {
            /*ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(AutoClosable::task);
            log.info("Task submitted successfully");
            executorService.shutdown();*/

        try(ExecutorService executor = Executors.newSingleThreadExecutor()) {
            executor.execute(AutoClosable::task);
            log.info("Task submitted successfully");
        }catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    public static void task(){
        CommonUtils.sleep(Duration.ofSeconds(1));
        log.info("Task executed...");
    }
}
