package manager;

import org.junit.Test;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;

    public TaskManagerTest(T taskManager) {
        this.taskManager = taskManager;
    }

    @Test
    public void shouldReturnStatusIsInProgressForTask1Updated() {
        Task task1 = taskManager.createTask("task1", "1st task");
        taskManager.put(task1);
        taskManager.update(task1);
        assertEquals(Status.IN_PROGRESS, task1.getStatus(), "Incorrect status of task1.");
    }

    //methods of TaskManager interface

    @Test
    public void shouldReturnStatusIsNewForEmptyEpic() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        assertEquals(Status.NEW, epic1.getStatus(), "Incorrect status of epic1.");
    }

    @Test
    public void shouldReturnStatusIsNewForEpicWithTwoNewSubtasks() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        taskManager.put(subtask1);
        Subtask subtask2 = taskManager.createSubtask("subtask2", "2d subtask", epic1);
        taskManager.put(subtask2);
        taskManager.update(epic1);
        assertEquals(Status.NEW, epic1.getStatus(), "Incorrect status of epic1.");
    }

    @Test
    public void shouldReturnStatusIsNewForEpicWithTwoDoneSubtasks() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        taskManager.put(subtask1);
        taskManager.update(subtask1);
        taskManager.update(subtask1);
        Subtask subtask2 = taskManager.createSubtask("subtask2", "2d subtask", epic1);
        taskManager.put(subtask2);
        taskManager.update(subtask2);
        taskManager.update(subtask2);
        taskManager.update(epic1);
        assertEquals(Status.DONE, epic1.getStatus(), "Incorrect status of epic1.");
    }

    @Test
    public void shouldReturnStatusIsInProgressForEpicWithNewAndDoneSubtasks() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        taskManager.put(subtask1);
        Subtask subtask2 = taskManager.createSubtask("subtask2", "2d subtask", epic1);
        taskManager.put(subtask2);
        taskManager.update(subtask2);
        taskManager.update(subtask2);
        assertEquals(Status.DONE, subtask2.getStatus(), "Incorrect status of subtask2.");
        taskManager.update(epic1);
        assertEquals(Status.IN_PROGRESS, epic1.getStatus(), "Incorrect status of epic1.");
    }

    @Test
    public void shouldReturnStatusIsNewForEpicWithTwoInProgressSubtasks() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        taskManager.put(subtask1);
        taskManager.update(subtask1);
        Subtask subtask2 = taskManager.createSubtask("subtask2", "2d subtask", epic1);
        taskManager.put(subtask2);
        taskManager.update(subtask2);
        taskManager.update(epic1);
        assertEquals(Status.IN_PROGRESS, epic1.getStatus(), "Incorrect status of epic1.");
    }

    @Test
    public void shouldBeUpperEpicForSubtask() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        taskManager.put(subtask1);
        assertEquals(1, subtask1.getUpperEpicId(), "subtask1 hasn't id of upper epic.");
        assertEquals(epic1, taskManager.getEpicById(epic1.getId()), "upper epic doesn't exist for subtask1.");
    }

    @Test
    public void shouldThrowNoExceptionWhileCreationTaskWithEmptyParameters() {
        Task task1 = taskManager.createTask("", "");
        assertEquals(1, task1.getId(), "An error occurred while task1 creation.");
        assertDoesNotThrow(() -> {
            taskManager.createTask("", "");
        });
    }

    @Test
    public void shouldReturnTaskBuId() {
        Task task1 = taskManager.createTask("", "");
        taskManager.put(task1);
        assertEquals(task1, taskManager.getTaskById(task1.getId()), "Task was not returned");
    }

    @Test
    public void shouldThrowNPEWhileReturningTaskByIdOf0() {
        final NullPointerException exception = assertThrows(NullPointerException.class, () ->
                taskManager.getTaskById(0));

        assertEquals("Cannot invoke \"task.Task.getId()\" because \"task\" is null", exception.getMessage());
    }

    @Test
    public void shouldThrowNPEWhileReturningTaskByIdOfMinus1() {
        final NullPointerException exception = assertThrows(NullPointerException.class, () ->
                taskManager.getTaskById(-1));

        assertEquals("Cannot invoke \"task.Task.getId()\" because \"task\" is null", exception.getMessage());
    }

    @Test
    public void shouldThrowNoExceptionWhileCreationSubtaskWithEmptyParameters() {
        Epic epic = taskManager.createEpic("", "");
        Subtask subtask = taskManager.createSubtask("", "", epic);
        assertEquals(2, subtask.getId(), "An error occurred while subtask creation.");
        assertDoesNotThrow(() -> {
            taskManager.createSubtask("", "", epic);
        });
    }

    @Test
    public void shouldThrowNPEWhileReturningSubtaskByIdOf0() {
        final NullPointerException exception = assertThrows(NullPointerException.class, () ->
                taskManager.getSubtaskById(0));

        assertEquals("Cannot invoke \"task.Task.getId()\" because \"task\" is null", exception.getMessage());
    }

    @Test
    public void shouldThrowNPEWhileReturningSubtaskByIdOfMinus1() {
        final NullPointerException exception = assertThrows(NullPointerException.class, () ->
                taskManager.getSubtaskById(-1));

        assertEquals("Cannot invoke \"task.Task.getId()\" because \"task\" is null", exception.getMessage());
    }

    @Test
    public void shouldThrowNoExceptionWhileCreationEpicWithEmptyParameters() {
        Epic epic = taskManager.createEpic("", "");
        assertEquals(1, epic.getId(), "An error occurred while epic creation.");
        assertDoesNotThrow(() -> {
            taskManager.createEpic("", "");
        });
    }

    @Test
    public void shouldThrowNPEWhileReturningEpicByIdOf0() {
        final NullPointerException exception = assertThrows(NullPointerException.class, () ->
                taskManager.getEpicById(0));
        assertEquals("Cannot invoke \"task.Task.getId()\" because \"task\" is null", exception.getMessage());
    }

    @Test
    public void shouldThrowNPEWhileReturningEpicByIdOfMinus1() {
        final NullPointerException exception = assertThrows(NullPointerException.class, () ->
                taskManager.getEpicById(-1));
        assertEquals("Cannot invoke \"task.Task.getId()\" because \"task\" is null", exception.getMessage());
    }

    @Test
    public void shouldReturnNonEmptyListOfTasks() {
        Task task1 = taskManager.createTask("task1", "1st task");
        taskManager.put(task1);
        Task task2 = taskManager.createTask("task2", "2d task");
        taskManager.put(task2);
        List<Task> list = taskManager.getList(task1);
        assertTrue(list.contains(task1) & list.contains(task2) & (list.size() == 2),
                "getList() method doesn't return list of tasks");
    }

    @Test
    public void shouldReturnEmptyListOfTasks() {
        Task task1 = taskManager.createTask("task1", "1st task");
        List<Task> list = taskManager.getList(task1);
        assertEquals(0, list.size(), "getList() method doesn't return empty list of tasks");
    }

    @Test
    public void shouldReturnNonEmptyListOfSubtasks() {
        Epic epic = taskManager.createEpic("epic1", "1st epic");
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic);
        taskManager.put(subtask1);
        Subtask subtask2 = taskManager.createSubtask("subtask2", "2d subtask", epic);
        taskManager.put(subtask2);
        List<Subtask> list = taskManager.getList(subtask1);
        assertTrue(list.contains(subtask1) & list.contains(subtask2) & (list.size() == 2),
                "getList() method doesn't return list of subtasks");
    }

    @Test
    public void shouldReturnEmptyListOfSubtasks() {
        Epic epic = taskManager.createEpic("epic1", "1st epic");
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic);
        List<Subtask> list = taskManager.getList(subtask1);
        assertEquals(0, list.size(), "getList() method doesn't return empty list of subtasks");
    }

    @Test
    public void shouldReturnNonEmptyListOfEpics() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        Epic epic2 = taskManager.createEpic("epic2", "2d epic");
        taskManager.put(epic2);
        List<Epic> list = taskManager.getList(epic1);
        assertTrue(list.contains(epic1) & list.contains(epic2) & (list.size() == 2),
                "getList() method doesn't return list of epics");
    }

    @Test
    public void shouldReturnEmptyListOfEpics() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        List<Epic> list = taskManager.getList(epic1);
        assertEquals(0, list.size(), "getList() method doesn't return empty list of epics");
    }

    @Test
    public void shouldPutTaskToMapOfTasks() {
        Task task = taskManager.createTask("", "");
        taskManager.put(task);
        assertEquals(task, taskManager.getTaskById(task.getId()), "task doesn't exist in map of tasks");
    }

    @Test
    public void shouldPutSubtaskToMapOfSubtasks() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        taskManager.put(subtask1);
        assertEquals(subtask1, taskManager.getSubtaskById(subtask1.getId()),
                "subtask doesn't exist in map of subtasks");
    }

    @Test
    public void shouldPutEpicToMapOfEpics() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        assertEquals(epic1, taskManager.getEpicById(epic1.getId()),
                "subtask doesn't exist in map of epics");
    }

    @Test
    public void shouldDeleteTaskById() {
        Task task1 = taskManager.createTask("task1", "1st task");
        taskManager.put(task1);
        taskManager.deleteTopLevelTaskById(task1.getId(), task1);
        assertTrue(taskManager.getList(task1).isEmpty(), "task1 was not deleted");
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletionOfTaskByIdOf0() {
        Task task1 = taskManager.createTask("task1", "1st task");
        taskManager.deleteTopLevelTaskById(0, task1);
        assertTrue(taskManager.getList(task1).isEmpty(),
                "An error occurred while deletion of task with id=0");
        assertDoesNotThrow(() -> taskManager.deleteTopLevelTaskById(0, task1));
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletionOfTaskByIdOfMinus1() {
        Task task1 = taskManager.createTask("task1", "1st task");
        taskManager.deleteTopLevelTaskById(-1, task1);
        assertTrue(taskManager.getList(task1).isEmpty(),
                "An error occurred while deletion of task with id=-1");
        assertDoesNotThrow(() -> taskManager.deleteTopLevelTaskById(-1, task1));
    }

    @Test
    public void shouldDeleteEpicById() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        taskManager.deleteTopLevelTaskById(epic1.getId(), epic1);
        assertTrue(taskManager.getList(epic1).isEmpty(), "task1 was not deleted");
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletionOfEpicByIdOf0() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.deleteTopLevelTaskById(0, epic1);
        assertTrue(taskManager.getList(epic1).isEmpty(),
                "An error occurred while deletion of epic with id=0");
        assertDoesNotThrow(() -> taskManager.deleteTopLevelTaskById(0, epic1));
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletionOfEpicByIdOfMinus1() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.deleteTopLevelTaskById(-1, epic1);
        assertTrue(taskManager.getList(epic1).isEmpty(),
                "An error occurred while deletion of epic with id=-1");
        assertDoesNotThrow(() -> taskManager.deleteTopLevelTaskById(-1, epic1));
    }

    @Test
    public void shouldDeleteAllTasks() {
        Task task1 = taskManager.createTask("task1", "1st task");
        taskManager.put(task1);
        Task task2 = taskManager.createTask("task2", "2d task");
        taskManager.put(task2);
        assertEquals(2, taskManager.getList(task1).size(), "An error occurred during adding tasks.");
        taskManager.deleteAllTasksSameKind(task1);
        assertEquals(0, taskManager.getList(task1).size(), "An error occurred during tasks deletion.");
    }

    @Test
    public void shouldThrowNoExceptionWhileClearingOfEmptyMapOfTasks() {
        Task task1 = taskManager.createTask("task1", "1st task");
        taskManager.deleteAllTasksSameKind(task1);
        assertEquals(0, taskManager.getList(task1).size(),
                "An error occurred during clearing empty map of tasks.");
        assertDoesNotThrow(() -> taskManager.deleteAllTasksSameKind(task1));
    }

    @Test
    public void shouldDeleteAllSubTasks() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        taskManager.put(subtask1);
        Subtask subtask2 = taskManager.createSubtask("subtask2", "2d subtask", epic1);
        taskManager.put(subtask2);
        assertEquals(2, taskManager.getList(subtask1).size(),
                "An error occurred during adding subtasks.");
        taskManager.deleteAllTasksSameKind(subtask1);
        assertEquals(0, taskManager.getList(subtask1).size(),
                "An error occurred during subtasks deletion.");
    }

    @Test
    public void shouldThrowNoExceptionWhileClearingOfEmptyMapOfSubtasks() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        taskManager.deleteAllTasksSameKind(subtask1);
        assertEquals(0, taskManager.getList(subtask1).size(),
                "An error occurred during clearing empty map of subtasks.");
        assertDoesNotThrow(() -> taskManager.deleteAllTasksSameKind(subtask1));
    }

    @Test
    public void shouldDeleteAllEpics() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        Epic epic2 = taskManager.createEpic("epic2", "2d epic");
        taskManager.put(epic2);
        assertEquals(2, taskManager.getList(epic1).size(),
                "An error occurred during adding epics.");
        taskManager.deleteAllTasksSameKind(epic1);
        assertEquals(0, taskManager.getList(epic1).size(),
                "An error occurred during epics deletion.");
    }

    @Test
    public void shouldThrowNoExceptionWhileClearingOfEmptyMapOfEpics() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.deleteAllTasksSameKind(epic1);
        assertEquals(0, taskManager.getList(epic1).size(),
                "An error occurred during clearing empty map of epics.");
        assertDoesNotThrow(() -> taskManager.deleteAllTasksSameKind(epic1));
    }

    @Test
    public void shouldReturnNonEmptyListOfTasksFromHistory() {
        Task task1 = taskManager.createTask("task1", "1st task");
        taskManager.put(task1);
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        taskManager.put(subtask1);
        taskManager.getTaskById(task1.getId());
        assertTrue(taskManager.getHistory().contains(task1), "History doesn't contain task1");
        taskManager.getEpicById(epic1.getId());
        assertTrue(taskManager.getHistory().contains(epic1), "History doesn't contain epic1");
        taskManager.getSubtaskById(subtask1.getId());
        assertTrue(taskManager.getHistory().contains(subtask1), "History doesn't contain subtask1");
    }

    @Test
    public void shouldReturnEmptyListOfTasksFromHistory() {
        assertEquals(0, taskManager.getHistory().size(), "History is not an empty");
    }

    //InMemoryTaskManager-specific
    @Test
    public void shouldReturnInstanceOfInMemoryHistoryManager() {
        assertNotNull(((InMemoryTaskManager) taskManager).getHistoryManager(),
                "An object returned by getHistoryManager is null");
    }

    @Test
    public void shouldReturnListOfSubtasksInEpicWithOneSubtask() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        taskManager.put(subtask1);
        assertTrue(((InMemoryTaskManager) taskManager).getListOfSubtasksInEpic(epic1).contains(subtask1),
                "List of subtasks of epic1 doesn't contain subtask1");
    }

    @Test
    public void shouldReturnListOfSubtasksInEpicWithTwoSubtasks() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        taskManager.put(subtask1);
        Subtask subtask2 = taskManager.createSubtask("subtask2", "2d subtask", epic1);
        taskManager.put(subtask2);
        assertTrue(((InMemoryTaskManager) taskManager).getListOfSubtasksInEpic(epic1).contains(subtask1)
                        & ((InMemoryTaskManager) taskManager).getListOfSubtasksInEpic(epic1).contains(subtask2),
                "List of subtasks of epic1 doesn't contain subtask1 and subtask2");
    }

    @Test
    public void shouldReturnListOfSubtasksInEpicWithoutSubtasks() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        assertTrue(((InMemoryTaskManager) taskManager).getListOfSubtasksInEpic(epic1).isEmpty(),
                "List of subtasks of epic1 is not an empty");
    }

    @Test
    public void shouldDeleteOneOfTwoSubtasks() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        taskManager.put(subtask1);
        Subtask subtask2 = taskManager.createSubtask("subtask2", "2d subtask", epic1);
        taskManager.put(subtask2);
        ((InMemoryTaskManager) taskManager).deleteSubtaskById(subtask2.getId());
        assertTrue(!taskManager.getList(subtask1).contains(subtask2)
                        & !epic1.getSubTaskIds().contains(subtask2.getId()),
                "Incorrect operation of deleteSubtaskById() method while deleting one of two subtasks");
    }

    @Test
    public void shouldDeleteSingleSubtask() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        taskManager.put(subtask1);
        ((InMemoryTaskManager) taskManager).deleteSubtaskById(subtask1.getId());
        assertTrue(!taskManager.getList(subtask1).contains(subtask1)
                        & !epic1.getSubTaskIds().contains(subtask1.getId()),
                "Incorrect operation of deleteSubtaskById() method while deleting single subtask");
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletingOfSubtaskWithIdOf0() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        taskManager.put(subtask1);
        ((InMemoryTaskManager) taskManager).deleteSubtaskById(0);
        assertTrue(taskManager.getList(subtask1).contains(subtask1)
                        & (taskManager.getList(subtask1).size() == 1) & epic1.getSubTaskIds().contains(subtask1.getId())
                        & (epic1.getSubTaskIds().size() == 1),
                "An error occurred while deleting of subtask with id=0");
        assertDoesNotThrow(() -> ((InMemoryTaskManager) taskManager).deleteSubtaskById(0));
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletingOfSubtaskWithIdOfMinus1() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        taskManager.put(subtask1);
        ((InMemoryTaskManager) taskManager).deleteSubtaskById(-1);
        assertTrue(taskManager.getList(subtask1).contains(subtask1)
                        & (taskManager.getList(subtask1).size() == 1) & epic1.getSubTaskIds().contains(subtask1.getId())
                        & (epic1.getSubTaskIds().size() == 1),
                "An error occurred while deleting of subtask with id=-1");
        assertDoesNotThrow(() -> ((InMemoryTaskManager) taskManager).deleteSubtaskById(-1));
    }
}