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
public class WorkerVerticle1 extends Verticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerVerticle1.class);
    private String deploymentId;

    EventBus eventBus;

    public void start() {
        LOGGER.info("Starting WorkerVerticle 1 ...");
        eventBus = vertx.eventBus();
        startTheEngines();
        eventBus.registerHandler("WorkerVerticle1", new Handler<Message>() {
            @Override
            public void handle(Message message) {

            }
        });
    }

    public void stop() {
        LOGGER.info("Stopping WorkerVerticle 1 ...");
    }

    private void startTheEngines() {

        container.deployWorkerVerticle(WorkerVerticle2.class.getName(), null, 10, false, new AsyncResultHandler<String>() {
            @Override
            public void handle(AsyncResult<String> asyncResult) {
                if (asyncResult.succeeded()) {
                    deploymentId = asyncResult.result();
                    LOGGER.info(WorkerVerticle2.class.getName() + " has been deployed. DeploymentID: " + deploymentId);
                } else {
                    LOGGER.error(asyncResult.cause().getStackTrace().toString());
                }
            }
        });
    }

}
