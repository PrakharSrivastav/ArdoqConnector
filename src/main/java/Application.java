import controller.ArdoqController;
import controller.HealthController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public final class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        // run on port
        port(8080);

        // create routes
        path("/api", () -> {
            // initial filter before any of the routes starts
            before("/*", (q, a) -> logger.info("Received api call"));

            // all the health check endpoint
            get("/health", "application/json", HealthController.check);

            // all the connector endpoints
            path("/connector", () -> {
                post("/systems", "application/json", ArdoqController.syncSystems);
                post("/pipes", "application/json", ArdoqController.syncPipes);
                post("/datasets", "application/json", ArdoqController.syncDatasets);
            });
        });
    }
}

