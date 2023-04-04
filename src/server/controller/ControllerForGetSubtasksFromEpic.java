package server.controller;

import com.sun.net.httpserver.HttpExchange;
import manager.FileBackedTasksManager;
import manager.TaskManager;
import task.Subtask;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ControllerForGetSubtasksFromEpic extends ControllerForGetAndDeleteMethods{

    public ControllerForGetSubtasksFromEpic (HttpExchange exchange, TaskManager taskManager) {
        super(exchange, taskManager);
    }

    public boolean isTskStorageEmpty(TaskManager taskManager) {
        return true;
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
    }

    public void processingOfSingleTsk(HttpExchange httpExchange, int iD) throws IOException {
        int responseCode;
        List<Subtask> listOfSubtasks = ((FileBackedTasksManager)taskManager).getListOfSubtasksByUpperEpicId(iD);
        String response;

        if (listOfSubtasks.isEmpty()) {
            responseCode = 204;
            response = "No content";
        } else {
            responseCode = 200;
            response = gson.toJson(listOfSubtasks);
        }

        exchange.sendResponseHeaders(responseCode, 0);

        try(OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}