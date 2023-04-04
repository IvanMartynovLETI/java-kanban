package manager;

import client.KVTaskClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.KVServer;
import server.adapter.DurationAdapter;
import server.adapter.LocalDateTimeAdapter;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskManagerTest extends FileBackedTasksManagerTest{

    private static KVServer kvServer;

    public HttpTaskManagerTest() throws MalformedURLException {
        super(new HttpTaskManager(new URL("http://localhost:8078")));
    }

    @BeforeAll
    public static void beforeAll() {
        try {
            kvServer = new KVServer();
            kvServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void afterAll() {
        kvServer.stop();
    }

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
        super.shouldReturnListOfPrioritizedTasks();
    }

    @Test
    public void shouldReturnEmptyListOfPrioritizedTasks() {
        assertTrue(taskManager.getPrioritizedTasks().isEmpty(), "List of prioritized tasks is not an empty");
    }

    @Test
    public void shouldThrowNoExceptionWhileSettingUpstartDateTimeForTask() {
        super.shouldThrowNoExceptionWhileSettingUpstartDateTimeForTask();
    }

    @Test
    public void shouldReturnDateTimeParseExceptionWhileSettingUpIncorrectStartDateTimeForTask() {
        super.shouldReturnDateTimeParseExceptionWhileSettingUpIncorrectStartDateTimeForTask();
    }

    @Test
    public void shouldThrowNoExceptionWhileSettingUpstartDateTimeForSubtask() {
        super.shouldThrowNoExceptionWhileSettingUpstartDateTimeForSubtask();
    }

    @Test
    public void shouldReturnDateTimeParseExceptionWhileSettingUpIncorrectStartDateTimeForSubtask() {
        super.shouldReturnDateTimeParseExceptionWhileSettingUpIncorrectStartDateTimeForSubtask();
    }

    @Test
    public void shouldThrowNoExceptionWhileSettingUpDurationForTask() {
        super.shouldThrowNoExceptionWhileSettingUpDurationForTask();
    }

    @Test
    public void shouldThrowNoExceptionWhileSettingUpDurationForSubtask() {
        super.shouldThrowNoExceptionWhileSettingUpDurationForSubtask();
    }

    @Test
    public void shouldThrowManagerSaveExceptionWhileSettingUpDurationOf10ForTask() {
        super.shouldThrowManagerSaveExceptionWhileSettingUpDurationOf10ForTask();
    }

    @Test
    public void shouldThrowManagerSaveExceptionWhileSettingUpDurationOfMinus10ForTask() {
        super.shouldThrowManagerSaveExceptionWhileSettingUpDurationOfMinus10ForTask();
    }

    @Test
    public void shouldThrowManagerSaveExceptionWhileSettingUpDurationOf10ForSubtask() {
        super.shouldThrowManagerSaveExceptionWhileSettingUpDurationOf10ForSubtask();
    }

    @Test
    public void shouldThrowManagerSaveExceptionWhileSettingUpDurationOfMinus10ForSubtask() {
        super.shouldThrowManagerSaveExceptionWhileSettingUpDurationOfMinus10ForSubtask();
    }

    @Test
    public void shouldCalculateTimeParametersOfEpic() {
        super.shouldCalculateTimeParametersOfEpic();
    }

    @Test
    public void shouldSaveAndRestoreAllNonEmptyMapsAndNonEmptyHistory() {

        try{
            HttpTaskManager httpTaskManager1 = new HttpTaskManager(new URL("http://localhost:8078"));

            LocalDateTime nowDateTime = LocalDateTime.now();
            LocalDateTime startDateTime = nowDateTime.minusMinutes(nowDateTime.getMinute()).
                    minusSeconds(nowDateTime.getSecond()).
                    minusNanos(nowDateTime.getNano()).plusHours(24);

            Task task1 = httpTaskManager1.createTask("task1", "1st task");
            httpTaskManager1.put(task1);
            task1.setStartTime(startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));
            task1.setDuration(15L);
            httpTaskManager1.update(task1);
            Task task2 = httpTaskManager1.createTask("task2", "2d task");
            httpTaskManager1.put(task2);
            task2.setStartTime(startDateTime.plusMinutes(30).format(FileBackedTasksManager.DATE_TIME_FORMATTER));
            task2.setDuration(15L);
            httpTaskManager1.update(task2);
            httpTaskManager1.update(task2);
            Epic epic1 = httpTaskManager1.createEpic("epic1", "1st epic");
            httpTaskManager1.put(epic1);
            Subtask subtask1 = httpTaskManager1.createSubtask("subtask1", "1st subtask", epic1);
            httpTaskManager1.put(subtask1);
            subtask1.setStartTime(startDateTime.plusMinutes(60).format(FileBackedTasksManager.DATE_TIME_FORMATTER));
            subtask1.setDuration(15L);
            httpTaskManager1.update(subtask1);
            Subtask subtask2 = httpTaskManager1.createSubtask("subtask2", "2d subtask", epic1);
            httpTaskManager1.put(subtask2);
            subtask2.setStartTime(startDateTime.plusMinutes(90).format(FileBackedTasksManager.DATE_TIME_FORMATTER));
            subtask2.setDuration(15L);

            httpTaskManager1.update(epic1);
            httpTaskManager1.getTaskById(task2.getId());
            httpTaskManager1.getTaskById(task1.getId());
            httpTaskManager1.getEpicById(epic1.getId());
            httpTaskManager1.getSubtaskById(subtask2.getId());
            httpTaskManager1.getSubtaskById(subtask1.getId());

            HttpTaskManager httpTaskManager2 = HttpTaskManager.load(new URL("http://localhost:8078"));

            assertEquals(httpTaskManager1.tasks, httpTaskManager2.tasks,
                    "An error occurred while saving to and restoring map of tasks from KVServer");
            assertEquals(httpTaskManager1.subtasks, httpTaskManager2.subtasks,
                    "An error occurred while saving to and restoring map of subtasks from KVServer");
            assertEquals(httpTaskManager1.epics, httpTaskManager2.epics,
                    "An error occurred while saving to and restoring map of epics from KVServer");
            assertEquals(httpTaskManager1.getPrioritizedTasks(), httpTaskManager2.getPrioritizedTasks(),
                    "An error occurred while saving to and restoring from KVServer TreeSet of " +
                            "prioritized tasks");
            assertEquals(httpTaskManager1.getHistory(), httpTaskManager2.getHistory(),
                    "An error occurred while saving to and restoring from KVServer TreeSet of " +
                            "prioritized tasks");
            Task task3 = httpTaskManager2.createTask("task3", "3rd task");

            task3.setDuration(15L);

            assertDoesNotThrow(() -> httpTaskManager2.put(task3));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void shouldSaveAndRestoreMapsAndHistoryInCaseOfEmptyEpic() {

        try {

            HttpTaskManager httpTaskManager1 = new HttpTaskManager(new URL("http://localhost:8078"));

            LocalDateTime nowDateTime = LocalDateTime.now();
            LocalDateTime startDateTime = nowDateTime.minusMinutes(nowDateTime.getMinute()).
                    minusSeconds(nowDateTime.getSecond()).
                    minusNanos(nowDateTime.getNano()).plusHours(24);

            Task task1 = httpTaskManager1.createTask("task1", "1st task");
            httpTaskManager1.put(task1);
            task1.setStartTime(startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));
            task1.setDuration(15L);
            httpTaskManager1.update(task1);
            Task task2 = httpTaskManager1.createTask("task2", "2d task");
            httpTaskManager1.put(task2);
            task2.setStartTime(startDateTime.plusMinutes(30).format(FileBackedTasksManager.DATE_TIME_FORMATTER));
            task2.setDuration(15L);
            httpTaskManager1.update(task2);
            httpTaskManager1.update(task2);
            Epic epic1 = httpTaskManager1.createEpic("epic1", "1st epic");
            httpTaskManager1.put(epic1);

            httpTaskManager1.getTaskById(task2.getId());
            httpTaskManager1.getTaskById(task1.getId());
            httpTaskManager1.getEpicById(epic1.getId());


            HttpTaskManager httpTaskManager2 = HttpTaskManager.load(new URL("http://localhost:8078"));


            assertEquals(httpTaskManager1.tasks, httpTaskManager2.tasks,
                    "An error occurred while saving to and restoring map of tasks from KVServer");
            assertEquals(httpTaskManager1.subtasks, httpTaskManager2.subtasks,
                    "An error occurred while saving to and restoring map of subtasks from KVServer");
            assertEquals(httpTaskManager1.epics, httpTaskManager2.epics,
                    "An error occurred while saving to and restoring map of epics from KVServer");
            assertEquals(httpTaskManager1.getPrioritizedTasks(), httpTaskManager2.getPrioritizedTasks(),
                    "An error occurred while saving to and restoring from KVServer TreeSet of" +
                            " prioritized tasks");
            assertEquals(httpTaskManager1.getHistory(), httpTaskManager2.getHistory(),
                    "An error occurred while saving to and restoring from KVServer TreeSet of" +
                            " prioritized tasks");

            Task task3 = httpTaskManager2.createTask("task3", "3rd task");
            task3.setDuration(15L);

            assertDoesNotThrow(() -> httpTaskManager2.put(task3));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldSaveAndRestoreEmptyHistoryAndEmptyMaps() {

        try {
            HttpTaskManager httpTaskManager1 = new HttpTaskManager(new URL("http://localhost:8078"));

            LocalDateTime nowDateTime = LocalDateTime.now();
            LocalDateTime startDateTime = nowDateTime.minusMinutes(nowDateTime.getMinute()).
                    minusSeconds(nowDateTime.getSecond()).
                    minusNanos(nowDateTime.getNano()).plusHours(24);

            Task task1 = httpTaskManager1.createTask("task1", "1st task");
            httpTaskManager1.put(task1);
            task1.setStartTime(startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));
            task1.setDuration(15L);
            taskManager.update(task1);
            taskManager.deleteTopLevelTaskById(task1.getId(), task1);

            HttpTaskManager httpTaskManager2 = HttpTaskManager.load(new URL("http://localhost:8078"));

            assertTrue(httpTaskManager2.tasks.isEmpty(),
                    "An error occurred while saving to and restoring map of tasks from KVServer");
            assertTrue(httpTaskManager2.subtasks.isEmpty(),
                    "An error occurred while saving to and restoring map of subtasks from KVServer");
            assertTrue(httpTaskManager2.epics.isEmpty(),
                    "An error occurred while saving to and restoring map of epics from KVServer");
            assertTrue(httpTaskManager2.getPrioritizedTasks().isEmpty(),
                    "An error occurred while saving to and restoring from KVServer TreeSet of" +
                            " prioritized tasks");
            assertTrue(httpTaskManager2.getHistory().isEmpty(),
                    "An error occurred while saving to and restoring from KVServer TreeSet of" +
                            " prioritized tasks");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldRestoreNonEmptyMapsAndEmptyHistory() {
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        gsonBuilder.serializeNulls();
        gson = gsonBuilder.create();
        String nonEmptyMapsAndEmptyHistory = gson.toJson(
                """
                        id,type,name,status,description,setStartTime,duration,endTime,epic
                        1,TASK,task1,IN_PROGRESS,1st task,22.08.2023 00:00,15,22.08.2023 00:15,\s
                        2,TASK,task2,DONE,2d task,22.08.2023 00:30,15,22.08.2023 00:45,\s
                        3,EPIC,epic1,IN_PROGRESS,1st epic,22.08.2023 01:00,30,22.08.2023 01:45,\s
                        4,SUBTASK,subtask1,IN_PROGRESS,1st subtask,22.08.2023 01:00,15,22.08.2023 01:15,3
                        5,SUBTASK,subtask2,NEW,2d subtask,22.08.2023 01:30,15,22.08.2023 01:45,3



                        22.08.2023 00:00,22.08.2024 00:00,21.08.2024 22:30""");

        try {

            KVTaskClient client = new KVTaskClient(new URL("http://localhost:8078"));
            client.put("heap", nonEmptyMapsAndEmptyHistory);

            HttpTaskManager httpTaskManager = HttpTaskManager.load(new URL("http://localhost:8078"));

            Task task1 = CSVTaskFormatter.
                    fromString("1,TASK,task1,IN_PROGRESS,1st task,22.08.2023 00:00,15,22.08.2023 00:15, ");
            Task task2 = CSVTaskFormatter.
                    fromString("2,TASK,task2,DONE,2d task,22.08.2023 00:30,15,22.08.2023 00:45, ");
            Epic epic1 = (Epic) CSVTaskFormatter.
                    fromString("3,EPIC,epic1,IN_PROGRESS,1st epic,22.08.2023 01:00,30,22.08.2023 01:45, ");
            Subtask subtask1 = (Subtask) CSVTaskFormatter.
                    fromString("4,SUBTASK,subtask1,IN_PROGRESS,1st subtask,22.08.2023 01:00,15,22.08.2023 01:15,3");
            Subtask subtask2 = (Subtask) CSVTaskFormatter.
                    fromString("5,SUBTASK,subtask2,NEW,2d subtask,22.08.2023 01:30,15,22.08.2023 01:45,3");

            assertTrue(httpTaskManager.getHistory().isEmpty(),
                    "An error occurred while restoring history from log file");
            assertTrue(httpTaskManager.tasks.containsValue(task1),
                    "An error occurred during restoring of task1 from KVServer");
            assertTrue(httpTaskManager.tasks.containsValue(task2),
                    "An error occurred during restoring of task2 from KVServer");
            assertEquals(2, httpTaskManager.tasks.size(),
                    "An error occurred during maps of tasks restoring");
            assertTrue(httpTaskManager.subtasks.containsValue(subtask1),
                    "An error occurred during restoring of subtask1 from KVServer");
            assertTrue(httpTaskManager.subtasks.containsValue(subtask2),
                    "An error occurred during restoring of subtask2 from KVServer");
            assertEquals(2, httpTaskManager.subtasks.size(),
                    "An error occurred during maps of subtasks restoring");

            assert epic1 != null;
            httpTaskManager.epics.put(epic1.getId(), epic1);
            assert subtask1 != null;
            epic1.attachSubtaskToEpic(subtask1.getId());
            assert subtask2 != null;
            epic1.attachSubtaskToEpic(subtask2.getId());

            assertTrue(httpTaskManager.epics.containsValue(epic1),
                    "An error occurred during restoring of epic1 from KVServer");
            assertEquals(1, httpTaskManager.epics.size(),
                    "An error occurred during maps of epics restoring");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldThrowManagerSaveExceptionWhileRestoringLogWithHistoryAndWithoutTasks() {

        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        gsonBuilder.serializeNulls();
        gson = gsonBuilder.create();
        String emptyMapsAndNonEmptyHistory = gson.toJson(
                """
                        id,type,name,status,description,setStartTime,duration,endTime,epic
                        2,1,3,5,4

                        20.04.2023 00:00,20.04.2024 00:00,19.04.2024 22:30""");

        try {

            KVTaskClient client = new KVTaskClient(new URL("http://localhost:8078"));
            client.put("heap", emptyMapsAndNonEmptyHistory);

            final ManagerSaveException exception = assertThrows(ManagerSaveException.class, () ->
                    HttpTaskManager.load(new URL("http://localhost:8078")));
            assertEquals("Ann error occurred while restoring from log.", exception.getMessage());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}