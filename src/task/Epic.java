package task;

import manager.FileBackedTasksManager;
import manager.ManagerSaveException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    public ArrayList<Integer> subTaskIds;

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

    public void setStartTime(String setStartTime) {
        LocalDateTime ldtm = LocalDateTime.parse(setStartTime, FileBackedTasksManager.DATE_TIME_FORMATTER);

        if (this.startTime.getMinute() % FileBackedTasksManager.GRID_TIME_SPACE != 0) {
            throw new ManagerSaveException("Start time should be exactly divisible by "
                    + FileBackedTasksManager.startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER) + ".");
        }

        this.startTime = ldtm;

        if (this.duration.toMinutes() != 0) {
            setEndTime();
        }
    }

    @Override
    public String toString() {
        return "Epic{" +
                "epicId=" + id +
                ", epicName='" + name + '\'' +
                ", epicDescription='" + description + '\'' +
                ", epicStatus='" + status + '\'' +
                ", SubTaskIds=" + subTaskIds +
                ", startTime='" + startTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER) + '\'' +
                ", duration='" + duration.toMinutes() + '\'' +
                ", endTime='" + endTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER) + '\'' +
                '}';
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
        Epic epic = (Epic) o;
        return Objects.equals(subTaskIds, epic.subTaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskIds);
    }


    public void setEndTime(String endTime) {
        this.endTime = LocalDateTime.parse(endTime, FileBackedTasksManager.DATE_TIME_FORMATTER);

    }
}