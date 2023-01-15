import TaskTracker.*;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = taskManager.createTask("task1", "1st task");
        taskManager.putTask(task1);
        Task task2 = taskManager.createTask("task2", "2d task");
        taskManager.putTask(task2);
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.putEpic(epic1);
        Epic epic2 = taskManager.createEpic("epic2", "2d epic");
        taskManager.putEpic(epic2);
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        taskManager.putSubtask(subtask1);
        Subtask subtask2 = taskManager.createSubtask("subtask2", "2d subtask", epic1);
        taskManager.putSubtask(subtask2);
        Subtask subtask3 = taskManager.createSubtask("subtask3", "3rd subtask", epic2);
        taskManager.putSubtask(subtask3);

        System.out.println("List of tasks is:");
        System.out.println(taskManager.getListOfTasks());
        System.out.println("Total list of subtasks is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic1:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic1));
        System.out.println("List of subtasks in epic2:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic2));
        System.out.println("List of epics is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.updateTask(task1);
        System.out.println("\nStatus of task1 updated to IN_PROGRESS");
        System.out.println("task1 after updating is:");
        System.out.println(taskManager.getTaskByID(taskManager.getTaskID(task1)));
        System.out.println("List of tasks after task1 updating is:");
        System.out.println(taskManager.getListOfTasks());

        taskManager.updateTask(task1);
        System.out.println("\nStatus of task1 updated to DONE");
        System.out.println("task1 after updating is:");
        System.out.println(taskManager.getTaskByID(taskManager.getTaskID(task1)));
        System.out.println("List of tasks after task1 updating is:");
        System.out.println(taskManager.getListOfTasks());

        taskManager.updateTask(task2);
        System.out.println("\nStatus of task2 updated to IN_PROGRESS");
        System.out.println("task2 after updating is:");
        System.out.println(taskManager.getTaskByID(taskManager.getTaskID(task2)));
        System.out.println("List of tasks after task2 updating is:");
        System.out.println(taskManager.getListOfTasks());

        taskManager.updateTask(task2);
        System.out.println("\nStatus of task2 updated to DONE");
        System.out.println("task2 after updating is:");
        System.out.println(taskManager.getTaskByID(taskManager.getTaskID(task1)));
        System.out.println("List of tasks after task2 updating is:");
        System.out.println(taskManager.getListOfTasks());

        taskManager.updateEpic(epic1);
        System.out.println("\nepic1 after updating is:");
        System.out.println(taskManager.getEpicByID(taskManager.getEpicID(epic1)));
        System.out.println("List of epics after updating epic1 is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.updateEpic(epic2);
        System.out.println("\nepic2 after updating is:");
        System.out.println(taskManager.getEpicByID(taskManager.getEpicID(epic2)));
        System.out.println("List of epics after updating epic2 is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.updateSubtask(subtask1);
        taskManager.updateEpic(epic1);
        System.out.println("\nsubtask1 after updating to IN_PROGRESS is");
        System.out.println(taskManager.getSubtaskByID(taskManager.getSubtaskID(subtask1)));
        System.out.println("List of subtasks after subtask1 updating is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic1 after subtask1 and epic1 updating is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after subtask1 and epic1 updating is:");
        System.out.println(taskManager.getEpicByID(taskManager.getEpicID(epic1)));
        System.out.println("List of epics after subtask1 and epic1 updating is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.updateSubtask(subtask2);
        taskManager.updateEpic(epic1);
        System.out.println("\nsubtask2 after updating to IN_PROGRESS is");
        System.out.println(taskManager.getSubtaskByID(taskManager.getSubtaskID(subtask2)));
        System.out.println("List of subtasks after subtask2 updating is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic1 after subtask2 and epic1 updating is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after subtask2 and epic1 updating is:");
        System.out.println(taskManager.getEpicByID(taskManager.getEpicID(epic1)));
        System.out.println("List of epics after subtask2 and epic1 updating is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.updateSubtask(subtask1);
        taskManager.updateEpic(epic1);
        System.out.println("\nsubtask1 after updating to DONE is");
        System.out.println(taskManager.getSubtaskByID(taskManager.getSubtaskID(subtask1)));
        System.out.println("List of subtasks after subtask1 updating is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic1 after subtask1 and epic1 updating is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after subtask1 and epic1 updating is:");
        System.out.println(taskManager.getEpicByID(taskManager.getEpicID(epic1)));
        System.out.println("List of epics after subtask1 and epic1 updating is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.updateSubtask(subtask2);
        taskManager.updateEpic(epic1);
        System.out.println("\nsubtask2 after updating to DONE is");
        System.out.println(taskManager.getSubtaskByID(taskManager.getSubtaskID(subtask2)));
        System.out.println("List of subtasks after subtask2 updating is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic1 after subtask2 and epic1 updating is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after subtask2 and epic1 updating is:");
        System.out.println(taskManager.getEpicByID(taskManager.getEpicID(epic1)));
        System.out.println("List of epics after subtask2 and epic1 updating is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.updateSubtask(subtask3);
        taskManager.updateEpic(epic2);
        System.out.println("\nsubtask3 after updating to IN_PROGRESS is");
        System.out.println(taskManager.getSubtaskByID(taskManager.getSubtaskID(subtask3)));
        System.out.println("List of subtasks after subtask3 updating is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic2 after subtask3 and epic2 updating is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic2));
        System.out.println("epic2 after subtask3 and epic2 updating is:");
        System.out.println(taskManager.getEpicByID(taskManager.getEpicID(epic2)));
        System.out.println("List of epics after subtask3 and epic2 updating is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.updateSubtask(subtask3);
        taskManager.updateEpic(epic2);
        System.out.println("\nsubtask3 after updating to DONE is");
        System.out.println(taskManager.getSubtaskByID(taskManager.getSubtaskID(subtask3)));
        System.out.println("List of subtasks after subtask3 updating is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic2 after subtask3 and epic2 updating is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic2));
        System.out.println("epic2 after subtask3 and epic2 updating is:");
        System.out.println(taskManager.getEpicByID(taskManager.getEpicID(epic2)));
        System.out.println("List of epics after subtask3 and epic2 updating is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.deleteSubtaskByID(taskManager.getSubtaskID(subtask3));
        taskManager.updateEpic(epic2);
        System.out.println("\nList of subtasks after subtask3 deleting is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic2 after subtask3 deleting is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic2));
        System.out.println("epic2 after deleting of subtask3 is:");
        System.out.println(taskManager.getEpicByID(taskManager.getEpicID(epic2)));
        System.out.println("List of epics after deleting of subtask3 is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.deleteSubtaskByID(taskManager.getSubtaskID(subtask1));
        taskManager.updateEpic(epic1);
        System.out.println("\nList of subtasks after subtask1 deleting is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic1 after subtask1 deleting is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after deleting of subtask1 is:");
        System.out.println(taskManager.getEpicByID(taskManager.getEpicID(epic1)));
        System.out.println("List of epics after deleting of subtask1 is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.deleteAllSubtasks();
        taskManager.updateEpic(epic1);
        System.out.println("\nList of subtasks after subtask2 deleting is:");
        System.out.println(taskManager.getListOfSubtasks());
        System.out.println("List of subtasks in epic1 after subtask2 deleting is:");
        System.out.println(taskManager.getListOfSubtasksInEpic(epic1));
        System.out.println("epic1 after deleting of subtask2 is:");
        System.out.println(taskManager.getEpicByID(taskManager.getEpicID(epic1)));
        System.out.println("List of epics after deleting of subtask2 is:");
        System.out.println(taskManager.getListOfEpics());

        taskManager.deleteTaskByID(taskManager.getTaskID(task1));
        System.out.println("\nList of tasks after task1 deletion is:");
        System.out.println(taskManager.getListOfTasks());
        taskManager.deleteAllTasks();
        System.out.println("List of tasks after its clearing is:");
        System.out.println(taskManager.getListOfTasks());

        taskManager.deleteEpicByID(taskManager.getEpicID(epic2));
        System.out.println("\nList of epics after epic2 deletion is:");
        System.out.println(taskManager.getListOfEpics());
        taskManager.deleteAllEpics();
        System.out.println("List of epics after its clearing is:");
        System.out.println(taskManager.getListOfEpics());
    }
}