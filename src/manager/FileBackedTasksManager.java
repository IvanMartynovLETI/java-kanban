package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTasksManager(File file) {

        super();
        this.file = file;
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

    public int getMaxId(ArrayList<String> listOfStrings) {
        int maxId = 0;
        String[] taskElements;

        for (int line = 1; line < listOfStrings.size() - 2; line++) {
            taskElements = listOfStrings.get(line).split(",");
            int tempMaxId = Integer.parseInt(taskElements[0]);

            if (tempMaxId > maxId) {
                maxId = tempMaxId;
            }
        }

        return maxId;
    }

    public void fillMaps(ArrayList<String> list, FileBackedTasksManager fileBackedTasksManager) {
        String[] taskElements;

        for (int line = 1; line < list.size() - 2; line++) {
            taskElements = list.get(line).split(",");
            int iD = Integer.parseInt(taskElements[0]);
            TaskType taskType = TaskType.valueOf(taskElements[1]);

            switch (taskType) {
                case TASK -> fileBackedTasksManager.tasks.put(iD, CSVTaskFormatter.fromString(list.get(line)));
                case SUBTASK -> fileBackedTasksManager.subtasks.put(iD,
                        (Subtask) CSVTaskFormatter.fromString(list.get(line)));
                case EPIC -> fileBackedTasksManager.epics.put(iD,
                        (Epic) CSVTaskFormatter.fromString(list.get(line)));
                default -> {
                }
            }
        }
    }

    public void attachSubtasksToEpics(FileBackedTasksManager fileBackedTasksManager) {

        for (Subtask subtask : fileBackedTasksManager.subtasks.values()) {
            Epic tempEpic = fileBackedTasksManager.epics.get(subtask.getUpperEpicId());
            tempEpic.attachSubtaskToEpic(subtask.getId());
            fileBackedTasksManager.epics.remove(subtask.getUpperEpicId());
            fileBackedTasksManager.epics.put(subtask.getUpperEpicId(), tempEpic);
        }
    }

    public void restoreHistory(ArrayList<String> list, FileBackedTasksManager fileBackedTasksManager) {
        String historyStr = list.get(list.size() - 1);
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

    private void save() throws ManagerSaveException {
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
        super.put(t);
        save();
    }

    @Override
    public <T extends Task> void deleteTopLevelTaskById(int id, T t) {
        super.deleteTopLevelTaskById(id, t);
        save();
    }

    @Override
    public void deleteSubtaskById(int subtaskId) {
        super.deleteSubtaskById(subtaskId);
        save();
    }

    @Override
    public <T extends Task> void update(T t) {
        super.update(t);
        save();
    }

    @Override
    public <T extends Task> void deleteAllTasksSameKind(T t) {
        super.deleteAllTasksSameKind(t);
        save();
    }

    public static void main(String[] args) {
        File file = new File("log/log.csv");

        FileBackedTasksManager fileBackedTasksManager1 = new FileBackedTasksManager(file);

        Task task1 = fileBackedTasksManager1.createTask("task1", "1st task");
        fileBackedTasksManager1.put(task1);
        Task task2 = fileBackedTasksManager1.createTask("task2", "2d task");
        fileBackedTasksManager1.put(task2);
        Epic epic1 = fileBackedTasksManager1.createEpic("epic1", "1st epic");
        fileBackedTasksManager1.put(epic1);
        Subtask subtask1 = fileBackedTasksManager1.createSubtask("subtask1", "1st subtask", epic1);
        fileBackedTasksManager1.put(subtask1);
        Subtask subtask2 = fileBackedTasksManager1.createSubtask("subtask2", "2d subtask", epic1);
        fileBackedTasksManager1.put(subtask2);
        Subtask subtask3 = fileBackedTasksManager1.createSubtask("subtask3", "3rd subtask", epic1);
        fileBackedTasksManager1.put(subtask3);
        Epic epic2 = fileBackedTasksManager1.createEpic("epic2", "2d epic");
        fileBackedTasksManager1.put(epic2);

        fileBackedTasksManager1.getTaskById(task1.getId());
        fileBackedTasksManager1.getTaskById(task1.getId());
        fileBackedTasksManager1.getTaskById(task2.getId());
        fileBackedTasksManager1.getTaskById(task2.getId());
        fileBackedTasksManager1.getSubtaskById(subtask1.getId());
        fileBackedTasksManager1.getSubtaskById(subtask2.getId());
        fileBackedTasksManager1.getSubtaskById(subtask3.getId());
        fileBackedTasksManager1.getEpicById(epic1.getId());
        fileBackedTasksManager1.getEpicById(epic2.getId());
        System.out.println("Initial history is: ");
        System.out.println(fileBackedTasksManager1.getHistory());

        fileBackedTasksManager1.getTaskById(task1.getId());
        System.out.println("History after task1 calling is: ");
        System.out.println(fileBackedTasksManager1.getHistory());

        fileBackedTasksManager1.getTaskById(task2.getId());
        System.out.println("History after task2 calling is: ");
        System.out.println(fileBackedTasksManager1.getHistory());

        fileBackedTasksManager1.getSubtaskById(subtask1.getId());
        System.out.println("History after subtask1 calling is: ");
        System.out.println(fileBackedTasksManager1.getHistory());

        fileBackedTasksManager1.getSubtaskById(subtask2.getId());
        System.out.println("History after subtask2 calling is: ");
        System.out.println(fileBackedTasksManager1.getHistory());

        fileBackedTasksManager1.getSubtaskById(subtask3.getId());
        System.out.println("History after subtask3 calling is: ");
        System.out.println(fileBackedTasksManager1.getHistory());

        fileBackedTasksManager1.getEpicById(epic1.getId());
        System.out.println("History after epic1 calling is: ");
        System.out.println(fileBackedTasksManager1.getHistory());

        fileBackedTasksManager1.getEpicById(epic2.getId());
        System.out.println("History after epic2 calling is: ");
        System.out.println(fileBackedTasksManager1.getHistory());

        System.out.println("Map of tasks for fileBackedTasksManager1 is:");
        System.out.println(fileBackedTasksManager1.tasks);
        System.out.println("Map of epics for fileBackedTasksManager1 is:");
        System.out.println(fileBackedTasksManager1.epics);
        System.out.println("Map of subtasks for fileBackedTasksManager1 is:");
        System.out.println(fileBackedTasksManager1.subtasks);
        System.out.println("History is:");
        System.out.println(fileBackedTasksManager1.getHistory());

        try {
            FileBackedTasksManager fileBackedTasksManager2 = FileBackedTasksManager.loadFromFile(file);
            System.out.println("Map of tasks for fileBackedTasksManager2 is:");
            System.out.println(fileBackedTasksManager2.tasks);
            System.out.println("Map of epics for fileBackedTasksManager2 is:");
            System.out.println(fileBackedTasksManager2.epics);
            System.out.println("Map of subtasks for fileBackedTasksManager2 is:");
            System.out.println(fileBackedTasksManager2.subtasks);
            System.out.println("History is:");
            System.out.println(fileBackedTasksManager2.getHistory());
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}