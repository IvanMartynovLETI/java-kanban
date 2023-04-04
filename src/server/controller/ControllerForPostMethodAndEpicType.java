package server.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import manager.ManagerSaveException;
import manager.TaskManager;
import task.Epic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerForPostMethodAndEpicType extends ControllerForPostMethod{

    public ControllerForPostMethodAndEpicType (HttpExchange exchange, TaskManager taskManager) {
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
            taskManager.getEpicById(iDToCheck);
        } catch (NullPointerException e) {
            iDToCheck =0;
        }

        return iDToCheck;
    }

    public void taskCreator(List<String> taskElements, TaskManager taskManager) throws ManagerSaveException {
        Epic epic= taskManager.createEpic(taskElements.get(1), taskElements.get(2));
        taskManager.put(epic);
    }

    public void taskUpdater(int iD, TaskManager taskManager) {
        taskManager.update(taskManager.getEpicById(iD));
    }
}