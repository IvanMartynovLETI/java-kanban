package server.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import manager.FileBackedTasksManager;
import manager.ManagerSaveException;
import manager.TaskManager;
import task.Task;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ControllerForPostMethodAndTaskType extends ControllerForPostMethod{

    public ControllerForPostMethodAndTaskType (HttpExchange exchange, TaskManager taskManager) {
        super(exchange, taskManager);
    }

    public List<String> taskReader(HttpExchange exchange) throws IOException {
        List<String> taskElements = new ArrayList<>();
        boolean isTaskCorrect = true;

        try {

            JsonElement jsonElement = JsonParser.parseString(super.readBody(exchange));
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            if(!jsonObject.get("id").getAsString().isEmpty()) {
                try {
                    int iD = jsonObject.get("id").getAsInt();

                    if(iD <=0) {
                        isTaskCorrect = false;
                    }

                } catch (NumberFormatException e) {
                    isTaskCorrect = false;
                }

                taskElements.add(jsonObject.get("id").getAsString());
            } else {
                isTaskCorrect = false;
            }

            if(!jsonObject.get("name").getAsString().isEmpty()) {
                taskElements.add(jsonObject.get("name").getAsString());
            } else {
                isTaskCorrect = false;
            }

            if(!jsonObject.get("description").getAsString().isEmpty()) {
                taskElements.add(jsonObject.get("description").getAsString());
            } else {
                isTaskCorrect = false;
            }

            if(!jsonObject.get("startTime").getAsString().isEmpty()) {
                try {
                    LocalDateTime ldtm = LocalDateTime.parse(jsonObject.get("startTime").getAsString(),
                            FileBackedTasksManager.DATE_TIME_FORMATTER);

                    taskElements.add(ldtm.format(FileBackedTasksManager.DATE_TIME_FORMATTER));

                } catch (NumberFormatException e) {
                    isTaskCorrect = false;
                }
            } else {
                taskElements.add("empty");
            }

            if(!jsonObject.get("duration").getAsString().isEmpty()) {
                try {
                    long duration = jsonObject.get("duration").getAsLong();

                    if(duration <= 0) {
                        throw new IOException("Incorrect duration");
                    }

                    taskElements.add(Long.toString(duration));

                } catch (NumberFormatException | IOException e) {
                    isTaskCorrect = false;
                }
            }

            if(!isTaskCorrect) {
                taskElements.clear();
            }

        } catch (NullPointerException e) {
            taskElements.clear();
        }

        return taskElements;
    }

    public int findIdIfTaskIsPresentInStorage(List<String> taskElements, TaskManager taskManager) {
        int iDToCheck = Integer.parseInt(taskElements.get(0));

        try {
            taskManager.getTaskById(iDToCheck);
        } catch (NullPointerException e) {
            iDToCheck =0;
        }

        return iDToCheck;
    }

    public void taskCreator(List<String> taskElements, TaskManager taskManager) throws ManagerSaveException {
        Task task= taskManager.createTask(taskElements.get(1), taskElements.get(2));

        if(taskElements.get(3).equals("empty")) {
            task.setDuration(Long.parseLong(taskElements.get(4)));
        } else {
            task.setStartTime(taskElements.get(3));
            task.setDuration(Long.parseLong(taskElements.get(4)));
        }

        taskManager.put(task);
    }

    public void taskUpdater(int iD, TaskManager taskManager) {
        taskManager.update(taskManager.getTaskById(iD));
    }
}