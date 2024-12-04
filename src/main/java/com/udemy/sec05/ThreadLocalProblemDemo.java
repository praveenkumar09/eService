package com.udemy.sec05;

import com.udemy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.UUID;

public class ThreadLocalProblemDemo {

    private static final Logger log = LoggerFactory.getLogger(ThreadLocalProblemDemo.class);
    //private static final ThreadLocal<String> SESSION_TOKEN = new ThreadLocal<>();
    private static final ThreadLocal<String> SESSION_TOKEN = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        Thread.ofVirtual().name("virtual-",1).start(ThreadLocalProblemDemo::processIncomingRequest);
        Thread.ofVirtual().name("virtual-",10).start(ThreadLocalProblemDemo::processIncomingRequest);
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static void processIncomingRequest(){
        authenticate();
        controller();
    }

    private static void authenticate() {
        var token = UUID.randomUUID().toString();
        log.info("Authenticating token: {}", token);
        SESSION_TOKEN.set(token);
    }

    private static void controller() {
        log.info("Starting controller with session token : {}", SESSION_TOKEN.get());
        service();
    }

    private static void service() {
        log.info("Starting service with session token : {}", SESSION_TOKEN.get());
        Thread.ofVirtual().start(ThreadLocalProblemDemo::callExternalService);
    }

    private static void callExternalService() {
        log.info("Starting callExternalService with session token : {}", SESSION_TOKEN.get());
        //SESSION_TOKEN.remove();
        SESSION_TOKEN.set(UUID.randomUUID().toString());
    }
}
