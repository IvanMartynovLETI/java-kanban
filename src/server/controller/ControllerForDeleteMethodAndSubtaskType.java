package server.controller;

import com.sun.net.httpserver.HttpExchange;
import manager.FileBackedTasksManager;
import manager.TaskManager;
import task.Epic;
import task.Subtask;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ControllerForDeleteMethodAndSubtaskType extends ControllerForGetAndDeleteMethods {
    public ControllerForDeleteMethodAndSubtaskType (HttpExchange exchange, TaskManager taskManager) {
        super(exchange, taskManager);
    }

    public boolean isTskStorageEmpty(TaskManager taskManager) {
        return taskManager.getList(new Subtask(0, "", "",
                new Epic(0, "", ""))).isEmpty();
    }

    public boolean taskPresenceInStorage(int iD) {
        boolean isTaskExist = true;

        try {
            taskManager.getSubtaskById(iD);
        } catch (NullPointerException e) {
            isTaskExist = false;
        }

        return isTaskExist;
    }

    public void processingOfTasks(HttpExchange exchange, TaskManager taskManager) throws IOException {
        int responseCode = 200;
        String response = "OK";
        taskManager.deleteAllTasksSameKind(new Subtask(0, "", "",
                new Epic(0, "", "")));

        exchange.sendResponseHeaders(responseCode, 0);

        try(OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }

    public void processingOfSingleTsk(HttpExchange exchange, int iD) throws IOException {
        int responseCode = 200;
        String response = "OK";
        ((FileBackedTasksManager)taskManager).deleteSubtaskById(iD);

        exchange.sendResponseHeaders(responseCode, 0);

        try(OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
