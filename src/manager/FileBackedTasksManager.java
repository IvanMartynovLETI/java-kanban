package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    protected final File file;
    protected final HashMap<LocalDateTime, Boolean> gridOfTimes = new HashMap<>();
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public static final Long GRID_TIME_SPACE = 15L;//шаг временнОй сетки планирования задач, минут
    protected LocalDateTime startDateTimeOfUnusualTsk; //задачи с пропущенным временем начала планировщик будет
                                                       //помещать на конец года, каждая новая такая задача будет
                                                       //сдвигаться к началу года.

    protected LocalDateTime tempEndDateTime;
    private final static LocalDateTime nowDateTime = LocalDateTime.now();
    public static LocalDateTime startDateTime = nowDateTime.      //Начальная точка временнОй шкалы
            minusHours(nowDateTime.getHour()).                    //планирования задач (период планирования
            minusMinutes(nowDateTime.getMinute()).                //-год).
            minusSeconds(nowDateTime.getSecond()).
            minusNanos(nowDateTime.getNano()).plusDays(1);

    public static LocalDateTime endDateTime = startDateTime.plusYears(1L); //Конечная точка временнОй шкалы
                                                                           // планирования задач.
    protected final Comparator<Task> comparator = Comparator.comparing(Task::getStartDateTime);

    protected final Set<Task> prioritizedTasks = new TreeSet<>(comparator);

    public FileBackedTasksManager(File file) {
        super();
        this.file = file;
        this.startDateTimeOfUnusualTsk = endDateTime;
        this.tempEndDateTime = endDateTime;
        fillTimeGrid();
    }

    protected <T extends Task> void addToPrioritizedTasks(T t) {

        for (LocalDateTime dtm = t.getStartDateTime(); dtm.isBefore(t.getEndDateTime().plusMinutes(GRID_TIME_SPACE));
             dtm = dtm.plusMinutes(GRID_TIME_SPACE)) {
            gridOfTimes.put(dtm, false);
        }

        prioritizedTasks.add(t);
    }

    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    protected void fillTimeGrid() {
        for (LocalDateTime dtm = startDateTime; dtm.isBefore(endDateTime.plusMinutes(GRID_TIME_SPACE));
             dtm = dtm.plusMinutes(GRID_TIME_SPACE)) {
            gridOfTimes.put(dtm, true);
        }
    }

    protected void fillTimeGridFromEndStringOfFile(List<String> listOfStrings) {
        String[] elements = listOfStrings.get(listOfStrings.size() - 1).split(",");

        for (LocalDateTime ldm = LocalDateTime.parse(elements[0], DATE_TIME_FORMATTER);
             ldm.isBefore(LocalDateTime.parse(elements[1], DATE_TIME_FORMATTER).plusMinutes(GRID_TIME_SPACE));
             ldm = ldm.plusMinutes(GRID_TIME_SPACE)) {
            gridOfTimes.put(ldm, true);
        }
    }

    protected LocalDateTime restoreTempEndDateTimeFromListOfStrings(List<String> listOfStrings) {
        String[] elements = listOfStrings.get(listOfStrings.size() - 1).split(",");
        return LocalDateTime.parse(elements[2], DATE_TIME_FORMATTER);
    }

    protected void restoreTimeGrid(Task task) {
        if (!(task instanceof Epic)) {
            for (LocalDateTime ldm = task.getStartDateTime();
                 ldm.isBefore(task.getEndDateTime().plusMinutes(GRID_TIME_SPACE));
                 ldm = ldm.plusMinutes(GRID_TIME_SPACE)) {
                gridOfTimes.put(ldm, true);
            }
        }
    }

    protected boolean isTskValid(Task task) {
        boolean isTskValid = true;
        if(!(task.getStartDateTime().isAfter(startDateTime.minusMinutes(GRID_TIME_SPACE))) 
                & task.getEndDateTime().isBefore(endDateTime.plusMinutes(GRID_TIME_SPACE))) {
            isTskValid = false;
        } else {
            for (LocalDateTime dtm = task.getStartDateTime(); dtm.isBefore(task.getEndDateTime().
                    plusMinutes(GRID_TIME_SPACE));
                 dtm = dtm.plusMinutes(GRID_TIME_SPACE)) {
                if (!gridOfTimes.get(dtm)) {
                    isTskValid =  false;
                }
            }
        }

        return isTskValid;
    }

    protected  <T extends Task> void incorrectTskImprover(T t) {
        if (!(t instanceof Epic)) {
            t.setStartDateTime((tempEndDateTime.minusMinutes(t.getDuraTion().toMinutes()))
                    .format(FileBackedTasksManager.DATE_TIME_FORMATTER));
            t.setEndDateTime();
            startDateTimeOfUnusualTsk = t.getStartDateTime().minusMinutes(GRID_TIME_SPACE);//обеспечиваем временной
            tempEndDateTime = startDateTimeOfUnusualTsk;                                   //зазор между задачами
        }
    }

    protected void actualizeTimeParametersOfEpc(int epicId) {
        ArrayList<Integer> iDsOfAttachedSubtsks = epics.get(epicId).getSubTaskIds();
        LocalDateTime minStartDateTime = LocalDateTime.parse("01.01.0001 00:00", DATE_TIME_FORMATTER);
        LocalDateTime maxEndDateTime = LocalDateTime.parse("01.01.0001 00:00", DATE_TIME_FORMATTER);
        Duration sumOfDurations = Duration.ofMinutes(0);
        Epic tempEpic;

        for (Integer iD : iDsOfAttachedSubtsks) {
            if (iDsOfAttachedSubtsks.indexOf(iD) == 0) {
                minStartDateTime = subtasks.get(iD).getStartDateTime();
                maxEndDateTime = subtasks.get(iD).getEndDateTime();
            } else {
                if (minStartDateTime.isAfter(subtasks.get(iD).getStartDateTime())) {
                    minStartDateTime = subtasks.get(iD).getStartDateTime();
                }

                if (maxEndDateTime.isBefore(subtasks.get(iD).getEndDateTime())) {
                    maxEndDateTime = subtasks.get(iD).getEndDateTime();
                }
            }
            sumOfDurations = sumOfDurations.plus(subtasks.get(iD).getDuraTion());
        }

        tempEpic = epics.get(epicId);
        tempEpic.setStartDateTime(minStartDateTime.format(DATE_TIME_FORMATTER));
        tempEpic.setDuration(sumOfDurations.toMinutes());
        tempEpic.setEndDateTime(maxEndDateTime.format(DATE_TIME_FORMATTER));

        epics.remove(epicId);
        epics.put(epicId, tempEpic);
    }

    public static FileBackedTasksManager loadFromFile(File file) throws ManagerSaveException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        if (!file.isFile()) {
            throw new ManagerSaveException("Log file does not exist.");
        } else if (file.length() == 0) {
            throw new ManagerSaveException("Log file is empty, nothing to restore");
        } else {
            try (FileReader fr = new FileReader(file.toString(), StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(fr)) {
                ArrayList<String> listOfStrings = new ArrayList<>();

                while (br.ready()) {
                    listOfStrings.add(br.readLine());
                }
                if (!fileBackedTasksManager.hasAnyKindOfTask(listOfStrings)) {
                    throw new ManagerSaveException("Ann error occurred while restoring from log file");
                }

                startDateTime = LocalDateTime.parse(listOfStrings.get(listOfStrings.size()-1).split(",")[0],
                        FileBackedTasksManager.DATE_TIME_FORMATTER);
                endDateTime = LocalDateTime.parse(listOfStrings.get(listOfStrings.size()-1).split(",")[1],
                        FileBackedTasksManager.DATE_TIME_FORMATTER);

                fileBackedTasksManager.fillTimeGridFromEndStringOfFile(listOfStrings);
                fileBackedTasksManager.tempEndDateTime =
                        fileBackedTasksManager.restoreTempEndDateTimeFromListOfStrings(listOfStrings);
                fileBackedTasksManager.prioritizedTasks.clear();

                int maxId = fileBackedTasksManager.getMaxId(listOfStrings);
                fileBackedTasksManager.fillMaps(listOfStrings, fileBackedTasksManager);
                fileBackedTasksManager.attachSubtasksToEpics(fileBackedTasksManager);
                fileBackedTasksManager.restoreHistory(listOfStrings, fileBackedTasksManager);
                fileBackedTasksManager.id = maxId;
            } catch (IOException e) {
                throw new ManagerSaveException("An error occurred during reading " + file + " file");
            }
        }

        return fileBackedTasksManager;
    }

    protected boolean hasAnyKindOfTask(ArrayList<String> listOfStrings) {
        boolean isContainsAnyKindOfTask = true;
        if (listOfStrings.isEmpty()) {
            isContainsAnyKindOfTask = false;
        } else if (listOfStrings.get(listOfStrings.size() - 1).isEmpty()) {
            isContainsAnyKindOfTask = false;
        } else if (listOfStrings.size() < 5) {
            isContainsAnyKindOfTask = false;
        } else {
            for (int line = 1; line < listOfStrings.size() - 4; line++) {

                if (!(listOfStrings.get(line).contains("TASK") | listOfStrings.get(line).contains("SUBTASK")
                        | listOfStrings.get(line).contains("EPIC"))) {
                    isContainsAnyKindOfTask = false;
                    break;
                }
            }
        }

        return isContainsAnyKindOfTask;
    }

    protected int getMaxId(ArrayList<String> listOfStrings) {
        int maxId = 0;
        String[] taskElements;

        for (int line = 1; line < listOfStrings.size() - 4; line++) {
            taskElements = listOfStrings.get(line).split(",");
            int tempMaxId = Integer.parseInt(taskElements[0]);

            if (tempMaxId > maxId) {
                maxId = tempMaxId;
            }
        }

        return maxId;
    }

    protected void fillMaps(ArrayList<String> list, FileBackedTasksManager fileBackedTasksManager) {
        String[] taskElements;

        for (int line = 1; line < list.size() - 4; line++) {
            taskElements = list.get(line).split(",");
            int iD = Integer.parseInt(taskElements[0]);
            TaskType taskType = TaskType.valueOf(taskElements[1]);

            switch (taskType) {
                case TASK -> {
                    fileBackedTasksManager.tasks.put(iD, CSVTaskFormatter.fromString(list.get(line)));
                    fileBackedTasksManager.addToPrioritizedTasks(Objects.requireNonNull(CSVTaskFormatter.
                            fromString(list.get(line))));
                }
                case SUBTASK -> {
                    fileBackedTasksManager.subtasks.put(iD,
                            (Subtask) CSVTaskFormatter.fromString(list.get(line)));
                    fileBackedTasksManager.addToPrioritizedTasks((Subtask) Objects.requireNonNull(CSVTaskFormatter.
                            fromString(list.get(line))));
                }
                case EPIC -> fileBackedTasksManager.epics.put(iD,
                        (Epic) CSVTaskFormatter.fromString(list.get(line)));
                default -> {
                }
            }
        }
    }

    protected void attachSubtasksToEpics(FileBackedTasksManager fileBackedTasksManager) {

        for (Subtask subtask : fileBackedTasksManager.subtasks.values()) {
            Epic tempEpic = fileBackedTasksManager.epics.get(subtask.getUpperEpicId());
            tempEpic.attachSubtaskToEpic(subtask.getId());
            fileBackedTasksManager.epics.remove(subtask.getUpperEpicId());
            fileBackedTasksManager.epics.put(subtask.getUpperEpicId(), tempEpic);
        }

        for (Epic epic : fileBackedTasksManager.epics.values()) {
            actualizeTimeParametersOfEpc(epic.getId());
        }
    }

    protected void restoreHistory(ArrayList<String> list, FileBackedTasksManager fileBackedTasksManager) {
        String historyStr = list.get(list.size() - 3);
        List<Integer> iDsOfTasksToBeRestored = CSVTaskFormatter.historyFromString(historyStr);

        for (Integer iD : iDsOfTasksToBeRestored) {
            if (fileBackedTasksManager.tasks.containsKey(iD)) {
                historyManager.add(fileBackedTasksManager.tasks.get(iD));
            } else if (fileBackedTasksManager.epics.containsKey(iD)) {
                historyManager.add(fileBackedTasksManager.epics.get(iD));
            } else if (fileBackedTasksManager.subtasks.containsKey(iD)) {
                historyManager.add(fileBackedTasksManager.subtasks.get(iD));
            }
        }
    }

    protected void save() throws ManagerSaveException {
        if (!file.isFile()) {//если лога еще нет, его нужно создать
            try {
                Files.createFile(Path.of(file.toString()));
            } catch (IOException e) {
                throw new ManagerSaveException("An error occurred during creation of " + file + " file.");
            }
        } else {//при наличии лога пишем в него, при каждом вызове метода save() идет перезапись содержимого лога
            try (FileWriter fw = new FileWriter(file.toString(), StandardCharsets.UTF_8);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(CSVTaskFormatter.getHeader());

                for (Task task : tasks.values()) {
                    bw.write(CSVTaskFormatter.toString(task));
                }

                for (Epic epic : epics.values()) {
                    bw.write(CSVTaskFormatter.toString(epic));
                }

                for (Subtask subtask : subtasks.values()) {
                    bw.write(CSVTaskFormatter.toString(subtask));
                }

                bw.newLine();
                bw.write(CSVTaskFormatter.historyToString(historyManager));

                bw.newLine();
                bw.newLine();
                bw.write(startDateTime.format(DATE_TIME_FORMATTER) + ","
                        + endDateTime.format(DATE_TIME_FORMATTER) + "," + tempEndDateTime.format(DATE_TIME_FORMATTER));
            } catch (IOException e) {
                throw new ManagerSaveException("An error occurred during writing to log file.");
            }
        }
    }
    
    @Override
    public Task getTaskById(int taskId) {
        Task task = super.getTaskById(taskId);
        save();

        return task;
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        Subtask subtask = super.getSubtaskById(subtaskId);
        save();

        return subtask;
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = super.getEpicById(epicId);
        save();

        return epic;
    }

    @Override
    public <T extends Task> void put(T t) {
        if (!(t instanceof Epic)) {
            if (t.getStartDateTime().equals(LocalDateTime.parse("01.01.0001 00:00", DATE_TIME_FORMATTER))) {
                incorrectTskImprover(t);
            }

            if (!isTskValid(t)) {
                throw new ManagerSaveException(t.getClass().toString() + " " + t.getName()
                        + " has incorrect time parameters and can't be added.");
            } else {
                addToPrioritizedTasks(t);
            }
        }

        super.put(t);
        save();
    }

    @Override
    public <T extends Task> void deleteTopLevelTaskById(int id, T t) {
        if (tasks.containsKey(id) || epics.containsKey(id)) {
            restoreTimeGrid(t);

            if (!(t instanceof Epic)) {
                prioritizedTasks.remove(t);
            }

            super.deleteTopLevelTaskById(id, t);
            save();
        }
    }

    @Override
    public void deleteSubtaskById(int subtaskId) {

        if (subtasks.containsKey(subtaskId)) {
            restoreTimeGrid(getSubtaskById(subtaskId));
            prioritizedTasks.remove(getSubtaskById(subtaskId));
            super.deleteSubtaskById(subtaskId);
            save();
        }
    }

    @Override
    public <T extends Task> void update(T t) {

        if (!(t instanceof Epic)) {
            prioritizedTasks.add(t);
        }

        super.update(t);
        if (t instanceof Epic) {
            actualizeTimeParametersOfEpc(t.getId());
        }

        save();
    }

    @Override
    public <T extends Task> void deleteAllTasksSameKind(T t) {
        if ((t instanceof Subtask) || (t instanceof Epic)) {
            for (Subtask subtask : subtasks.values()) {
                restoreTimeGrid(subtask);
                prioritizedTasks.remove(subtask);
            }
        } else {
            for (Task task : tasks.values()) {
                restoreTimeGrid(task);
                prioritizedTasks.remove(task);
            }
        }

        super.deleteAllTasksSameKind(t);
        save();
    }
}