import taskTracker.*;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = taskManager.createTask("task1", "1st task");
        taskManager.putTask(task1);
        Task task2 = taskManager.createTask("task2", "2d task");
        taskManager.putTask(task2);

        System.out.println("List of tasks is:");
        System.out.println(taskManager.getListOfTasks());

        taskManager.updateTask(task1);
        System.out.println("\nStatus of task1 updated to IN_PROGRESS");
        System.out.println("task1 after updating is:");
        System.out.println(taskManager.getTaskById(taskManager.getTaskId(task1)));
        System.out.println("List of tasks after task1 updating is:");
        System.out.println(taskManager.getListOfTasks());

        taskManager.updateTask(task1);
        System.out.println("\nStatus of task1 updated to DONE");
        System.out.println("task1 after updating is:");
        System.out.println(taskManager.getTaskById(taskManager.getTaskId(task1)));
        System.out.println("List of tasks after task1 updating is:");
        System.out.println(taskManager.getListOfTasks());

        taskManager.updateTask(task2);
        System.out.println("\nStatus of task2 updated to IN_PROGRESS");
        System.out.println("task2 after updating is:");
        System.out.println(taskManager.getTaskById(taskManager.getTaskId(task2)));
        System.out.println("List of tasks after task2 updating is:");
        System.out.println(taskManager.getListOfTasks());

        taskManager.updateTask(task2);
        System.out.println("\nStatus of task2 updated to DONE");
        System.out.println("task2 after updating is:");
        System.out.println(taskManager.getTaskById(taskManager.getTaskId(task1)));
        System.out.println("List of tasks after task2 updating is:");
        System.out.println(taskManager.getListOfTasks());

        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        System.out.println("\nepic1 created");
        taskManager.putEpic(epic1);
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        System.out.println("subtask1 created");
        taskManager.assignSubtaskToEpic(subtask1, epic1);
        System.out.println("subtask1 attached to epic1");
        taskManager.putSubtask(subtask1);
        Subtask subtask2 = taskManager.createSubtask("subtask2", "2d subtask", epic1);
        System.out.println("subtask2 created");
        taskManager.assignSubtaskToEpic(subtask2, epic1);
        System.out.println("subtask2 attached to epic1");
        taskManager.putSubtask(subtask2);
        Epic epic2 = taskManager.createEpic("epic2", "2d epic");
        System.out.println("epic2 created");
        taskManager.putEpic(epic2);
        Subtask subtask3 = taskManager.createSubtask("subtask3", "3rd subtask", epic2);
        System.out.println("subtask3 created");
        taskManager.assignSubtaskToEpic(subtask3, epic2);
        System.out.println("subtask3 attached to epic2");
        taskManager.putSubtask(subtask3);

        System.out.println("\nInitial list of subtasks for epic1 is");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic1));
        System.out.println("Initial list of subtasks for epic2 is");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic2));
        System.out.println("Initial list of subtasks is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("Initial list of epics is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.updateEpic(epic1);
        System.out.println("\nepic1 after updating is:");
        System.out.println(taskManager.getEpicById(taskManager.getEpicId(epic1)));
        System.out.println("List of epics after updating epic1 is:");
        System.out.println(taskManager.getListOfEpics());
        System.out.println("List of subtasks for epic1 after epic1 updated is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic1));
        System.out.println("List of subtasks after epic1 updated is:");
        System.out.println(taskManager.getListOfSubtasks());

        taskManager.updateEpic(epic2);
        System.out.println("\nepic2 after updating is:");
        System.out.println(taskManager.getEpicById(taskManager.getEpicId(epic2)));
        System.out.println("List of epics after updating epic2 is:");
        System.out.println(taskManager.getListOfEpics());
        System.out.println("List of subtasks for epic2 after epic2 updated is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic2));
        System.out.println("List of subtasks after epic2 updated is:");
        System.out.println(taskManager.getListOfSubtasks());

        taskManager.updateSubtask(subtask1);
        taskManager.updateEpic(epic1);
        System.out.println("\nsubtask1 after updating to IN_PROGRESS is");
        System.out.println(taskManager.getSubtaskById(taskManager.getSubtaskId(subtask1)));
        System.out.println("List of subtasks after subtask1 updating is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic1 after subtask1 and epic1 updating is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after subtask1 and epic1 updating is:");
        System.out.println(taskManager.getEpicById(taskManager.getEpicId(epic1)));
        System.out.println("List of epics after subtask1 and epic1 updating is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.updateSubtask(subtask2);
        taskManager.updateEpic(epic1);
        System.out.println("\nsubtask2 after updating to IN_PROGRESS is");
        System.out.println(taskManager.getSubtaskById(taskManager.getSubtaskId(subtask2)));
        System.out.println("List of subtasks after subtask2 updating is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic1 after subtask2 and epic1 updating is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after subtask2 and epic1 updating is:");
        System.out.println(taskManager.getEpicById(taskManager.getEpicId(epic1)));
        System.out.println("List of epics after subtask2 and epic1 updating is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.updateSubtask(subtask1);
        taskManager.updateEpic(epic1);
        System.out.println("\nsubtask1 after updating to DONE is");
        System.out.println(taskManager.getSubtaskById(taskManager.getSubtaskId(subtask1)));
        System.out.println("List of subtasks after subtask1 updating is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic1 after subtask1 and epic1 updating is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after subtask1 and epic1 updating is:");
        System.out.println(taskManager.getEpicById(taskManager.getEpicId(epic1)));
        System.out.println("List of epics after subtask1 and epic1 updating is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.updateSubtask(subtask2);
        taskManager.updateEpic(epic1);
        System.out.println("\nsubtask2 after updating to DONE is");
        System.out.println(taskManager.getSubtaskById(taskManager.getSubtaskId(subtask2)));
        System.out.println("List of subtasks after subtask2 updating is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic1 after subtask2 and epic1 updating is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after subtask2 and epic1 updating is:");
        System.out.println(taskManager.getEpicById(taskManager.getEpicId(epic1)));
        System.out.println("List of epics after subtask2 and epic1 updating is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.updateSubtask(subtask3);
        taskManager.updateEpic(epic2);
        System.out.println("\nsubtask3 after updating to IN_PROGRESS is");
        System.out.println(taskManager.getSubtaskById(taskManager.getSubtaskId(subtask3)));
        System.out.println("List of subtasks after subtask3 updating is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic2 after subtask3 and epic2 updating is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic2));
        System.out.println("epic2 after subtask3 and epic2 updating is:");
        System.out.println(taskManager.getEpicById(taskManager.getEpicId(epic2)));
        System.out.println("List of epics after subtask3 and epic2 updating is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.updateSubtask(subtask3);
        taskManager.updateEpic(epic2);
        System.out.println("\nsubtask3 after updating to DONE is");
        System.out.println(taskManager.getSubtaskById(taskManager.getSubtaskId(subtask3)));
        System.out.println("List of subtasks after subtask3 updating is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic2 after subtask3 and epic2 updating is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic2));
        System.out.println("epic2 after subtask3 and epic2 updating is:");
        System.out.println(taskManager.getEpicById(taskManager.getEpicId(epic2)));
        System.out.println("List of epics after subtask3 and epic2 updating is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.deleteEpicById(taskManager.getEpicId(epic2));
        System.out.println("\nepic2 deleted");
        System.out.println("list of epics after epic2 deleted is:");
        System.out.println(taskManager.getListOfEpics());
        System.out.println("List of subtasks after epic2 deletion is:");
        System.out.println(taskManager.getListOfSubtasks());

        taskManager.deleteSubtaskById(taskManager.getSubtaskId(subtask1));
        taskManager.updateEpic(epic1);
        System.out.println("\nList of subtasks after subtask1 deleting is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic1 after subtask1 deleting is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after deleting of subtask1 is:");
        System.out.println(taskManager.getEpicById(taskManager.getEpicId(epic1)));
        System.out.println("List of epics after deleting of subtask1 is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.deleteAllSubtasks();
        taskManager.updateEpic(epic1);
        System.out.println("\nList of subtasks after subtask2 deleting is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic1 after subtask2 deleting is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after deleting of subtask2 is:");
        System.out.println(taskManager.getEpicById(taskManager.getEpicId(epic1)));
        System.out.println("List of epics after deleting of subtask2 is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.deleteTaskById(taskManager.getTaskId(task1));
        System.out.println("\nList of tasks after task1 deletion is:");
        System.out.println(taskManager.getListOfTasks());
        taskManager.deleteAllTasks();
        System.out.println("List of tasks after its clearing is:");
        System.out.println(taskManager.getListOfTasks());

        taskManager.deleteAllEpics();
        System.out.println("List of epics after its clearing is:");
        System.out.println(taskManager.getListOfEpics());
        System.out.println("List of subtasks after all epics deletion is:");
    }
}

