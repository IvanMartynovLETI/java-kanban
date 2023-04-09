package manager;

import java.net.MalformedURLException;
import java.net.URL;

public class Managers {
    private final static String URL_OF_KVSERVER = "http://localhost:8078";
    public static TaskManager getDefault() {
        TaskManager taskManager = null;
        try {
            taskManager = new HttpTaskManager(new URL(URL_OF_KVSERVER));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return taskManager;
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}