package server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import manager.FileBackedTasksManager;
import manager.TaskManager;
import server.adapter.*;
import task.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class ControllerForGetMethodAndPrioritizedTasks {
    protected HttpExchange exchange;
    protected Gson gson;

    public ControllerForGetMethodAndPrioritizedTasks (HttpExchange exchange) {
        this.exchange = exchange;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        gsonBuilder.serializeNulls();
        this.gson = gsonBuilder.create();
    }

    public void proceeding(HttpExchange exchange, TaskManager taskManager) throws IOException {
        String response;
        int responseCode;

        if (exchange.getRequestURI().getQuery() == null) {
            List<Task> prioritizedTasks = ((FileBackedTasksManager)taskManager).getPrioritizedTasks();

            if(prioritizedTasks.isEmpty()) {
                response = "No content";
                responseCode = 204;
            } else {
                response = gson.toJson(prioritizedTasks);
                responseCode = 200;
            }

        } else {
            response = "Bad request";
            responseCode = 400;
        }

        exchange.sendResponseHeaders(responseCode, 0);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}