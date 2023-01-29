package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    //Task-specific methods
    Task createTask(String name, String description);

    Task getTaskById(int id);

    //Subtask-specific methods
    Subtask createSubtask(String name, String description, Epic upperEpc);

    Subtask getSubtaskById(int subtaskId);

    //Epic-specific methods
    Epic createEpic(String epicName, String epicDescription);

    Epic getEpicById(int epicId);

    //Parameterized methods
    <T extends Task> ArrayList<T> getList(T t);

    <T extends Task> void put(T t);

    <T extends Task> void deleteTopLevelTaskById(int id, T t);

    <T extends Task> void update(T t);

    <T extends Task> void deleteAllTasksSameKind(T t);

    //logging methods
    List<Task> getHistory();
}
