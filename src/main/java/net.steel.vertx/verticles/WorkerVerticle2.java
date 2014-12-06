package net.steel.vertx.verticles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

/**
 * Created by Mr__Steel on 05.12.2014.
 */
public class WorkerVerticle2 extends Verticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerVerticle1.class);

    EventBus eventBus;

    public void start() {
        LOGGER.info("Starting WorkerVerticle 2 ...");
        eventBus = vertx.eventBus();
        eventBus.registerHandler("WorkerVerticle2", new Handler<Message>() {
            @Override
            public void handle(Message message) {

            }
        });
    }

    public void stop() {
        LOGGER.info("Stopping WorkerVerticle 2 ...");
    }

}
