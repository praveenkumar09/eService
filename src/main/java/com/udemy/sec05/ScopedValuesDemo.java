package com.udemy.sec05;

import com.udemy.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.UUID;

public class ScopedValuesDemo {

    private static final Logger log = LoggerFactory.getLogger(ScopedValuesDemo.class);
    //private static final ThreadLocal<String> SESSION_TOKEN = new ThreadLocal<>();
    private static final ScopedValue<String> SESSION_TOKEN = ScopedValue.newInstance();

    public static void main(String[] args) {
        Thread.ofVirtual().name("virtual-",1).start(ScopedValuesDemo::processIncomingRequest);
        Thread.ofVirtual().name("virtual-",10).start(ScopedValuesDemo::processIncomingRequest);
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static void processIncomingRequest(){
        authenticate();
    }

    private static void authenticate() {
        var token = UUID.randomUUID().toString();
        log.info("Authenticating token: {}", token);
        ScopedValue.runWhere(SESSION_TOKEN,token,() -> controller());
    }

    private static void controller() {
        log.info("Starting controller with session token : {}", SESSION_TOKEN.get());
        service();
    }

    private static void service() {
        log.info("Starting service with session token : {}", SESSION_TOKEN.get());
        //Thread.ofVirtual().start(ScopedValuesDemo::callExternalService);
        callExternalService();
    }

    private static void callExternalService() {
        log.info("Starting callExternalService with session token : {}", SESSION_TOKEN.get());
        //SESSION_TOKEN.remove();
    }
}
