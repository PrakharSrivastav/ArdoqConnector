package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

// This class perform the health check of the application
public final class HealthController {

    private static Logger logger = LoggerFactory.getLogger(HealthController.class);

    public static Route check = (final Request request, final Response response) -> {
        logger.info("Health check endpoint");
        logger.info("input is {}", request.body());
        return "ok";
    };
}
