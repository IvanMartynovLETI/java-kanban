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
                + t.getDescription() + ","
                + t.getStartDateTime().format(FileBackedTasksManager.DATE_TIME_FORMATTER) + ","
                + t.getDuraTion().toMinutes() + "," + t.getEndTime().format(FileBackedTasksManager.DATE_TIME_FORMATTER)
                + ",";
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

        if (descriptors[8].equals(" ")) {
            upperEpcId = 0;
        } else {
            upperEpcId = Integer.parseInt(descriptors[8]);
        }

        switch (taskType) {
            case TASK -> {
                Task restoredTask = new Task(id, name, description);
                restoredTask.setStatus(status);
                restoredTask.setStartTime(descriptors[5]);
                restoredTask.setDuration(Long.parseLong(descriptors[6]));
                restoredTask.setEndTime();
                return restoredTask;
            }
            case EPIC -> {
                Epic restoredEpic = new Epic(id, name, description);
                restoredEpic.setStatus(status);
                restoredEpic.setStartTime(descriptors[5]);
                restoredEpic.setDuration(Long.parseLong(descriptors[6]));
                restoredEpic.setEndTime(descriptors[7]);
                return restoredEpic;
            }
            case SUBTASK -> {

                Subtask restoredSubtask = new Subtask(id, name, description,
                        new Epic(upperEpcId, "", ""));
                restoredSubtask.setStatus(status);
                restoredSubtask.setStartTime(descriptors[5]);
                restoredSubtask.setDuration(Long.parseLong(descriptors[6]));
                restoredSubtask.setEndTime();
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
        List<Integer> listOfWatchedTasksIds = new ArrayList<>();
        if (!value.isEmpty()) {
            String[] historySubstrings = value.split(",");

            for (String element : historySubstrings) {
                listOfWatchedTasksIds.add(Integer.parseInt(element));
            }
        }

        return listOfWatchedTasksIds;
    }

    public static String getHeader() {
        return "id,type,name,status,description,setStartTime,duration,endTime,epic\n";
    }
}