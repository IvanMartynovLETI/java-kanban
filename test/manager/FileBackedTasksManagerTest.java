package manager;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;


public abstract class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    public FileBackedTasksManagerTest(FileBackedTasksManager fileBackedTasksManager) {
        super(fileBackedTasksManager);
    }


    //Tests for methods from TaskManager interface
    @Test
    public void shouldReturnStatusIsInProgressForTask1Updated() {
        super.shouldReturnStatusIsInProgressForTask1Updated();
    }

    @Test
    public void shouldReturnStatusIsNewForEmptyEpic() {
        super.shouldReturnStatusIsNewForEmptyEpic();
    }

    @Test
    public void shouldReturnStatusIsNewForEpicWithTwoNewSubtasks() {
        super.shouldReturnStatusIsNewForEpicWithTwoNewSubtasks();
    }

    @Test
    public void shouldReturnStatusIsNewForEpicWithTwoDoneSubtasks() {
        super.shouldReturnStatusIsNewForEpicWithTwoDoneSubtasks();
    }

    @Test
    public void shouldReturnStatusIsInProgressForEpicWithNewAndDoneSubtasks() {
        super.shouldReturnStatusIsInProgressForEpicWithNewAndDoneSubtasks();
    }

    @Test
    public void shouldReturnStatusIsNewForEpicWithTwoInProgressSubtasks() {
        super.shouldReturnStatusIsNewForEpicWithTwoInProgressSubtasks();
    }

    @Test
    public void shouldBeUpperEpicForSubtask() {
        super.shouldBeUpperEpicForSubtask();
    }

    @Test
    public void shouldThrowNoExceptionWhileCreationTaskWithEmptyParameters() {
        super.shouldThrowNoExceptionWhileCreationTaskWithEmptyParameters();
    }

    @Test
    public void shouldReturnTaskBuId() {
        super.shouldReturnTaskBuId();
    }

    @Test
    public void shouldThrowNPEWhileReturningTaskByIdOf0() {
        super.shouldThrowNPEWhileReturningTaskByIdOf0();
    }

    @Test
    public void shouldThrowNPEWhileReturningTaskByIdOfMinus1() {
        super.shouldThrowNPEWhileReturningTaskByIdOfMinus1();
    }

    @Test
    public void shouldThrowNoExceptionWhileCreationSubtaskWithEmptyParameters() {
        super.shouldThrowNoExceptionWhileCreationSubtaskWithEmptyParameters();
    }

    @Test
    public void shouldThrowNPEWhileReturningSubtaskByIdOf0() {
        super.shouldThrowNPEWhileReturningSubtaskByIdOf0();
    }

    @Test
    public void shouldThrowNPEWhileReturningSubtaskByIdOfMinus1() {
        super.shouldThrowNPEWhileReturningSubtaskByIdOfMinus1();
    }

    @Test
    public void shouldThrowNoExceptionWhileCreationEpicWithEmptyParameters() {
        super.shouldThrowNoExceptionWhileCreationEpicWithEmptyParameters();
    }

    @Test
    public void shouldThrowNPEWhileReturningEpicByIdOf0() {
        super.shouldThrowNPEWhileReturningEpicByIdOf0();
    }

    @Test
    public void shouldThrowNPEWhileReturningEpicByIdOfMinus1() {
        super.shouldThrowNPEWhileReturningEpicByIdOfMinus1();
    }

    @Test
    public void shouldReturnNonEmptyListOfTasks() {
        super.shouldReturnNonEmptyListOfTasks();
    }

    @Test
    public void shouldReturnEmptyListOfTasks() {
        super.shouldReturnEmptyListOfTasks();
    }

    @Test
    public void shouldReturnNonEmptyListOfSubtasks() {
        super.shouldReturnNonEmptyListOfSubtasks();
    }

    @Test
    public void shouldReturnEmptyListOfSubtasks() {
        super.shouldReturnEmptyListOfSubtasks();
    }

    @Test
    public void shouldReturnNonEmptyListOfEpics() {
        super.shouldReturnNonEmptyListOfEpics();
    }

    @Test
    public void shouldReturnEmptyListOfEpics() {
        super.shouldReturnEmptyListOfEpics();
    }

    @Test
    public void shouldPutTaskToMapOfTasks() {
        super.shouldPutTaskToMapOfTasks();
    }

    @Test
    public void shouldPutSubtaskToMapOfSubtasks() {
        super.shouldPutSubtaskToMapOfSubtasks();
    }

    @Test
    public void shouldPutEpicToMapOfEpics() {
        super.shouldPutEpicToMapOfEpics();
    }

    @Test
    public void shouldDeleteTaskById() {
        super.shouldDeleteTaskById();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletionOfTaskByIdOf0() {
        super.shouldThrowNoExceptionWhileDeletionOfTaskByIdOf0();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletionOfTaskByIdOfMinus1() {
        super.shouldThrowNoExceptionWhileDeletionOfTaskByIdOfMinus1();
    }

    @Test
    public void shouldDeleteEpicById() {
        super.shouldDeleteEpicById();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletionOfEpicByIdOf0() {
        super.shouldThrowNoExceptionWhileDeletionOfEpicByIdOf0();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletionOfEpicByIdOfMinus1() {
        super.shouldThrowNoExceptionWhileDeletionOfEpicByIdOfMinus1();
    }

    @Test
    public void shouldDeleteAllTasks() {
        super.shouldDeleteAllTasks();
    }

    @Test
    public void shouldThrowNoExceptionWhileClearingOfEmptyMapOfTasks() {
        super.shouldThrowNoExceptionWhileClearingOfEmptyMapOfTasks();
    }

    @Test
    public void shouldDeleteAllSubTasks() {
        super.shouldDeleteAllSubTasks();
    }

    @Test
    public void shouldThrowNoExceptionWhileClearingOfEmptyMapOfSubtasks() {
        super.shouldThrowNoExceptionWhileClearingOfEmptyMapOfSubtasks();
    }

    @Test
    public void shouldDeleteAllEpics() {
        super.shouldDeleteAllEpics();
    }

    @Test
    public void shouldThrowNoExceptionWhileClearingOfEmptyMapOfEpics() {
        super.shouldThrowNoExceptionWhileClearingOfEmptyMapOfEpics();
    }

    @Test
    public void shouldReturnNonEmptyListOfTasksFromHistory() {
        super.shouldReturnNonEmptyListOfTasksFromHistory();
    }

    public void shouldReturnEmptyListOfTasksFromHistory() {
        super.shouldReturnEmptyListOfTasksFromHistory();
    }

    //methods inherited from InMemoryTaskManager

    @Test
    public void shouldReturnInstanceOfInMemoryHistoryManager() {
        super.shouldReturnInstanceOfInMemoryHistoryManager();
    }

    @Test
    public void shouldReturnListOfSubtasksInEpicWithOneSubtask() {
        super.shouldReturnListOfSubtasksInEpicWithOneSubtask();
    }

    @Test
    public void shouldReturnListOfSubtasksInEpicWithTwoSubtasks() {
        super.shouldReturnListOfSubtasksInEpicWithTwoSubtasks();
    }

    @Test
    public void shouldReturnListOfSubtasksInEpicWithoutSubtasks() {
        super.shouldReturnListOfSubtasksInEpicWithoutSubtasks();
    }

    @Test
    public void shouldDeleteOneOfTwoSubtasks() {
        super.shouldDeleteOneOfTwoSubtasks();
    }

    @Test
    public void shouldDeleteSingleSubtask() {
        super.shouldDeleteSingleSubtask();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletingOfSubtaskWithIdOf0() {
        super.shouldThrowNoExceptionWhileDeletingOfSubtaskWithIdOf0();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletingOfSubtaskWithIdOfMinus1() {
        super.shouldThrowNoExceptionWhileDeletingOfSubtaskWithIdOfMinus1();
    }

    @Test
    public void shouldReturnListOfPrioritizedTasks() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = nowDateTime.minusMinutes(nowDateTime.getMinute()).
                minusSeconds(nowDateTime.getSecond()).
                minusNanos(nowDateTime.getNano()).plusHours(24);

        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        subtask1.setStartDateTime(startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));
        subtask1.setDuration(15L);

        taskManager.put(epic1);
        taskManager.put(subtask1);
        taskManager.updateEpic(epic1);

        assertTrue(taskManager.getPrioritizedTasks().size() == 1
                        & taskManager.getPrioritizedTasks().get(0).equals(subtask1),
                "subtask1 is not present in list of prioritized tasks");

        Task task1 = taskManager.createTask("task1", "1st task");

        taskManager.put(task1);

        LocalDateTime taskStartDateTime = startDateTime.plusMinutes(30);
        task1.setStartDateTime(taskStartDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));

        assertTrue(taskManager.getPrioritizedTasks().size() == 2
                        & taskManager.getPrioritizedTasks().get(1).equals(task1),
                "task1 is not present in list of prioritized tasks");
    }

    @Test
    public void shouldReturnEmptyListOfPrioritizedTasks() {
        assertTrue(taskManager.getPrioritizedTasks().isEmpty(), "List of prioritized tasks is not an empty");
    }

    @Test
    public void shouldThrowNoExceptionWhileSettingUpstartDateTimeForTask() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = nowDateTime.minusMinutes(nowDateTime.getMinute()).
                minusSeconds(nowDateTime.getSecond()).
                minusNanos(nowDateTime.getNano()).plusHours(24);

        Task task = taskManager.createTask("task1", "1st task");

        task.setStartDateTime(startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));

        assertEquals(startDateTime, task.getStartDateTime(),
                "An error occurred while setting start time of task");
        assertDoesNotThrow(() -> task.setStartDateTime(startDateTime.format(FileBackedTasksManager.
                DATE_TIME_FORMATTER)));
    }

    @Test
    public void shouldReturnDateTimeParseExceptionWhileSettingUpIncorrectStartDateTimeForTask() {
        Task task = taskManager.createTask("task1", "1st task");
        final DateTimeParseException exception = assertThrows(DateTimeParseException.class, () ->
                task.setStartDateTime(""));

        assertEquals("Text '' could not be parsed at index 0", exception.getMessage());
    }

    @Test
    public void shouldThrowNoExceptionWhileSettingUpstartDateTimeForSubtask() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = nowDateTime.minusMinutes(nowDateTime.getMinute()).
                minusSeconds(nowDateTime.getSecond()).
                minusNanos(nowDateTime.getNano()).plusHours(24);

        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);

        subtask1.setStartDateTime(startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));

        assertEquals(startDateTime, subtask1.getStartDateTime(),
                "An error occurred during setting start time for subtask1");
        assertDoesNotThrow(() ->
                subtask1.setStartDateTime(startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER)));
    }

    @Test
    public void shouldReturnDateTimeParseExceptionWhileSettingUpIncorrectStartDateTimeForSubtask() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);

        final DateTimeParseException exception = assertThrows(DateTimeParseException.class, () ->
                subtask1.setStartDateTime(""));

        assertEquals("Text '' could not be parsed at index 0", exception.getMessage());
    }

    @Test
    public void shouldThrowNoExceptionWhileSettingUpDurationForTask() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = nowDateTime.minusMinutes(nowDateTime.getMinute()).
                minusSeconds(nowDateTime.getSecond()).
                minusNanos(nowDateTime.getNano()).plusHours(24);

        Task task1 = taskManager.createTask("task1", "1st task");

        task1.setStartDateTime(startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));
        task1.setDuration(15L);

        assertEquals(Duration.ofMinutes(15L), task1.getDuraTion(),
                "An error occurred while setting duration for task1");

        assertDoesNotThrow(() -> task1.setDuration(15L));
    }

    @Test
    public void shouldThrowNoExceptionWhileSettingUpDurationForSubtask() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = nowDateTime.minusMinutes(nowDateTime.getMinute()).
                minusSeconds(nowDateTime.getSecond()).
                minusNanos(nowDateTime.getNano()).plusHours(24);

        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);

        subtask1.setStartDateTime(startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));
        subtask1.setDuration(15L);

        assertEquals(Duration.ofMinutes(15L), subtask1.getDuraTion(), "Incorrect duration for subtask1");

        assertDoesNotThrow(() -> subtask1.setDuration(15L));
    }

    @Test
    public void shouldThrowManagerSaveExceptionWhileSettingUpDurationOf10ForTask() {
        Task task = taskManager.createTask("task1", "1st task");

        final ManagerSaveException exception = assertThrows(ManagerSaveException.class, () ->
                task.setDuration(10L));

        assertEquals("Duration time should by exactly divisible by "
                + FileBackedTasksManager.GRID_TIME_SPACE + ".", exception.getMessage());
    }

    @Test
    public void shouldThrowManagerSaveExceptionWhileSettingUpDurationOfMinus10ForTask() {
        Task task = taskManager.createTask("task1", "1st task");

        final ManagerSaveException exception = assertThrows(ManagerSaveException.class, () ->
                task.setDuration(-15L));

        assertEquals("Duration must be positive.", exception.getMessage());
    }

    @Test
    public void shouldThrowManagerSaveExceptionWhileSettingUpDurationOf10ForSubtask() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);

        final ManagerSaveException exception = assertThrows(ManagerSaveException.class, () ->
                subtask1.setDuration(10L));

        assertEquals("Duration time should by exactly divisible by "
                + FileBackedTasksManager.GRID_TIME_SPACE + ".", exception.getMessage());
    }

    @Test
    public void shouldThrowManagerSaveExceptionWhileSettingUpDurationOfMinus10ForSubtask() {
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);

        final ManagerSaveException exception = assertThrows(ManagerSaveException.class, () ->
                subtask1.setDuration(-15L));

        assertEquals("Duration must be positive.", exception.getMessage());
    }

    @Test
    public void shouldCalculateTimeParametersOfEpic() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = nowDateTime.minusMinutes(nowDateTime.getMinute()).
                minusSeconds(nowDateTime.getSecond()).
                minusNanos(nowDateTime.getNano()).plusHours(24);

        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        taskManager.put(epic1);

        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);

        subtask1.setStartDateTime(startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));

        subtask1.setDuration(15L);

        taskManager.put(subtask1);


        Subtask subtask2 = taskManager.createSubtask("subtask2", "2 subtask", epic1);
        subtask2.setStartDateTime(startDateTime.plusMinutes(30L).format(FileBackedTasksManager.DATE_TIME_FORMATTER));

        subtask2.setDuration(15L);

        taskManager.put(subtask2);
        taskManager.update(epic1);

        assertEquals(subtask1.getStartDateTime(), epic1.getStartDateTime(), "Incorrect start time for epic1");
        assertEquals(Duration.ofMinutes(30L), epic1.getDuraTion(), "Incorrect duration for epic1");
        assertEquals(subtask2.getEndDateTime(), epic1.getEndDateTime(), "Incorrect end time for epic1");

        subtask2.setDuration(0L);
        taskManager.update(epic1);

        assertEquals(subtask1.getStartDateTime(), epic1.getStartDateTime(), "Incorrect start time for epic1");
        assertEquals(Duration.ofMinutes(15L), epic1.getDuraTion(), "Incorrect duration for epic1");
        assertEquals(subtask2.getEndDateTime(), epic1.getEndDateTime(), "Incorrect end time for epic1");

        taskManager.deleteSubtaskById(subtask2.getId());
        taskManager.update(epic1);

        assertEquals(subtask1.getStartDateTime(), epic1.getStartDateTime(), "Incorrect start time for epic1");
        assertEquals(Duration.ofMinutes(15L), epic1.getDuraTion(), "Incorrect duration for epic1");
        assertEquals(subtask1.getEndDateTime(), epic1.getEndDateTime(), "Incorrect end time for epic1");

        Subtask subtask3 = taskManager.createSubtask("subtask3", "3rd subtask", epic1);
        taskManager.put(subtask3);

        subtask3.setDuration(15L);
        taskManager.update(epic1);
        assertEquals(subtask3.getEndDateTime(), epic1.getEndDateTime(), "Incorrect end time for epic1");
    }

    @Test
    public void shouldSaveAndRestoreAllNonEmptyMapsAndNonEmptyHistory() {
        File file = new File("log/log.csv");
        FileBackedTasksManager fileBackedTasksManager1 = new FileBackedTasksManager(file);

        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = nowDateTime.minusMinutes(nowDateTime.getMinute()).
                minusSeconds(nowDateTime.getSecond()).
                minusNanos(nowDateTime.getNano()).plusHours(24);

        Task task1 = fileBackedTasksManager1.createTask("task1", "1st task");
        fileBackedTasksManager1.put(task1);
        task1.setStartDateTime(startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));
        task1.setDuration(15L);
        fileBackedTasksManager1.update(task1);
        Task task2 = fileBackedTasksManager1.createTask("task2", "2d task");
        fileBackedTasksManager1.put(task2);
        task2.setStartDateTime(startDateTime.plusMinutes(30).format(FileBackedTasksManager.DATE_TIME_FORMATTER));
        task2.setDuration(15L);
        fileBackedTasksManager1.update(task2);
        fileBackedTasksManager1.update(task2);
        Epic epic1 = fileBackedTasksManager1.createEpic("epic1", "1st epic");
        fileBackedTasksManager1.put(epic1);
        Subtask subtask1 = fileBackedTasksManager1.createSubtask("subtask1", "1st subtask", epic1);
        fileBackedTasksManager1.put(subtask1);
        subtask1.setStartDateTime(startDateTime.plusMinutes(60).format(FileBackedTasksManager.DATE_TIME_FORMATTER));
        subtask1.setDuration(15L);
        fileBackedTasksManager1.update(subtask1);
        Subtask subtask2 = fileBackedTasksManager1.createSubtask("subtask2", "2d subtask", epic1);
        fileBackedTasksManager1.put(subtask2);
        subtask2.setStartDateTime(startDateTime.plusMinutes(90).format(FileBackedTasksManager.DATE_TIME_FORMATTER));
        subtask2.setDuration(15L);

        fileBackedTasksManager1.update(epic1);
        fileBackedTasksManager1.getTaskById(task2.getId());
        fileBackedTasksManager1.getTaskById(task1.getId());
        fileBackedTasksManager1.getEpicById(epic1.getId());
        fileBackedTasksManager1.getSubtaskById(subtask2.getId());
        fileBackedTasksManager1.getSubtaskById(subtask1.getId());

        FileBackedTasksManager fileBackedTasksManager2 = FileBackedTasksManager.loadFromFile(file);

        assertEquals(fileBackedTasksManager1.tasks, fileBackedTasksManager2.tasks,
                "An error occurred while saving to and restoring from log file map of tasks");
        assertEquals(fileBackedTasksManager1.subtasks, fileBackedTasksManager2.subtasks,
                "An error occurred while saving to and restoring from log file map of subtasks");
        assertEquals(fileBackedTasksManager1.epics, fileBackedTasksManager2.epics,
                "An error occurred while saving to and restoring from log file map of epics");
        assertEquals(fileBackedTasksManager1.getPrioritizedTasks(), fileBackedTasksManager2.getPrioritizedTasks(),
                "An error occurred while saving to and restoring from log file TreeSet of prioritized tasks");
        assertEquals(fileBackedTasksManager1.getHistory(), fileBackedTasksManager2.getHistory(),
                "An error occurred while saving to and restoring from log file TreeSet of prioritized tasks");
        Task task3 = fileBackedTasksManager2.createTask("task3", "3rd task");

        task3.setDuration(15L);

        assertDoesNotThrow(() -> fileBackedTasksManager2.put(task3));
    }


    @Test
    public void shouldSaveAndRestoreMapsAndHistoryInCaseOfEmptyEpic() {
        File file = new File("log/log.csv");
        FileBackedTasksManager fileBackedTasksManager1 = new FileBackedTasksManager(file);

        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = nowDateTime.minusMinutes(nowDateTime.getMinute()).
                minusSeconds(nowDateTime.getSecond()).
                minusNanos(nowDateTime.getNano()).plusHours(24);

        Task task1 = fileBackedTasksManager1.createTask("task1", "1st task");
        fileBackedTasksManager1.put(task1);
        task1.setStartDateTime(startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));
        task1.setDuration(15L);
        fileBackedTasksManager1.update(task1);
        Task task2 = fileBackedTasksManager1.createTask("task2", "2d task");
        fileBackedTasksManager1.put(task2);
        task2.setStartDateTime(startDateTime.plusMinutes(30).format(FileBackedTasksManager.DATE_TIME_FORMATTER));
        task2.setDuration(15L);
        fileBackedTasksManager1.update(task2);
        fileBackedTasksManager1.update(task2);
        Epic epic1 = fileBackedTasksManager1.createEpic("epic1", "1st epic");
        fileBackedTasksManager1.put(epic1);

        fileBackedTasksManager1.getTaskById(task2.getId());
        fileBackedTasksManager1.getTaskById(task1.getId());
        fileBackedTasksManager1.getEpicById(epic1.getId());

        FileBackedTasksManager fileBackedTasksManager2 = FileBackedTasksManager.loadFromFile(file);

        assertEquals(fileBackedTasksManager1.tasks, fileBackedTasksManager2.tasks,
                "An error occurred while saving to and restoring from log file map of tasks");
        assertEquals(fileBackedTasksManager1.subtasks, fileBackedTasksManager2.subtasks,
                "An error occurred while saving to and restoring from log file map of subtasks");
        assertEquals(fileBackedTasksManager1.epics, fileBackedTasksManager2.epics,
                "An error occurred while saving to and restoring from log file map of epics");
        assertEquals(fileBackedTasksManager1.getPrioritizedTasks(), fileBackedTasksManager2.getPrioritizedTasks(),
                "An error occurred while saving to and restoring from log file TreeSet of prioritized tasks");
        assertEquals(fileBackedTasksManager1.getHistory(), fileBackedTasksManager2.getHistory(),
                "An error occurred while saving to and restoring from log file TreeSet of prioritized tasks");

        Task task3 = fileBackedTasksManager2.createTask("task3", "3rd task");
        task3.setDuration(15L);

        assertDoesNotThrow(() -> fileBackedTasksManager2.put(task3));

    }

    @Test
    public void shouldSaveAndRestoreEmptyHistoryAndEmptyMaps() {
        File file = new File("log/log.csv");

        FileBackedTasksManager fileBackedTasksManager1 = new FileBackedTasksManager(file);

        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = nowDateTime.minusMinutes(nowDateTime.getMinute()).
                minusSeconds(nowDateTime.getSecond()).
                minusNanos(nowDateTime.getNano()).plusHours(24);

        Task task1 = fileBackedTasksManager1.createTask("task1", "1st task");
        fileBackedTasksManager1.put(task1);
        task1.setStartDateTime(startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));
        task1.setDuration(15L);
        taskManager.update(task1);
        taskManager.deleteTopLevelTaskById(task1.getId(), task1);

        FileBackedTasksManager fileBackedTasksManager2 = FileBackedTasksManager.loadFromFile(file);

        assertTrue(fileBackedTasksManager2.tasks.isEmpty(),
                "An error occurred while saving to and restoring from log file map of tasks");
        assertTrue(fileBackedTasksManager2.subtasks.isEmpty(),
                "An error occurred while saving to and restoring from log file map of subtasks");
        assertTrue(fileBackedTasksManager2.epics.isEmpty(),
                "An error occurred while saving to and restoring from log file map of epics");
        assertTrue(fileBackedTasksManager2.getPrioritizedTasks().isEmpty(),
                "An error occurred while saving to and restoring from log file TreeSet of prioritized tasks");
        assertTrue(fileBackedTasksManager2.getHistory().isEmpty(),
                "An error occurred while saving to and restoring from log file TreeSet of prioritized tasks");
    }

    @Test
    public void shouldSaveAndRestoreNonEmptyMapsAndEmptyHistory() {
        File file1 = new File("log/nonEmptyTasksAndEmptyHistory.csv");

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file1);

        Task task3 = CSVTaskFormatter.
                fromString("7,TASK,task3,IN_PROGRESS,3rd task,20.03.2023 10:00,15,20.03.2023 10:15, ");

        assert task3 != null;

        fileBackedTasksManager1.tasks.put(task3.getId(), task3);

        Task task4 = CSVTaskFormatter.
                fromString("8,TASK,task4,DONE,4th task,20.03.2023 10:30,15,20.03.2023 10:45, ");

        assert task4 != null;

        fileBackedTasksManager1.tasks.put(task4.getId(), task4);

        Subtask subtask3 = (Subtask) CSVTaskFormatter.
                fromString("9,SUBTASK,subtask3,IN_PROGRESS,3rd subtask,20.03.2023 11:00,15,20.03.2023 11:15,3");

        assert subtask3 != null;

        fileBackedTasksManager1.subtasks.put(subtask3.getId(), subtask3);

        Subtask subtask4 = (Subtask) CSVTaskFormatter.
                fromString("10,SUBTASK,subtask4,NEW,4th subtask,20.03.2023 11:30,15,20.03.2023 11:45,3");

        assert subtask4 != null;

        fileBackedTasksManager1.subtasks.put(subtask4.getId(), subtask4);

        assertTrue(fileBackedTasksManager1.tasks.containsValue(task3),
                "An error occurred during restoring of task3 from log file");
        assertTrue(fileBackedTasksManager1.tasks.containsValue(task4),
                "An error occurred during restoring of task4 from log file");
        assertEquals(4, fileBackedTasksManager1.tasks.size(),
                "An error occurred during maps of tasks restoring");
        assertTrue(fileBackedTasksManager1.subtasks.containsValue(subtask3),
                "An error occurred during restoring of subtask3 from log file");
        assertTrue(fileBackedTasksManager1.subtasks.containsValue(subtask3),
                "An error occurred during restoring of subtask1 from log file");
        assertEquals(4, fileBackedTasksManager1.subtasks.size(),
                "An error occurred during maps of subtasks restoring");

        Epic epic2 = (Epic) CSVTaskFormatter.
                fromString("11,EPIC,epic2,IN_PROGRESS,2d epic,20.03.2023 11:00,30,20.03.2023 11:45, ");

        assert epic2 != null;

        fileBackedTasksManager1.epics.put(epic2.getId(), epic2);
        epic2.attachSubtaskToEpic(subtask3.getId());
        epic2.attachSubtaskToEpic(subtask4.getId());

        assertTrue(fileBackedTasksManager1.epics.containsValue(epic2),
                "An error occurred during restoring of epic1 from log file");
        assertEquals(2, fileBackedTasksManager1.epics.size(),
                "An error occurred during maps of epics restoring");
        assertTrue(fileBackedTasksManager1.getHistory().isEmpty(),
                "An error occurred while restoring history from log file");
    }

    @Test
    public void shouldThrowManagerSaveExceptionWhileRestoringLogWithHistoryAndWithoutTasks() {
        File file2 = new File("log/emptyTasksAndNonEmptyHistory.csv");

        final ManagerSaveException exception = assertThrows(ManagerSaveException.class, () ->
                FileBackedTasksManager.loadFromFile(file2));
        assertEquals("Ann error occurred while restoring from log file", exception.getMessage());
    }
}