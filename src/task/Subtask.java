package task;

import manager.FileBackedTasksManager;

import java.util.Objects;

public class Subtask extends Task {
    private final int upperEpcId;

    public Subtask(int subtaskId, String subtaskName, String subtaskDescription, Epic upperEpc) {
        super(subtaskId, subtaskName, subtaskDescription);
        this.upperEpcId = upperEpc.id;
    }

    public int getUpperEpicId() {
        return upperEpcId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "subtaskId=" + id +
                ", SubtaskName='" + name + '\'' +
                ", SubtaskDescription='" + description + '\'' +
                ", SubtaskStatus='" + status + '\'' +
                ", upperEpcId='" + upperEpcId + '\'' +
                ", setStartTime='" + setStartTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER) + '\'' +
                ", duration='" + duration.toMinutes() + '\'' +
                ", endTime='" + endTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER) + '\'' +
                '}';
    }

    @Override
    public Integer getEpicId() {
        return getUpperEpicId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Subtask subtask = (Subtask) o;
        return upperEpcId == subtask.upperEpcId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), upperEpcId);
    }
}