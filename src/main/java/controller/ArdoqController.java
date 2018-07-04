package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ArdoqService;
import service.ArdoqServiceImpl;
import spark.Request;
import spark.Response;
import spark.Route;

public final class ArdoqController {

    private static Logger logger = LoggerFactory.getLogger(ArdoqController.class);
    private static ArdoqService ardoqService = new ArdoqServiceImpl();

    public static Route syncDatasets = (final Request request, final Response response) -> {
        logger.info("syncDatasets :: Start");
        try {
            ardoqService.syncComponent(request, "dataset");
        } catch (Exception e) {
            logger.error("error is ", e);
        }
        return "ok";
    };


    public static Route syncSystems = (final Request request, final Response response) -> {
        logger.info("sync Systems start");
        ardoqService.syncComponent(null, "system");
        return null;
    };

    public static Route syncPipes = (final Request request, final Response response) -> {
        logger.info("sync Pipes start");
        ardoqService.syncComponent(null, "pipe");
        return null;
    };

}
