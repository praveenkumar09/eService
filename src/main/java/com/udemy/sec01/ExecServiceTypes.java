package com.udemy.sec01;

import com.udemy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.*;

public class ExecServiceTypes {

    private static final Logger log = LoggerFactory.getLogger(ExecServiceTypes.class);

    public static void main(String[] args) {
       // execute(newSingleThreadExecutor(),3);
        //execute(newFixedThreadPool(Runtime.getRuntime().availableProcessors()),20);
        //execute(newCachedThreadPool(),200);
        //execute(newThreadPerTaskExecutor(Thread.ofVirtual().factory()),200);
        //execute(newVirtualThreadPerTaskExecutor(),200);
        scheduled();
    }

    private static void scheduled(){
        try(var executor = newSingleThreadScheduledExecutor(Thread.ofVirtual().factory())){
            executor.scheduleAtFixedRate(() -> {
                log.info("Executing scheduled task");
            },0,1, TimeUnit.SECONDS);
            CommonUtils.sleep(Duration.ofSeconds(10));
        }
    }

    private static void execute(ExecutorService service,int taskCount) {
        try(service){
            for(int i=0; i<taskCount; i++) {
                int finalI = i;
                service.submit(() -> inTask(finalI));
            }
            log.info("Task Submitted...");
        }
    }

    private static void inTask(int i){
        log.info("Executing task #{}: Thread info {}", i, Thread.currentThread());
        CommonUtils.sleep(Duration.ofSeconds(5));
        log.info("Executed task #{}: Thread info {}", i, Thread.currentThread());
    }
}
