package taskTracker;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int taskId = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    private int generateTaskId() {
        return ++taskId;
    }

    //Tasks methods

    public Task createTask(String taskName, String taskDescription) {
        return new Task(generateTaskId(), taskName, taskDescription);
    }

    public void putTask(Task task) {
        tasks.put(task.getTaskId(), task);
    }

    public ArrayList<Task> getListOfTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Task getTaskById(int taskId) {
        return tasks.get(taskId);
    }

    public int getTaskId(Task task) {
        return task.getTaskId();
    }

    public void deleteTaskById(int taskId) {
        tasks.remove(taskId);
    }

    public void updateTask(Task task) {

        switch (task.getTaskStatus()) {
            case "NEW" -> task.taskStatus = "IN_PROGRESS";
            case "IN_PROGRESS" -> task.taskStatus = "DONE";
            default -> {
            } //reserved for future use
        }

        deleteTaskById(task.getTaskId());
        putTask(task);
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    //Subtasks methods

    public Subtask createSubtask(String subtaskName, String subtaskDescription, Epic upperEpc) {
        return new Subtask(generateTaskId(), subtaskName, subtaskDescription, upperEpc);
    }

    public void assignSubtaskToEpic(Subtask subtask, Epic epic) {
        epic.attachSubtaskToEpic(subtask.getTaskId());
    }

    public void putSubtask(Subtask subtask) {
        subtasks.put(subtask.getTaskId(), subtask);
    }


    public ArrayList<Subtask> getListOfSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public Subtask getSubtaskById(int subtaskId) {
        return subtasks.get(subtaskId);
    }

    public ArrayList<Subtask> getListOfSubtasksByUpperEpicId(int upperEpicId) {
        ArrayList<Subtask> subtasksAccordingToUpperEpcId = new ArrayList<>();

        for (Subtask subtask : subtasks.values())
            if (subtask.upperEpcId == upperEpicId)
                subtasksAccordingToUpperEpcId.add(subtask);

        return subtasksAccordingToUpperEpcId;
    }

    public int getSubtaskId(Subtask subtask) {
        return subtask.getTaskId();
    }

    public void deleteSubtaskById(int subtaskId) {
        int upperEpcId = getSubtaskById(subtaskId).getUpperEpicId();
        getEpicById(upperEpcId).unattachSubtaskFromEpic(subtaskId);
        subtasks.remove(subtaskId);
    }

    public void updateSubtask(Subtask subtask) {
        int indexOfSubtskAttachedToEpc;
        switch (subtask.getTaskStatus()) {
            case "NEW" -> subtask.taskStatus = "IN_PROGRESS";
            case "IN_PROGRESS" -> subtask.taskStatus = "DONE";
            default -> {
            } //reserved for future use
        }

        indexOfSubtskAttachedToEpc = getEpicById(subtask.getUpperEpicId()).idsOfSubtsks.indexOf(subtask.getTaskId());

        deleteSubtaskById(subtask.getTaskId());
        putSubtask(subtask);
        getEpicById(subtask.getUpperEpicId()).idsOfSubtsks.add(indexOfSubtskAttachedToEpc, subtask.getTaskId());
    }

    public void deleteAllSubtasks() {
        for (Epic epic : epics.values())
            epic.idsOfSubtsks.clear();

        subtasks.clear();
    }

    //Epic methods

    public Epic createEpic(String epicName, String epicDescription) {
        return new Epic(generateTaskId(), epicName, epicDescription);
    }

    public void putEpic(Epic epic) {
        epics.put(epic.getTaskId(), epic);
    }

    public ArrayList<Epic> getListOfEpics() {
        return new ArrayList<>(epics.values());
    }

    public Epic getEpicById(int epicId) {
        return epics.get(epicId);
    }

    public int getEpicId(Epic epic) {
        return epic.getTaskId();
    }

    public void deleteEpicById(int epicId) {
        ArrayList<Subtask> listOfSubtsksByUpperEpcId = getListOfSubtasksByUpperEpicId(epicId);

        for (Subtask subtask : listOfSubtsksByUpperEpcId)
            subtasks.remove(subtask.getTaskId());

        epics.remove(epicId);
    }

    public ArrayList<Subtask> getListOfSubtasksInEpic(Epic epic) {
        ArrayList<Subtask> ListOfSubtsksInEpc = new ArrayList<>();
        for (Subtask subtask : subtasks.values())
            if (subtask.getUpperEpicId() == epic.getTaskId())
                ListOfSubtsksInEpc.add(subtask);

        return ListOfSubtsksInEpc;
    }

    public void updateEpic(Epic epic) {
        ArrayList<Subtask> listOfSubtsksInEpc = getListOfSubtasksInEpic(epic);
        boolean isAllStatusesAreNew = true;
        boolean isAllStatusesAreDone = true;

        if (listOfSubtsksInEpc.size() == 0)
            epic.taskStatus = "NEW";
        else {
            for (Subtask subtask : listOfSubtsksInEpc) {
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

        deleteEpicById(epic.getTaskId());
        putEpic(epic);

        for (Subtask subtask : listOfSubtsksInEpc) {
            subtasks.put(subtask.getTaskId(), subtask);
        }
    }

    public void deleteAllEpics() {
        subtasks.clear();
        epics.clear();
    }
}


