package manager;

import org.junit.Test;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CSVTaskFormatterTest {
    protected HistoryManager historyManager;
    protected FileBackedTasksManager fileBackedTasksManager;
    protected long duration = 15;

    public CSVTaskFormatterTest() {
        File file = new File("log/log.csv");
        this.historyManager = Managers.getDefaultHistory();
        this.fileBackedTasksManager = new FileBackedTasksManager(file);
    }

    @Test
    public void shouldConvertTaskToString() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = nowDateTime.minusMinutes(nowDateTime.getMinute()).
                minusSeconds(nowDateTime.getSecond()).
                minusNanos(nowDateTime.getNano()).plusHours(24);


        Task task1 = fileBackedTasksManager.createTask("task1", "1st task");
        task1.setStartTime(startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));
        task1.setDuration(duration);

        String taskStr = CSVTaskFormatter.toString(task1);
        String[] elements = taskStr.split(",");

        assertTrue((Integer.parseInt(elements[0]) == task1.getId())
                        & (TaskType.valueOf(elements[1]).equals(TaskType.valueOf("TASK")))
                        & (elements[2].equals(task1.getName()))
                        & (Status.valueOf(elements[3]).equals(task1.getStatus()))
                        & (elements[4].equals(task1.getDescription()))
                        & (LocalDateTime.parse(elements[5], FileBackedTasksManager.
                        DATE_TIME_FORMATTER).equals(task1.getStartDateTime()))
                        & Duration.ofMinutes(Long.parseLong(elements[6])).equals(task1.getDuraTion())
                        & (LocalDateTime.parse(elements[7], FileBackedTasksManager.
                        DATE_TIME_FORMATTER).equals(task1.getEndTime())),
                "task1 was not converted correctly to string");
    }

    @Test
    public void shouldConvertSubtaskToString() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = nowDateTime.minusMinutes(nowDateTime.getMinute()).
                minusSeconds(nowDateTime.getSecond()).
                minusNanos(nowDateTime.getNano()).plusHours(24);

        Epic epic1 = fileBackedTasksManager.createEpic("epic1", "1st epic");
        Subtask subtask1 = fileBackedTasksManager.createSubtask("subtask1", "1st subtask", epic1);
        subtask1.setStartTime(startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));
        subtask1.setDuration(duration);

        String taskStr = CSVTaskFormatter.toString(subtask1);
        String[] elements = taskStr.split(",");

        assertTrue((Integer.parseInt(elements[0]) == subtask1.getId())
                        & (TaskType.valueOf(elements[1]).equals(TaskType.valueOf("SUBTASK")))
                        & (elements[2].equals(subtask1.getName()))
                        & (Status.valueOf(elements[3]).equals(subtask1.getStatus()))
                        & (elements[4].equals(subtask1.getDescription()))
                        & (LocalDateTime.parse(elements[5], FileBackedTasksManager.
                        DATE_TIME_FORMATTER).equals(subtask1.getStartDateTime()))
                        & Duration.ofMinutes(Long.parseLong(elements[6])).equals(subtask1.getDuraTion())
                        & (LocalDateTime.parse(elements[7], FileBackedTasksManager.
                        DATE_TIME_FORMATTER).equals(subtask1.getEndTime())) &
                        (Integer.parseInt(elements[8].trim()) == epic1.getId()),
                "subtask1 was not converted correctly to string");
    }

    @Test
    public void shouldConvertEpicToString() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = nowDateTime.minusMinutes(nowDateTime.getMinute()).
                minusSeconds(nowDateTime.getSecond()).
                minusNanos(nowDateTime.getNano()).plusHours(24);

        Epic epic1 = fileBackedTasksManager.createEpic("epic1", "1st epic");
        Subtask subtask1 = fileBackedTasksManager.createSubtask("subtask1", "1st subtask", epic1);
        subtask1.setStartTime(startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER));
        subtask1.setDuration(duration);

        String taskStr = CSVTaskFormatter.toString(epic1);
        String[] elements = taskStr.split(",");

        assertTrue((Integer.parseInt(elements[0]) == epic1.getId())
                        & (TaskType.valueOf(elements[1]).equals(TaskType.valueOf("EPIC")))
                        & (elements[2].equals(epic1.getName()))
                        & (Status.valueOf(elements[3]).equals(epic1.getStatus()))
                        & (elements[4].equals(epic1.getDescription()))
                        & (LocalDateTime.parse(elements[5], FileBackedTasksManager.
                        DATE_TIME_FORMATTER).equals(epic1.getStartDateTime()))
                        & Duration.ofMinutes(Long.parseLong(elements[6])).equals(epic1.getDuraTion())
                        & (LocalDateTime.parse(elements[7], FileBackedTasksManager.
                        DATE_TIME_FORMATTER).equals(epic1.getEndTime())),
                "epic1 was not converted correctly to string");
    }

    @Test
    public void shouldRestoreTaskFromString() {
        String taskStr = "1,TASK,task1,IN_PROGRESS,1st task,20.03.2023 10:00,15,20.03.2023 10:15, ";
        String[] elements = taskStr.split(",");
        Task task1 = CSVTaskFormatter.fromString(taskStr);

        assert task1 != null;
        assertTrue((Integer.parseInt(elements[0]) == task1.getId())
                        & (TaskType.valueOf(elements[1]).equals(TaskType.valueOf("TASK")))
                        & (elements[2].equals(task1.getName()))
                        & (Status.valueOf(elements[3]).equals(task1.getStatus()))
                        & (elements[4].equals(task1.getDescription()))
                        & (LocalDateTime.parse(elements[5], FileBackedTasksManager.
                        DATE_TIME_FORMATTER).equals(task1.getStartDateTime()))
                        & Duration.ofMinutes(Long.parseLong(elements[6])).equals(task1.getDuraTion())
                        & (LocalDateTime.parse(elements[7], FileBackedTasksManager.
                        DATE_TIME_FORMATTER).equals(task1.getEndTime())),
                "task1 was not restored correctly from string");
    }

    @Test
    public void shouldRestoreSubtaskFromString() {
        String taskStr = "2,SUBTASK,subtask1,NEW,1st subtask,20.03.2023 19:00,15,20.03.2023 19:15,1";
        String[] elements = taskStr.split(",");
        Subtask subtask1 = (Subtask) CSVTaskFormatter.fromString(taskStr);

        assert subtask1 != null;
        assertTrue((Integer.parseInt(elements[0]) == subtask1.getId())
                        & (TaskType.valueOf(elements[1]).equals(TaskType.valueOf("SUBTASK")))
                        & (elements[2].equals(subtask1.getName()))
                        & (Status.valueOf(elements[3]).equals(subtask1.getStatus()))
                        & (elements[4].equals(subtask1.getDescription()))
                        & (LocalDateTime.parse(elements[5], FileBackedTasksManager.
                        DATE_TIME_FORMATTER).equals(subtask1.getStartDateTime()))
                        & Duration.ofMinutes(Long.parseLong(elements[6])).equals(subtask1.getDuraTion())
                        & (LocalDateTime.parse(elements[7], FileBackedTasksManager.
                        DATE_TIME_FORMATTER).equals(subtask1.getEndTime())) &
                        (Integer.parseInt(elements[8]) == subtask1.getUpperEpicId()),
                "subtask1 was not restored correctly from string");
    }

    @Test
    public void shouldRestoreEpicFromString() {
        String taskStr = "3,EPIC,epic1,IN_PROGRESS,1st epic,20.03.2023 11:00,30,20.03.2023 11:45, ";
        String[] elements = taskStr.split(",");
        Epic epic1 = (Epic) CSVTaskFormatter.fromString(taskStr);

        assert epic1 != null;
        assertTrue((Integer.parseInt(elements[0]) == epic1.getId())
                        & (TaskType.valueOf(elements[1]).equals(TaskType.valueOf("EPIC")))
                        & (elements[2].equals(epic1.getName()))
                        & (Status.valueOf(elements[3]).equals(epic1.getStatus()))
                        & (elements[4].equals(epic1.getDescription()))
                        & (LocalDateTime.parse(elements[5], FileBackedTasksManager.
                        DATE_TIME_FORMATTER).equals(epic1.getStartDateTime()))
                        & Duration.ofMinutes(Long.parseLong(elements[6])).equals(epic1.getDuraTion())
                        & (LocalDateTime.parse(elements[7], FileBackedTasksManager.
                        DATE_TIME_FORMATTER).equals(epic1.getEndTime())),
                "epic1 was not restored correctly from string");
    }

    @Test
    public void shouldThrowNumberFormatExceptionWhileRestoringTaskFromIncorrectString() {
        final NumberFormatException exception = assertThrows(NumberFormatException.class, () ->
                CSVTaskFormatter.fromString("suxx"));
        assertEquals("For input string: \"suxx\"", exception.getMessage());
    }

    @Test
    public void shouldConvertNonEmptyHistoryToString() {
        Task task1 = fileBackedTasksManager.createTask("task1", "1st task");
        Task task2 = fileBackedTasksManager.createTask("task2", "2d task");
        Task task3 = fileBackedTasksManager.createTask("task3", "3rd task");

        fileBackedTasksManager.put(task1);
        fileBackedTasksManager.put(task2);
        fileBackedTasksManager.put(task3);
        fileBackedTasksManager.getTaskById(task1.getId());
        fileBackedTasksManager.getTaskById(task2.getId());
        fileBackedTasksManager.getTaskById(task3.getId());

        String historyStr = CSVTaskFormatter.historyToString(fileBackedTasksManager.getHistoryManager());
        String[] elements = historyStr.split(",");

        assertTrue((elements.length == 3) & (Integer.parseInt(elements[0]) == task1.getId())
                        & (Integer.parseInt(elements[1]) == task2.getId())
                        & (Integer.parseInt(elements[2]) == task3.getId()),
                "An error occurred during conversion history to string");
    }

    @Test
    public void shouldConvertEmptyHistoryToEmptyString() {
        Task task1 = fileBackedTasksManager.createTask("task1", "1st task");
        Task task2 = fileBackedTasksManager.createTask("task2", "2d task");
        Task task3 = fileBackedTasksManager.createTask("task3", "3rd task");

        fileBackedTasksManager.put(task1);
        fileBackedTasksManager.put(task2);
        fileBackedTasksManager.put(task3);

        String historyStr = CSVTaskFormatter.historyToString(fileBackedTasksManager.getHistoryManager());

        assertTrue(historyStr.isEmpty(), "An error occurred during conversion empty history to empty string");
    }

    @Test
    public void shouldReturnNonEmptyListFromNonEmptyString() {
        String str = "1,2,3";
        List<Integer> list = CSVTaskFormatter.historyFromString(str);

        assertTrue((list.size() == 3) & (list.get(0) == 1) & (list.get(1) == 2) & (list.get(2) == 3),
                "An error occurred while restoring history from string");
    }

    @Test
    public void shouldReturnEmptyHistoryFromEmptyString() {
        List<Integer> list = CSVTaskFormatter.historyFromString("");

        assertTrue(list.isEmpty(),
                "An error occurred while restoring history from empty string");
    }
}