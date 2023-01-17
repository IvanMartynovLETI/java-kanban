package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int id = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    private int generateId() {
        return ++id;
    }

    //Tasks methods

    public Task createTask(String name, String description) {
        return new Task(generateId(), name, description);
    }

    public void putTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public ArrayList<Task> getListOfTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void updateTask(Task task) {

        switch (task.getStatus()) {
            case "NEW" -> task.setStatus("IN_PROGRESS");
            case "IN_PROGRESS" -> task.setStatus("DONE");
            default -> {
            } //reserved for future use
        }

        deleteTaskById(task.getId());
        putTask(task);
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    //Subtasks methods

    public Subtask createSubtask(String name, String description, Epic upperEpc) {
        return new Subtask(generateId(), name, description, upperEpc);
    }

    public void assignSubtaskToEpic(Subtask subtask, Epic epic) {
        epic.attachSubtaskToEpic(subtask.getId());
    }

    public void putSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }


    public ArrayList<Subtask> getListOfSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }


    public ArrayList<Subtask> getListOfSubtasksByUpperEpicId(int upperEpicId) {
        ArrayList<Subtask> subtasksAccordingToUpperEpcId = new ArrayList<>();

        for (Subtask subtask : subtasks.values())
            if (subtask.getUpperEpicId() == upperEpicId)
                subtasksAccordingToUpperEpcId.add(subtask);

        return subtasksAccordingToUpperEpcId;
    }


    public void updateSubtask(Subtask subtask, Epic epic) {
        int indexOfSubtskAttachedToEpc;
        switch (subtask.getStatus()) {
            case "NEW" -> subtask.setStatus("IN_PROGRESS");
            case "IN_PROGRESS" -> subtask.setStatus("DONE");
            default -> {
            } //reserved for future use
        }

        indexOfSubtskAttachedToEpc = getEpicById(subtask.getUpperEpicId()).getSubTaskIds().indexOf(subtask.getId());

        epic.deleteSubtaskById(subtask.getId(), this);
        putSubtask(subtask);
        getEpicById(subtask.getUpperEpicId()).getSubTaskIds().add(indexOfSubtskAttachedToEpc, subtask.getId());
    }

    public void deleteAllSubtasks() {
        for (Epic epic : epics.values())
            epic.getSubTaskIds().clear();

        subtasks.clear();

        for (Epic epic : epics.values())
            epic.unattachAllSubtasksFromEpic();
    }

    //Epic methods

    public Epic createEpic(String epicName, String epicDescription) {
        return new Epic(generateId(), epicName, epicDescription);
    }

    public void putEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public ArrayList<Epic> getListOfEpics() {
        return new ArrayList<>(epics.values());
    }

    public Epic getEpicById(int epicId) {
        return epics.get(epicId);
    }

    public void deleteEpicById(int epicId) {
        ArrayList<Subtask> listOfSubtsksByUpperEpcId = getListOfSubtasksByUpperEpicId(epicId);

        for (Subtask subtask : listOfSubtsksByUpperEpcId)
            subtasks.remove(subtask.getId());

        epics.remove(epicId);
    }

    public ArrayList<Subtask> getListOfSubtasksInEpic(Epic epic) {
        ArrayList<Subtask> ListOfSubtsksInEpc = new ArrayList<>();
        for (Subtask subtask : subtasks.values())
            if (subtask.getUpperEpicId() == epic.getId())
                ListOfSubtsksInEpc.add(subtask);

        return ListOfSubtsksInEpc;
    }

    public void updateEpic(Epic epic) {
        ArrayList<Subtask> listOfSubtsksInEpc = getListOfSubtasksInEpic(epic);
        boolean isAllStatusesAreNew = true;
        boolean isAllStatusesAreDone = true;

        if (listOfSubtsksInEpc.size() == 0)
            epic.setStatus("NEW");
        else {
            for (Subtask subtask : listOfSubtsksInEpc) {
                isAllStatusesAreNew &= subtask.getStatus().equals("NEW");
                isAllStatusesAreDone &= subtask.getStatus().equals("DONE");
            }

            if (isAllStatusesAreNew)
                epic.setStatus("NEW");
            else if (isAllStatusesAreDone)
                epic.setStatus("DONE");
            else
                epic.setStatus("IN_PROGRESS");
        }

        deleteEpicById(epic.getId());
        putEpic(epic);

        for (Subtask subtask : listOfSubtsksInEpc) {
            subtasks.put(subtask.getId(), subtask);
        }
    }

    public void deleteAllEpics() {
        subtasks.clear();
        epics.clear();
    }
}