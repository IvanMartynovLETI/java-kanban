import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task1 = taskManager.createTask("task1", "1st task");
        taskManager.put(task1);
        Task task2 = taskManager.createTask("task2", "2d task");
        taskManager.put(task2);
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        taskManager.put(subtask1);
        Subtask subtask2 = taskManager.createSubtask("subtask2", "2d subtask", epic1);
        taskManager.put(subtask2);
        Subtask subtask3 = taskManager.createSubtask("subtask3", "3rd subtask", epic1);
        taskManager.put(subtask3);
        Epic epic2 = taskManager.createEpic("epic2", "2d epic");
        taskManager.put(epic2);

        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getSubtaskById(subtask1.getId());
        taskManager.getSubtaskById(subtask2.getId());
        taskManager.getSubtaskById(subtask3.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getEpicById(epic2.getId());
        System.out.println("Initial history is: ");
        System.out.println(taskManager.getHistory());

        taskManager.getTaskById(task1.getId());
        System.out.println("History after task1 calling is: ");
        System.out.println(taskManager.getHistory());

        taskManager.getTaskById(task2.getId());
        System.out.println("History after task2 calling is: ");
        System.out.println(taskManager.getHistory());

        taskManager.getSubtaskById(subtask1.getId());
        System.out.println("History after subtask1 calling is: ");
        System.out.println(taskManager.getHistory());

        taskManager.getSubtaskById(subtask2.getId());
        System.out.println("History after subtask2 calling is: ");
        System.out.println(taskManager.getHistory());

        taskManager.getSubtaskById(subtask3.getId());
        System.out.println("History after subtask3 calling is: ");
        System.out.println(taskManager.getHistory());

        taskManager.getEpicById(epic1.getId());
        System.out.println("History after epic1 calling is: ");
        System.out.println(taskManager.getHistory());

        taskManager.getEpicById(epic2.getId());
        System.out.println("History after epic2 calling is: ");
        System.out.println(taskManager.getHistory());

        taskManager.deleteTopLevelTaskById(task2.getId(), task2);
        System.out.println("History after task2 deleting is:");
        System.out.println(taskManager.getHistory());

        taskManager.deleteTopLevelTaskById(epic1.getId(), epic1);
        System.out.println("History after epic1 deleting is:");
        System.out.println(taskManager.getHistory());
    }
}



