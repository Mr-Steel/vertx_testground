package net.steel.vertx.verticles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.platform.Verticle;

/**
 * Created by Mr__Steel on 29.11.2014.
 */

public class StarterVerticle extends Verticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(StarterVerticle.class);

    public void start() {

        LOGGER.info("Deploying Verticles: ");

        LOGGER.info(" ---> " + LoggerVerticle.class.getName());
        container.deployWorkerVerticle(LoggerVerticle.class.getName(), 1);

        LOGGER.info(" ---> " + ControlVerticle.class.getName());
        container.deployWorkerVerticle(ControlVerticle.class.getName(), 1);
    }
}
