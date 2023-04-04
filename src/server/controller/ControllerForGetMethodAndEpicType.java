package server.controller;

import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import task.Epic;

import java.io.IOException;
import java.io.OutputStream;

public class ControllerForGetMethodAndEpicType extends ControllerForGetAndDeleteMethods {

    public ControllerForGetMethodAndEpicType (HttpExchange exchange, TaskManager taskManager) {
        super(exchange, taskManager);
    }

    public boolean isTskStorageEmpty(TaskManager taskManager) {
        return taskManager.getList(new Epic(0, "", "")).isEmpty();
    }

    public boolean taskPresenceInStorage(int iD) {
        boolean isTaskExist = true;

        try {
            taskManager.getEpicById(iD);
        } catch (NullPointerException e) {
            isTaskExist = false;
        }

        return isTaskExist;
    }

    public void processingOfTasks(HttpExchange exchange, TaskManager taskManager) throws IOException {
        int responseCode = 200;

        exchange.sendResponseHeaders(responseCode, 0);

        try(OutputStream os = exchange.getResponseBody()) {
            os.write(gson.toJson(taskManager.getList(new Epic(0, "", ""))).getBytes());
        }
    }

    public void processingOfSingleTsk(HttpExchange exchange, int iD) throws IOException {
        int responseCode = 200;

        exchange.sendResponseHeaders(responseCode, 0);

        try(OutputStream os = exchange.getResponseBody()) {
            os.write(gson.toJson(taskManager.getEpicById(iD)).getBytes());
        }
    }
}