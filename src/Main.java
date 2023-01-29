import manager.InMemoryTaskManager;
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

        System.out.println("List of tasks is:");
        System.out.println(taskManager.getList(task1));

        taskManager.update(task1);
        System.out.println("\nStatus of task1 updated to IN_PROGRESS");
        System.out.println("task1 after updating is:");
        System.out.println(taskManager.getTaskById(task1.getId()));
        System.out.println("List of tasks after task1 updating is:");
        System.out.println(taskManager.getList(task1));

        taskManager.update(task1);
        System.out.println("\nStatus of task1 updated to DONE");
        System.out.println("task1 after updating is:");
        System.out.println(taskManager.getTaskById(task1.getId()));
        System.out.println("List of tasks after task1 updating is:");
        System.out.println(taskManager.getList(task1));

        taskManager.update(task2);
        System.out.println("\nStatus of task2 updated to IN_PROGRESS");
        System.out.println("task2 after updating is:");
        System.out.println(taskManager.getTaskById(task2.getId()));
        System.out.println("List of tasks after task2 updating is:");
        System.out.println(taskManager.getList(task2));

        taskManager.update(task2);
        System.out.println("\nStatus of task2 updated to DONE");
        System.out.println("task2 after updating is:");
        System.out.println(taskManager.getTaskById(task2.getId()));
        System.out.println("List of tasks after task2 updating is:");
        System.out.println(taskManager.getList(task2));

        System.out.println("\nHistory is:");
        System.out.println(taskManager.getHistory());
        System.out.println("Length of history is: " + taskManager.getHistory().size());

        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        System.out.println("\nepic1 created");
        taskManager.put(epic1);
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        System.out.println("subtask1 created");
        ((InMemoryTaskManager) taskManager).assignSubtaskToEpic(subtask1, epic1);
        System.out.println("subtask1 attached to epic1");
        taskManager.put(subtask1);
        Subtask subtask2 = taskManager.createSubtask("subtask2", "2d subtask", epic1);
        System.out.println("subtask2 created");
        ((InMemoryTaskManager) taskManager).assignSubtaskToEpic(subtask2, epic1);
        System.out.println("subtask2 attached to epic1");
        taskManager.put(subtask2);
        Epic epic2 = taskManager.createEpic("epic2", "2d epic");
        System.out.println("epic2 created");
        taskManager.put(epic2);
        Subtask subtask3 = taskManager.createSubtask("subtask3", "3rd subtask", epic2);
        System.out.println("subtask3 created");
        ((InMemoryTaskManager) taskManager).assignSubtaskToEpic(subtask3, epic2);
        System.out.println("subtask3 attached to epic2");
        taskManager.put(subtask3);

        System.out.println("\nHistory is:");
        System.out.println(taskManager.getHistory());
        System.out.println("Length of history is: " + taskManager.getHistory().size());

        System.out.println("\nInitial list of subtasks for epic1 is");
        System.out.println(((InMemoryTaskManager) taskManager).getListOfSubtasksInEpic(epic1));
        System.out.println("Initial list of subtasks for epic2 is");
        System.out.println(((InMemoryTaskManager) taskManager).getListOfSubtasksInEpic(epic2));
        System.out.println("Initial list of subtasks is:");
        System.out.println(taskManager.getList(subtask1));
        System.out.println("Initial list of epics is:");
        System.out.println(taskManager.getList(epic1));

        System.out.println("\nHistory is:");
        System.out.println(taskManager.getHistory());
        System.out.println("Length of history is: " + taskManager.getHistory().size());

        taskManager.update(epic1);
        System.out.println("\nepic1 after updating is:");
        System.out.println(taskManager.getEpicById(epic1.getId()));
        System.out.println("List of epics after updating epic1 is:");
        System.out.println(taskManager.getList(epic1));
        System.out.println("List of subtasks for epic1 after epic1 updated is:");
        System.out.println(((InMemoryTaskManager) taskManager).getListOfSubtasksInEpic(epic1));
        System.out.println("List of subtasks after epic1 updated is:");
        System.out.println(taskManager.getList(subtask1));

        System.out.println("\nHistory is:");
        System.out.println(taskManager.getHistory());
        System.out.println("Length of history is: " + taskManager.getHistory().size());

        taskManager.update(epic2);
        System.out.println("\nepic2 after updating is:");
        System.out.println(taskManager.getEpicById(epic2.getId()));
        System.out.println("List of epics after updating epic2 is:");
        System.out.println(taskManager.getList(epic1));
        System.out.println("List of subtasks for epic2 after epic2 updated is:");
        System.out.println(((InMemoryTaskManager) taskManager).getListOfSubtasksInEpic(epic2));
        System.out.println("List of subtasks after epic2 updated is:");
        System.out.println(taskManager.getList(subtask1));

        System.out.println("\nHistory is:");
        System.out.println(taskManager.getHistory());
        System.out.println("Length of history is: " + taskManager.getHistory().size());

        taskManager.update(subtask1);
        System.out.println("\nsubtask1 after updating to IN_PROGRESS is");
        System.out.println(taskManager.getSubtaskById(subtask1.getId()));
        System.out.println("List of subtasks after subtask1 updating is:");
        System.out.println(taskManager.getList(subtask1));
        System.out.println("List of subtasks in epic1 after subtask1 and epic1 updating is:");
        System.out.println(((InMemoryTaskManager) taskManager).getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after subtask1 and epic1 updating is:");
        System.out.println(taskManager.getEpicById(epic1.getId()));
        System.out.println("List of epics after subtask1 and epic1 updating is:");
        System.out.println(taskManager.getList(epic1));

        System.out.println("\nHistory is:");
        System.out.println(taskManager.getHistory());
        System.out.println("Length of history is: " + taskManager.getHistory().size());

        taskManager.update(subtask2);
        System.out.println("\nsubtask2 after updating to IN_PROGRESS is");
        System.out.println(taskManager.getSubtaskById(subtask2.getId()));
        System.out.println("List of subtasks after subtask2 updating is:");
        System.out.println(taskManager.getList(subtask1));
        System.out.println("List of subtasks in epic1 after subtask2 and epic1 updating is:");
        System.out.println(((InMemoryTaskManager) taskManager).getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after subtask2 and epic1 updating is:");
        System.out.println(taskManager.getEpicById(epic1.getId()));
        System.out.println("List of epics after subtask2 and epic1 updating is:");
        System.out.println(taskManager.getList(epic1));

        System.out.println("\nHistory is:");
        System.out.println(taskManager.getHistory());
        System.out.println("Length of history is: " + taskManager.getHistory().size());

        taskManager.update(subtask1);
        System.out.println("\nsubtask1 after updating to DONE is");
        System.out.println(taskManager.getSubtaskById(subtask1.getId()));
        System.out.println("List of subtasks after subtask1 updating is:");
        System.out.println(taskManager.getList(subtask1));
        System.out.println("List of subtasks in epic1 after subtask1 and epic1 updating is:");
        System.out.println(((InMemoryTaskManager) taskManager).getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after subtask1 and epic1 updating is:");
        System.out.println(taskManager.getEpicById(epic1.getId()));
        System.out.println("List of epics after subtask1 and epic1 updating is:");
        System.out.println(taskManager.getList(epic1));

        System.out.println("\nHistory is:");
        System.out.println(taskManager.getHistory());
        System.out.println("Length of history is: " + taskManager.getHistory().size());

        taskManager.update(subtask2);
        System.out.println("\nsubtask2 after updating to DONE is");
        System.out.println(taskManager.getSubtaskById(subtask2.getId()));
        System.out.println("List of subtasks after subtask2 updating is:");
        System.out.println(taskManager.getList(subtask1));
        System.out.println("List of subtasks in epic1 after subtask2 and epic1 updating is:");
        System.out.println(((InMemoryTaskManager) taskManager).getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after subtask2 and epic1 updating is:");
        System.out.println(taskManager.getEpicById(epic1.getId()));
        System.out.println("List of epics after subtask2 and epic1 updating is:");
        System.out.println(taskManager.getList(epic1));

        System.out.println("\nHistory is:");
        System.out.println(taskManager.getHistory());
        System.out.println("Length of history is: " + taskManager.getHistory().size());

        taskManager.update(subtask3);
        System.out.println("\nsubtask3 after updating to IN_PROGRESS is");
        System.out.println(taskManager.getSubtaskById(subtask3.getId()));
        System.out.println("List of subtasks after subtask3 updating is:");
        System.out.println(taskManager.getList(subtask1));
        System.out.println("List of subtasks in epic2 after subtask3 and epic2 updating is:");
        System.out.println(((InMemoryTaskManager) taskManager).getListOfSubtasksInEpic(epic2));
        System.out.println("epic2 after subtask3 and epic2 updating is:");
        System.out.println(taskManager.getEpicById(epic2.getId()));
        System.out.println("List of epics after subtask3 and epic2 updating is:");
        System.out.println(taskManager.getList(epic1));

        System.out.println("\nHistory is:");
        System.out.println(taskManager.getHistory());
        System.out.println("Length of history is: " + taskManager.getHistory().size());

        taskManager.update(subtask3);
        System.out.println("\nsubtask3 after updating to DONE is");
        System.out.println(taskManager.getSubtaskById(subtask3.getId()));
        System.out.println("List of subtasks after subtask3 updating is:");
        System.out.println(taskManager.getList(subtask1));
        System.out.println("List of subtasks in epic2 after subtask3 and epic2 updating is:");
        System.out.println(((InMemoryTaskManager) taskManager).getListOfSubtasksInEpic(epic2));
        System.out.println("epic2 after subtask3 and epic2 updating is:");
        System.out.println(taskManager.getEpicById(epic2.getId()));
        System.out.println("List of epics after subtask3 and epic2 updating is:");
        System.out.println(taskManager.getList(epic1));

        System.out.println("\nHistory is:");
        System.out.println(taskManager.getHistory());
        System.out.println("Length of history is: " + taskManager.getHistory().size());

        taskManager.deleteTopLevelTaskById(epic2.getId(), epic2);
        System.out.println("\nepic2 deleted");
        System.out.println("list of epics after epic2 deleted is:");
        System.out.println(taskManager.getList(epic1));
        System.out.println("List of subtasks after epic2 deletion is:");
        System.out.println(taskManager.getList(subtask1));

        System.out.println("\nHistory is:");
        System.out.println(taskManager.getHistory());
        System.out.println("Length of history is: " + taskManager.getHistory().size());

        epic1.deleteSubtaskById(subtask1.getId(), (InMemoryTaskManager) taskManager);
        taskManager.update(epic1);
        System.out.println("\nList of subtasks after subtask1 deleting is:");
        System.out.println(taskManager.getList(subtask1));
        System.out.println("List of subtasks in epic1 after subtask1 deleting is:");
        System.out.println(((InMemoryTaskManager) taskManager).getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after deleting of subtask1 is:");
        System.out.println(taskManager.getEpicById(epic1.getId()));
        System.out.println("List of epics after deleting of subtask1 is:");
        System.out.println(taskManager.getList(epic1));

        System.out.println("\nHistory is:");
        System.out.println(taskManager.getHistory());
        System.out.println("Length of history is: " + taskManager.getHistory().size());

        taskManager.deleteAllTasksSameKind(subtask1);
        taskManager.update(epic1);
        System.out.println("\nList of subtasks after subtask2 deleting is:");
        System.out.println(taskManager.getList(subtask1));
        System.out.println("List of subtasks in epic1 after subtask2 deleting is:");
        System.out.println(((InMemoryTaskManager) taskManager).getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after deleting of subtask2 is:");
        System.out.println(taskManager.getEpicById(epic1.getId()));
        System.out.println("List of epics after deleting of subtask2 is:");
        System.out.println(taskManager.getList(epic1));

        System.out.println("\nHistory is:");
        System.out.println(taskManager.getHistory());
        System.out.println("Length of history is: " + taskManager.getHistory().size());

        taskManager.deleteTopLevelTaskById(task1.getId(), task1);
        System.out.println("\nList of tasks after task1 deletion is:");
        System.out.println(taskManager.getList(task1));
        taskManager.deleteAllTasksSameKind(task1);
        System.out.println("List of tasks after its clearing is:");
        System.out.println(taskManager.getList(task1));

        System.out.println("\nHistory is:");
        System.out.println(taskManager.getHistory());
        System.out.println("Length of history is: " + taskManager.getHistory().size());

        taskManager.deleteAllTasksSameKind(epic1);
        System.out.println("List of epics after its clearing is:");
        System.out.println(taskManager.getList(epic1));
        System.out.println("List of subtasks after all epics deletion is:");
        System.out.println(taskManager.getList(subtask1));

        System.out.println("History is:");
        System.out.println(taskManager.getHistory());
        System.out.println("Length of history is: " + taskManager.getHistory().size());
    }
}



