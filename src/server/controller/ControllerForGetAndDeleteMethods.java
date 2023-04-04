package server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import server.adapter.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

public abstract class ControllerForGetAndDeleteMethods {
    protected TaskManager taskManager;
    protected HttpExchange exchange;
    protected Gson gson;



    public ControllerForGetAndDeleteMethods(HttpExchange exchange, TaskManager taskManager) {
        this.exchange = exchange;
        this.taskManager = taskManager;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        gsonBuilder.serializeNulls();
        this.gson = gsonBuilder.create();
    }

    public abstract boolean isTskStorageEmpty(TaskManager taskManager);
    public abstract boolean taskPresenceInStorage(int iD);

    public abstract void processingOfTasks(HttpExchange httpExchange, TaskManager taskManager) throws IOException;
    public abstract void processingOfSingleTsk(HttpExchange httpExchange, int iD) throws IOException;

    public void proceeding(HttpExchange exchange, TaskManager taskManager, boolean isMethodGetOrDelete)
            throws IOException {
        boolean isSendFinalResponse = true;
        String response = null;
        int responseCode = 0;

        if(exchange.getRequestURI().getQuery()==null) {
            if(isTskStorageEmpty(taskManager)) {
                if(isMethodGetOrDelete) {
                    response = "No content";
                    responseCode = 204;
                } else {
                    response = "Bad request";
                    responseCode = 400;
                }

            } else {
                processingOfTasks(exchange, taskManager);
                isSendFinalResponse = false;
            }
        } else if(!exchange.getRequestURI().getQuery().startsWith("id=")) {
            response = "Bad request";
            responseCode = 400;
        } else {
            boolean isNumber = true;
            int iD = 0;

            try {
                iD = Integer.parseInt(exchange.getRequestURI().getQuery().split("=")[1]);
            } catch (NumberFormatException e) {
                isNumber = false;
            }

            if(!isNumber) {
                response = "Bad request";
                responseCode = 400;
            } else {
                boolean isTskPresent = taskPresenceInStorage(iD);

                if(!isTskPresent) {
                    response = "Not found";
                    responseCode = 404;
                } else {
                    processingOfSingleTsk(exchange, iD);
                    isSendFinalResponse = false;
                }
            }
        }

        if(isSendFinalResponse) {
            exchange.sendResponseHeaders(responseCode,0);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}