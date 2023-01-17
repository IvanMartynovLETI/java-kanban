package task;

import manager.TaskManager;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private final ArrayList<Integer> subTaskIds;

    public Epic(int epicId, String epicName, String epicDescription) {
        super(epicId, epicName, epicDescription);
        subTaskIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void attachSubtaskToEpic(int id) {
        subTaskIds.add(id);
    }

    public void unattachSubtaskFromEpic(int id) {
        Object obj = id;
        subTaskIds.remove(obj);
    }

    public void unattachAllSubtasksFromEpic() {
        subTaskIds.clear();
    }

    public Subtask getSubtaskById(int subtaskId, TaskManager taskManager) {
        Subtask subtaskToBeFound = null;

        for (Subtask subtask : taskManager.getListOfSubtasksByUpperEpicId(this.getId()))
            if (subtask.getId() == subtaskId) {
                subtaskToBeFound = subtask;
                break;
            }
        return subtaskToBeFound;
    }

    public void deleteSubtaskById(int subtaskId, TaskManager taskManager) {
        unattachSubtaskFromEpic(subtaskId);
        taskManager.getSubtasks().remove(subtaskId);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "epicId=" + id +
                ", epicName='" + name + '\'' +
                ", epicDescription='" + description + '\'' +
                ", epicStatus='" + status + '\'' +
                ", SubTaskIds=" + subTaskIds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTaskIds, epic.subTaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskIds);
    }
}