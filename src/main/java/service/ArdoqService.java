package service;

import spark.Request;

public interface ArdoqService {
    void syncComponent(Request request, String componentType);
}
