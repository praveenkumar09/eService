package com.udemy.sec06;

import com.udemy.sec03.Airfare;
import com.udemy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.ThreadLocalRandom;

public class ScopedValueWithStructuredScope {

    private static final Logger log = LoggerFactory.getLogger(ScopedValueWithStructuredScope.class);
    private static final ScopedValue<String> SESSION_TOKEN = ScopedValue.newInstance();
    public static void main(String[] args) {
        ScopedValue.runWhere(SESSION_TOKEN,"token-123",() -> task());
    }

    private static void task(){

        log.info("token : {}",SESSION_TOKEN.get());

        try(StructuredTaskScope taskScope = new StructuredTaskScope<>()){
            Subtask deltaSubTask = taskScope.fork(() -> getDeltaAirfare());
            Subtask frontierSubTask = taskScope.fork(() -> getFrontierAirfare());

            //taskScope.joinUntil(Instant.now().plusMillis(1500));
            taskScope.join();


            log.info("Delta Subtask : {}, Status : {}",deltaSubTask.get(),deltaSubTask.state());
            log.info("Frontier Subtask : {}, Status : {}",frontierSubTask.get(),frontierSubTask.state());
        } catch (InterruptedException e) {
            log.error("Error : ",e);
            throw new RuntimeException(e);
        }
    }

    private static Airfare getDeltaAirfare(){
            var random = ThreadLocalRandom.current().nextInt(108,1808);
            log.info("token : {}",SESSION_TOKEN.get());
            CommonUtils.sleep("Delta",Duration.ofSeconds(1));
            return new Airfare("Delta", random);
    }

    private static Airfare getFrontierAirfare(){
            var random = ThreadLocalRandom.current().nextInt(108,1808);
        log.info("token : {}",SESSION_TOKEN.get());
            CommonUtils.sleep("Frontier",Duration.ofSeconds(2));
            return new Airfare("Frontier", random);
    }

    private static Airfare failingTask(){
        throw new RuntimeException("Failing Task");
    }
}
