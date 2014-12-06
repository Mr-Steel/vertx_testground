package net.steel.vertx.verticles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.platform.Verticle;

/**
 * Created by Bjoern on 05.12.2014.
 */
public class ControlVerticle extends Verticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControlVerticle.class);

    private String deploymentId;

    private boolean onHold = false;
    private HttpServerRequest resetRequestForAnswer;

    private EventBus eventBus;

    public void start() {
        eventBus = vertx.eventBus();

        RouteMatcher routeMatcher = new RouteMatcher();
        routeMatcher.get("/tester/start", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest startRequest) {
                if (!onHold) {
                    startTheEngines(startRequest);
                } else {
                    startRequest.response().end("System is busy - please try again in some seconds!");
                }
            }
        });

        routeMatcher.get("/tester/status", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest statusRequest) {
                statusRequest.response().end("DeploymentId: " + deploymentId);
            }
        });

        routeMatcher.get("/tester/stop", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest stopRequest) {
                onHold = true;
                LOGGER.info(" ---> Stopping " + WorkerVerticle1.class.getName());
                container.undeployVerticle(deploymentId, new AsyncResultHandler<Void>() {
                    @Override
                    public void handle(AsyncResult<Void> voidAsyncResult) {
                        if (voidAsyncResult.succeeded()) {
                            LOGGER.info("Undeployed WorkerVerticle 1!");
                        } else {
                            LOGGER.info("Failed to undeploy WorkerVerticle 1");
                        }

                    }
                });
                onHold = false;
                stopRequest.response().end("WorkerVerticle 1 has been stopped!");
            }
        });

        vertx.createHttpServer().requestHandler(routeMatcher).listen(8181, "localhost");
        LOGGER.info("WorkerVerticle deployment test started, listening on port: 8181");
    }

    private void startTheEngines(final HttpServerRequest startRequest) {

        container.deployWorkerVerticle(WorkerVerticle1.class.getName(), null, 2, false, new AsyncResultHandler<String>() {
            @Override
            public void handle(AsyncResult<String> asyncResult) {
                if (asyncResult.succeeded()) {
                    deploymentId = asyncResult.result();
                    LOGGER.info(WorkerVerticle1.class.getName() + " has been deployed. DeploymentID: " + deploymentId);
                    startRequest.response().end("WorkerVerticle 1 has been started!");
                } else {
                    LOGGER.error(asyncResult.cause().getStackTrace().toString());
                }
            }
        });
    }

}
