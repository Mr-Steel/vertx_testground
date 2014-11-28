package net.steel.vertx.verticles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

/**
 * Created by Mr__Steel on 29.11.2014.
 */
public class LoggerVerticle extends Verticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerVerticle.class);

    private EventBus eventBus;

    public void start() {
        LOGGER.info("Starting LoggerVerticle ... ");

        eventBus = vertx.eventBus();
        eventBus.registerHandler(LoggerVerticle.class.getSimpleName(), new LoggerHandler());
    }


    private class LoggerHandler implements Handler<Message<String>> {

        @Override
        public void handle(Message<String> event) {
            LOGGER.info(event.body());
        }
    }
}
