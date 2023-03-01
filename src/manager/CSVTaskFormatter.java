package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public class CSVTaskFormatter {
    public static <T extends Task> String toString(T t) {
        String taskType;

        if (t instanceof Epic) {
            taskType = "EPIC";
        } else if (t instanceof Subtask) {
            taskType = "SUBTASK";
        } else {
            taskType = "TASK";
        }

        String taskStr = t.getId() + "," + taskType + "," + t.getName() + "," + t.getStatus() + ","
                + t.getDescription() + ",";
        if (t.getEpicId() == 0) {
            taskStr += " \r\n";
        } else {
            taskStr += t.getEpicId() + "\r\n";
        }

        return taskStr;
    }

    public static Task fromString(String str) {
        String[] descriptors = str.split(",");
        int id = Integer.parseInt(descriptors[0]);
        TaskType taskType = TaskType.valueOf(descriptors[1]);
        String name = descriptors[2];
        Status status = Status.valueOf(descriptors[3]);
        String description = descriptors[4];
        int upperEpcId;

        if (descriptors[5].equals(" ")) {
            upperEpcId = 0;
        } else {
            upperEpcId = Integer.parseInt(descriptors[5]);
        }

        switch (taskType) {
            case TASK -> {
                Task restoredTask = new Task(id, name, description);
                restoredTask.setStatus(status);
                return restoredTask;
            }
            case EPIC -> {
                Epic restoredEpic = new Epic(id, name, description);
                restoredEpic.setStatus(status);
                return restoredEpic;
            }
            case SUBTASK -> {

                Subtask restoredSubtask = new Subtask(id, name, description,
                        new Epic(upperEpcId, "", ""));
                restoredSubtask.setStatus(status);
                return restoredSubtask;
            }
            default -> {
                return null;
            }
        }
    }

    public static String historyToString(HistoryManager manager) {
        ArrayList<String> listOfTasksIds = new ArrayList<>();

        for (Task task : manager.getHistory()) {
            listOfTasksIds.add(Integer.toString(task.getId()));
        }

        return String.join(",", listOfTasksIds);
    }

    public static List<Integer> historyFromString(String value) {
        String[] historySubstrings = value.split(",");
        List<Integer> listOfWatchedTasksIds = new ArrayList<>();

        for (String element : historySubstrings) {
            listOfWatchedTasksIds.add(Integer.parseInt(element));
        }

        return listOfWatchedTasksIds;
    }

    public static String getHeader() {
        return "id,type,name,status,description,epic\n";
    }
}