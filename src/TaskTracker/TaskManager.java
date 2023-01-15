package TaskTracker;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int taskID = 0;
    private final HashMap<Integer, Task> taskHashMap = new HashMap<>();
    private final HashMap<Integer, Subtask> subtaskHashMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicHashMap = new HashMap<>();

    private int generateTaskID() {
        return ++taskID;
    }

    //*******************************************************************************************************
    //*****************************************Tasks methods*************************************************
    //*******************************************************************************************************

    public Task createTask(String taskName, String taskDescription) {
        return new Task(generateTaskID(), taskName, taskDescription);
    }

    public void putTask(Task task) {
        taskHashMap.put(task.getTaskID(), task);
    }

    public HashMap<Integer, Task> getListOfTasks() {
        return taskHashMap;
    }

    public Task getTaskByID(int taskID) {
        return taskHashMap.get(taskID);
    }

    public int getTaskID(Task task) {
        return task.getTaskID();
    }

    public void deleteTaskByID(int taskID) {
        taskHashMap.remove(taskID);
    }

    public void updateTask(Task task) {

        switch (task.getTaskStatus()) {
            case "NEW":
                task.taskStatus = "IN_PROGRESS";
                break;
            case "IN_PROGRESS":
                task.taskStatus = "DONE";
                break;
            default: //reserved for future use
                break;
        }

        deleteTaskByID(task.getTaskID());
        putTask(task);
    }

    public void deleteAllTasks() {
        taskHashMap.clear();
    }

    //*******************************************************************************************************
    //**************************************Subtasks methods*************************************************
    //*******************************************************************************************************

    public Subtask createSubtask(String subtaskName, String subtaskDescription, Epic epic) {
        return new Subtask(generateTaskID(), subtaskName, subtaskDescription, epic);
    }

    public void putSubtask(Subtask subtask) {
        subtaskHashMap.put(subtask.getTaskID(), subtask);
    }

    public HashMap<Integer, Subtask> getListOfSubtasks() {
        return subtaskHashMap;
    }

    public Task getSubtaskByID(int subtaskID) {
        return subtaskHashMap.get(subtaskID);
    }

    public int getSubtaskID(Subtask subtask) {
        return subtask.getTaskID();
    }

    public void deleteSubtaskByID(int subtaskID) {
        subtaskHashMap.remove(subtaskID);
    }

    public void updateSubtask(Subtask subtask) {

        switch (subtask.getTaskStatus()) {
            case "NEW":
                subtask.taskStatus = "IN_PROGRESS";
                break;
            case "IN_PROGRESS":
                subtask.taskStatus = "DONE";
                break;
            default: //reserved for future use
                break;
        }

        deleteSubtaskByID(subtask.getTaskID());
        putSubtask(subtask);
    }

    public void deleteAllSubtasks() {
        subtaskHashMap.clear();
    }

    //*******************************************************************************************************
    //******************************************Epic methods*************************************************
    //*******************************************************************************************************

    public Epic createEpic(String epicName, String epicDescription) {
        return new Epic(generateTaskID(), epicName, epicDescription);
    }

    public void putEpic(Epic epic) {
        epicHashMap.put(epic.getTaskID(), epic);
    }

    public HashMap<Integer, Epic> getListOfEpics() {
        return epicHashMap;
    }

    public Epic getEpicByID(int epicID) {
        return epicHashMap.get(epicID);
    }

    public int getEpicID(Epic epic) {
        return epic.getTaskID();
    }

    public void deleteEpicByID(int epicID) {
        epicHashMap.remove(epicID);
    }

    public ArrayList<Subtask> getListOfSubtasksInEpic(Epic epic) {
        ArrayList<Subtask> ListOfSubtasksInEpic = new ArrayList<>();
        for (Subtask subtask : subtaskHashMap.values())
            if (subtask.getUpperEpicName().equals(epic.taskName))
                ListOfSubtasksInEpic.add(subtask);

        return ListOfSubtasksInEpic;
    }

    public void updateEpic(Epic epic) {
        ArrayList<Subtask> listOfSubtasksInEpic = getListOfSubtasksInEpic(epic);
        boolean isAllStatusesAreNew = true;
        boolean isAllStatusesAreDone = true;

        if (listOfSubtasksInEpic.size() == 0)
            epic.taskStatus = "NEW";
        else {
            for (Subtask subtask : listOfSubtasksInEpic) {
                isAllStatusesAreNew &= subtask.getTaskStatus().equals("NEW");
                isAllStatusesAreDone &= subtask.getTaskStatus().equals("DONE");
            }

            if (isAllStatusesAreNew)
                epic.taskStatus = "NEW";
            else if (isAllStatusesAreDone)
                epic.taskStatus = "DONE";
            else
                epic.taskStatus = "IN_PROGRESS";
        }
    }

    public void deleteAllEpics() {
        subtaskHashMap.clear();
        epicHashMap.clear();
    }
}

