package com.udemy.sec05;

import com.sun.jdi.PathSearchingVirtualMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;


public class ThreadLocalDemo {

    private static final Logger log = LoggerFactory.getLogger(ThreadLocalDemo.class);
    private static ThreadLocal<String> SESSION_TOKEN = new ThreadLocal<>();

    public static void main(String[] args) {
        Thread.ofVirtual().name("virtual-",1).start(ThreadLocalDemo::processIncomingRequest);
        Thread.ofPlatform().name("virtual-",10).start(ThreadLocalDemo::processIncomingRequest);
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
        callExternalService();
    }

    private static void callExternalService() {
        log.info("Starting callExternalService with session token : {}", SESSION_TOKEN.get());
    }
}
