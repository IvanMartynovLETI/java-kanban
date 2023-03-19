package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    protected int id = 0;
    protected final HashMap<Integer, Task> tasks;
    protected final HashMap<Integer, Subtask> subtasks;
    protected final HashMap<Integer, Epic> epics;
    protected final HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
    }

    private int generateId() {
        return ++id;
    }

    public HistoryManager getHistoryManager() {
        return this.historyManager;
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

    protected void updateTask(Task task) {

        switch (task.getStatus()) {
            case NEW -> task.setStatus(Status.IN_PROGRESS);
            case IN_PROGRESS -> task.setStatus(Status.DONE);
            default -> {
            } //reserved for future use
        }
        deleteTopLevelTaskById(task.getId(), task);
        historyManager.add(task);
        put(task);
    }

    //Subtask-specific methods
    @Override
    public Subtask createSubtask(String name, String description, Epic upperEpc) {
        Subtask subtask = new Subtask(generateId(), name, description, upperEpc);
        assignSubtaskToEpic(subtask, upperEpc);
        return subtask;
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        historyManager.add(subtasks.get(subtaskId));
        return subtasks.get(subtaskId);
    }

    private void assignSubtaskToEpic(Subtask subtask, Epic epic) {

        epic.attachSubtaskToEpic(subtask.getId());
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public ArrayList<Subtask> getListOfSubtasksByUpperEpicId(int upperEpicId) {
        ArrayList<Subtask> subtasksAccordingToUpperEpcId = new ArrayList<>();

        for (Subtask subtask : subtasks.values()) {
            if (subtask.getUpperEpicId() == upperEpicId) {
                subtasksAccordingToUpperEpcId.add(subtask);
            }
        }

        return subtasksAccordingToUpperEpcId;
    }

    protected void updateSubtask(Subtask subtask, Epic epic) {
        int indexOfSubtskAttachedToEpc;
        switch (subtask.getStatus()) {
            case NEW -> subtask.setStatus(Status.IN_PROGRESS);
            case IN_PROGRESS -> subtask.setStatus(Status.DONE);
            default -> {
            } //reserved for future use
        }

        indexOfSubtskAttachedToEpc = getEpicById(subtask.getUpperEpicId()).getSubTaskIds().indexOf(subtask.getId());
        deleteSubtaskById(subtask.getId());
        put(subtask);
        historyManager.add(subtask);
        getEpicById(subtask.getUpperEpicId()).getSubTaskIds().add(indexOfSubtskAttachedToEpc, subtask.getId());
        updateEpic(epic);
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
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getUpperEpicId() == epic.getId()) {
                listOfSubtsksInEpc.add(subtask);
            }
        }

        return listOfSubtsksInEpc;
    }

    protected void updateEpic(Epic epic) {
        ArrayList<Subtask> listOfSubtsksInEpc = getListOfSubtasksInEpic(epic);
        boolean isAllStatusesAreNew = true;
        boolean isAllStatusesAreDone = true;

        if (listOfSubtsksInEpc.size() == 0) {
            epic.setStatus(Status.NEW);
        } else {
            for (Subtask subtask : listOfSubtsksInEpc) {
                isAllStatusesAreNew &= subtask.getStatus().equals(Status.NEW);
                isAllStatusesAreDone &= subtask.getStatus().equals(Status.DONE);
            }

            if (isAllStatusesAreNew) {
                epic.setStatus(Status.NEW);
            } else if (isAllStatusesAreDone) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }

        }

        deleteTopLevelTaskById(epic.getId(), epic);
        historyManager.add(epic);
        put(epic);

        for (Subtask subtask : listOfSubtsksInEpc) {
            subtasks.put(subtask.getId(), subtask);
            historyManager.add(subtask);
        }
    }

    //parameterized methods

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Task> ArrayList<T> getList(T t) {
        if (t instanceof Epic) {
            return (ArrayList<T>) new ArrayList<>(epics.values());
        } else if (t instanceof Subtask) {
            return (ArrayList<T>) new ArrayList<>(subtasks.values());
        } else {
            return (ArrayList<T>) new ArrayList<>(tasks.values());
        }
    }

    @Override
    public <T extends Task> void put(T t) {
        if (t instanceof Subtask) {
            subtasks.put(t.getId(), (Subtask) t);
        } else if (t instanceof Epic) {
            epics.put(t.getId(), (Epic) t);
        } else {
            tasks.put(t.getId(), t);
        }
    }

    @Override
    public <T extends Task> void deleteTopLevelTaskById(int id, T t) {
        if (t instanceof Epic) {
            ArrayList<Subtask> listOfSubtsksByUpperEpcId = getListOfSubtasksByUpperEpicId(id);

            for (Subtask subtask : listOfSubtsksByUpperEpcId) {
                historyManager.remove(subtask.getId());
                subtasks.remove(subtask.getId());
            }

            historyManager.remove(id);
            epics.remove(id);
        } else {
            historyManager.remove(id);
            tasks.remove(id);
        }
    }

    public void deleteSubtaskById(int subtaskId) {
        if (subtasks.containsKey(subtaskId)) {
            Epic epic = getEpicById(getSubtaskById(subtaskId).getUpperEpicId());
            epic.unattachSubtaskFromEpic(subtaskId);
            historyManager.remove(subtaskId);
            subtasks.remove(subtaskId);
        }

    }

    @Override
    public <T extends Task> void update(T t) {
        if (t instanceof Epic) {
            updateEpic((Epic) t);
        } else if (t instanceof Subtask) {
            updateSubtask((Subtask) t, getEpicById(((Subtask) t).getUpperEpicId()));
        } else {
            updateTask(t);
        }
    }

    @Override
    public <T extends Task> void deleteAllTasksSameKind(T t) {
        if (t instanceof Epic) {

            for (Subtask subtask : subtasks.values()) {
                historyManager.remove(subtask.getId());
            }

            subtasks.clear();

            for (Epic epic : epics.values()) {
                historyManager.remove(epic.getId());
            }

            epics.clear();
        } else if (t instanceof Subtask) {
            for (Epic epic : epics.values()) {
                epic.getSubTaskIds().clear();
                epic.setStartTime("01.01.0001 00:00");
                epic.setDuration(0L);
                epic.setEndTime();

            }

            for (Subtask subtask : subtasks.values()) {
                historyManager.remove(subtask.getId());
            }

            subtasks.clear();

            for (Epic epic : epics.values()) {
                epic.unattachAllSubtasksFromEpic();
            }

        } else {

            for (Task task : tasks.values()) {
                historyManager.remove(task.getId());
            }

            tasks.clear();
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}