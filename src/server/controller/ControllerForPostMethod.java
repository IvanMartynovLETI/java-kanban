package server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import manager.ManagerSaveException;
import manager.TaskManager;
import server.adapter.DurationAdapter;
import server.adapter.LocalDateTimeAdapter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public abstract class ControllerForPostMethod {
    protected HttpExchange exchange;
    protected Gson gson;
    protected TaskManager taskManager;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public ControllerForPostMethod (HttpExchange exchange, TaskManager taskManager) {
        this.exchange = exchange;
        this.taskManager = taskManager;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        gsonBuilder.serializeNulls();
        this.gson = gsonBuilder.create();
    }
    public String readBody(HttpExchange exchange)  throws  IOException{
        return new String(exchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
    }

    public abstract List<String> taskReader(HttpExchange exchange) throws IOException;

    public abstract int findIdIfTaskIsPresentInStorage(List<String> taskElements, TaskManager taskManager);
    public abstract void taskCreator(List<String> taskElements, TaskManager taskManager) throws ManagerSaveException;
    public abstract void taskUpdater(int iD, TaskManager taskManager);

    public void proceeding(HttpExchange exchange, TaskManager taskManager) {
        String response = null;
        int responseCode;
        List<String> taskElements;

        try {
            if(!(exchange.getRequestURI().getQuery() == null)) {
                response = "Bad request";
                responseCode = 400;
            } else{
                taskElements = taskReader(exchange);

                if(taskElements.isEmpty()) {
                    response = "Bad request";
                    responseCode = 400;
                } else {
                    int iD = findIdIfTaskIsPresentInStorage(taskElements, taskManager);
                    if(iD != 0) {
                        taskUpdater(iD, taskManager);
                        response = "OK";
                        responseCode = 200;
                    } else {
                        try{
                            taskCreator(taskElements, taskManager);
                            response = "Created";
                            responseCode = 201;
                        } catch (ManagerSaveException e) {
                            response = "Bad request";
                            responseCode = 400;
                        }
                    }
                }
            }

            exchange.sendResponseHeaders(responseCode,0);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        try (OutputStream os = exchange.getResponseBody()) {
            assert response != null;
            os.write(response.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}