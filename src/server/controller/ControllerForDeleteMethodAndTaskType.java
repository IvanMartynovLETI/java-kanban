package server.controller;

import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import task.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ControllerForDeleteMethodAndTaskType extends ControllerForGetAndDeleteMethods {

    public ControllerForDeleteMethodAndTaskType (HttpExchange exchange, TaskManager taskManager) {
        super(exchange, taskManager);
    }

    public boolean isTskStorageEmpty(TaskManager taskManager) {
        return taskManager.getList(new Task(0,"task1", "1st task")).isEmpty();
    }

    public boolean taskPresenceInStorage(int iD) {
        boolean isTaskExist = true;

        try {
            taskManager.getTaskById(iD);
        } catch (NullPointerException e) {
            isTaskExist = false;
        }

        return isTaskExist;
    }

    public void processingOfTasks(HttpExchange exchange, TaskManager taskManager) throws IOException {
        int responseCode = 200;
        String response = "OK";
        taskManager.deleteAllTasksSameKind(new Task(0, "", ""));

        exchange.sendResponseHeaders(responseCode, 0);

        try(OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }

    public void processingOfSingleTsk(HttpExchange httpExchange, int iD) throws IOException {
        int responseCode = 200;
        String response = "OK";
        taskManager.deleteTopLevelTaskById(iD, new Task(0, "", ""));

        exchange.sendResponseHeaders(responseCode, 0);

        try(OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
