package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    private int generateId() {
        return ++id;
    }

    //Task-specific methods

    @Override
    public Task createTask(String name, String description) {
        return new Task(generateId(), name, description);
    }

    @Override
    public Task getTaskById(int taskId) {
        historyManager.add(tasks.get(taskId));
        return tasks.get(taskId);
    }

    public void updateTask(Task task) {

        switch (task.getStatus()) {
            case NEW -> task.setStatus(StaTus.IN_PROGRESS);
            case IN_PROGRESS -> task.setStatus(StaTus.DONE);
            default -> {
            } //reserved for future use
        }
        deleteTopLevelTaskById(task.getId(), task);
        put(task);
    }

    //Subtask-specific methods
    @Override
    public Subtask createSubtask(String name, String description, Epic upperEpc) {
        return new Subtask(generateId(), name, description, upperEpc);
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        historyManager.add(subtasks.get(subtaskId));
        return subtasks.get(subtaskId);
    }

    public void assignSubtaskToEpic(Subtask subtask, Epic epic) {
        epic.attachSubtaskToEpic(subtask.getId());
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
            case NEW -> subtask.setStatus(StaTus.IN_PROGRESS);
            case IN_PROGRESS -> subtask.setStatus(StaTus.DONE);
            default -> {
            } //reserved for future use
        }

        indexOfSubtskAttachedToEpc = getEpicById(subtask.getUpperEpicId()).getSubTaskIds().indexOf(subtask.getId());
        epic.deleteSubtaskById(subtask.getId(), this);
        put(subtask);
        getEpicById(subtask.getUpperEpicId()).getSubTaskIds().add(indexOfSubtskAttachedToEpc, subtask.getId());
    }

    //Epic-specific methods
    @Override
    public Epic createEpic(String epicName, String epicDescription) {
        return new Epic(generateId(), epicName, epicDescription);
    }

    @Override
    public Epic getEpicById(int epicId) {
        historyManager.add(epics.get(epicId));
        return epics.get(epicId);
    }

    public ArrayList<Subtask> getListOfSubtasksInEpic(Epic epic) {
        ArrayList<Subtask> listOfSubtsksInEpc = new ArrayList<>();
        for (Subtask subtask : subtasks.values())
            if (subtask.getUpperEpicId() == epic.getId())
                listOfSubtsksInEpc.add(subtask);

        return listOfSubtsksInEpc;
    }

    public void updateEpic(Epic epic) {
        ArrayList<Subtask> listOfSubtsksInEpc = getListOfSubtasksInEpic(epic);
        boolean isAllStatusesAreNew = true;
        boolean isAllStatusesAreDone = true;

        if (listOfSubtsksInEpc.size() == 0)
            epic.setStatus(StaTus.NEW);
        else {
            for (Subtask subtask : listOfSubtsksInEpc) {
                isAllStatusesAreNew &= subtask.getStatus().equals(StaTus.NEW);
                isAllStatusesAreDone &= subtask.getStatus().equals(StaTus.DONE);
            }

            if (isAllStatusesAreNew)
                epic.setStatus(StaTus.NEW);
            else if (isAllStatusesAreDone)
                epic.setStatus(StaTus.DONE);
            else
                epic.setStatus(StaTus.IN_PROGRESS);
        }

        deleteTopLevelTaskById(epic.getId(), epic);
        put(epic);

        for (Subtask subtask : listOfSubtsksInEpc) {
            subtasks.put(subtask.getId(), subtask);
        }
    }

    //parameterized methods

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Task> ArrayList<T> getList(T t) {
        if (t instanceof Epic)
            return (ArrayList<T>) new ArrayList<>(epics.values());
        else if (t instanceof Subtask)
            return (ArrayList<T>) new ArrayList<>(subtasks.values());
        else return (ArrayList<T>) new ArrayList<>(tasks.values());
    }

    @Override
    public <T extends Task> void put(T t) {
        if (t instanceof Subtask)
            subtasks.put(t.getId(), (Subtask) t);
        else if (t instanceof Epic)
            epics.put(t.getId(), (Epic) t);
        else tasks.put(t.getId(), t);
    }

    @Override
    public <T extends Task> void deleteTopLevelTaskById(int id, T t) {
        if (t instanceof Epic) {
            ArrayList<Subtask> listOfSubtsksByUpperEpcId = getListOfSubtasksByUpperEpicId(id);

            for (Subtask subtask : listOfSubtsksByUpperEpcId)
                subtasks.remove(subtask.getId());

            epics.remove(id);
        } else tasks.remove(id);
    }

    @Override
    public <T extends Task> void update(T t) {
        if (t instanceof Epic)
            updateEpic((Epic) t);
        else if (t instanceof Subtask)
            updateSubtask((Subtask) t, getEpicById(((Subtask) t).getUpperEpicId()));
        else updateTask(t);
    }

    @Override
    public <T extends Task> void deleteAllTasksSameKind(T t) {
        if (t instanceof Epic) {
            subtasks.clear();
            epics.clear();
        } else if (t instanceof Subtask) {
            for (Epic epic : epics.values())
                epic.getSubTaskIds().clear();

            subtasks.clear();

            for (Epic epic : epics.values())
                epic.unattachAllSubtasksFromEpic();
        } else tasks.clear();
    }
}