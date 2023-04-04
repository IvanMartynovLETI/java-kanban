package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import manager.*;
import server.controller.*;
import task.Epic;
import task.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private final TaskManager taskManager;
    private final HttpServer httpServer;

    private static final int PORT = 8081;

    public HttpTaskServer() throws IOException {
        taskManager = Managers.getDefault();
        httpServer = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        httpServer.createContext("/tasks/task/", this::taskHandler);
        httpServer.createContext("/tasks/subtask/", this::subtaskHandler);
        httpServer.createContext("/tasks/epic/", this::epicHandler);
        httpServer.createContext("/tasks/subtask/epic/", this::subtasksOfEpicHandler);
        httpServer.createContext("/tasks/history", this::historyHandler);
        httpServer.createContext("/tasks/", this::prioritizedTasksHandler);
    }

    private void taskHandler(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET" -> new ControllerForGetMethodAndTaskType(exchange, taskManager).proceeding(exchange,
                    taskManager, true);
            case "DELETE" -> new ControllerForDeleteMethodAndTaskType(exchange, taskManager).proceeding(exchange,
                    taskManager, true);
            case "POST" -> new ControllerForPostMethodAndTaskType(exchange,taskManager).proceeding(exchange,
                    taskManager);
            default -> {
                exchange.sendResponseHeaders(400, 0);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write("Bad request".getBytes());
                }
            }
        }
    }

    private void subtaskHandler(HttpExchange exchange) throws IOException{
        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET" -> new ControllerForGetMethodAndSubtaskType(exchange, taskManager).proceeding(exchange,
                    taskManager, true);
            case "DELETE" -> new ControllerForDeleteMethodAndSubtaskType(exchange, taskManager).proceeding(exchange,
                    taskManager, true);
            case "POST" -> new ControllerForPostMethodAndSubtaskType(exchange, taskManager).proceeding(exchange,
                    taskManager);
            default -> {
                exchange.sendResponseHeaders(400, 0);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write("Bad request".getBytes());
                }
            }
        }
    }

    private void epicHandler(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET" -> new ControllerForGetMethodAndEpicType(exchange, taskManager).proceeding(exchange,
                    taskManager, true);
            case "DELETE" -> new ControllerForDeleteMethodAndEpicType(exchange, taskManager).proceeding(exchange,
                    taskManager, true);
            case "POST" -> new ControllerForPostMethodAndEpicType(exchange, taskManager).proceeding(exchange,
                    taskManager);
            default -> {
                exchange.sendResponseHeaders(400, 0);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write("Bad request".getBytes());
                }
            }
        }
    }

    private void subtasksOfEpicHandler(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if (method.equals("GET")) {
            new ControllerForGetSubtasksFromEpic(exchange, taskManager).proceeding(exchange, taskManager,
                    false);
        } else {
            exchange.sendResponseHeaders(400, 0);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write("Bad request".getBytes());
            }
        }
    }

    private void historyHandler(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if (method.equals("GET")) {
            new ControllerForGetHistoryMethod(exchange).proceeding(exchange,
                    taskManager);
        } else {
            exchange.sendResponseHeaders(400, 0);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write("Bad request".getBytes());
            }
        }
    }

    private void prioritizedTasksHandler(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if (method.equals("GET")) {
            new ControllerForGetMethodAndPrioritizedTasks(exchange).proceeding(exchange, taskManager);
        } else {
            exchange.sendResponseHeaders(400, 0);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write("Bad request".getBytes());
            }
        }
    }

    public void stop() {
        this.httpServer.stop(0);
        taskManager.deleteAllTasksSameKind(new Task(0, "", ""));
        taskManager.deleteAllTasksSameKind(new Epic(0, "", ""));
    }

    public void start() throws IOException {
        this.httpServer.start();
        System.out.println("HTTP server started on " + PORT + " port.");
    }
}
