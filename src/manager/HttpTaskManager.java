package manager;

import client.KVTaskClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.adapter.*;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager{
    private final KVTaskClient client;
    private final static String keyForLoadSave = "HEAP";
    protected static Gson gson;

    public HttpTaskManager(URL url) {
        super(new File("log/log.csv"));
        this.client = new KVTaskClient(url);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        gsonBuilder.serializeNulls();
        gson = gsonBuilder.create();
    }

    public static HttpTaskManager load(URL url) throws ManagerSaveException {
        HttpTaskManager httpTaskManager = new HttpTaskManager(url);
        String allInOne = gson.fromJson(httpTaskManager.client.load(keyForLoadSave), String.class);

        if(allInOne.isEmpty()) {
            throw new ManagerSaveException("Log is empty, nothing to restore.");
        } else {
            ArrayList<String> listOfStrings =
                    new ArrayList<>(List.of(allInOne.split("\\r?\\n|\\r")));

            if(!httpTaskManager.hasAnyKindOfTask(listOfStrings)) {
                throw new ManagerSaveException("Ann error occurred while restoring from log.");
            }

            httpTaskManager.fillTimeGridFromEndStringOfFile(listOfStrings);
            httpTaskManager.tempEndDateTime = httpTaskManager.restoreTempEndDateTimeFromListOfStrings(listOfStrings);
            httpTaskManager.prioritizedTasks.clear();
            int maxId = httpTaskManager.getMaxId(listOfStrings);
            httpTaskManager.fillMaps(listOfStrings, httpTaskManager);
            httpTaskManager.attachSubtasksToEpics(httpTaskManager);
            httpTaskManager.restoreHistory(listOfStrings, httpTaskManager);
            httpTaskManager.id = maxId;
        }

        return httpTaskManager;
    }

    @Override
    protected void save(){
        StringBuilder allInOne = new StringBuilder();
        allInOne.append(CSVTaskFormatter.getHeader());

        for (Task task : tasks.values()) {
            allInOne.append(CSVTaskFormatter.toString(task));
        }

        for (Epic epic : epics.values()) {
            allInOne.append(CSVTaskFormatter.toString(epic));
        }

        for(Subtask subtask : subtasks.values()) {
            allInOne.append(CSVTaskFormatter.toString(subtask));
        }

        allInOne.append("\r\n");
        allInOne.append(CSVTaskFormatter.historyToString(historyManager));
        allInOne.append("\r\n\r\n");

        allInOne.append(FileBackedTasksManager.startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));
        allInOne.append(",");
        allInOne.append(FileBackedTasksManager.endDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));
        allInOne.append(",");
        allInOne.append(tempEndDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));

        client.put("HEAP", gson.toJson(allInOne.toString()));
    }
}