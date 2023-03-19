package task;

import manager.FileBackedTasksManager;
import manager.ManagerSaveException;
import manager.Status;

import java.time.Duration;
import java.util.Objects;
import java.time.LocalDateTime;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected Status status;
    protected LocalDateTime setStartTime;
    protected Duration duration;
    protected LocalDateTime endTime;

    public Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.setStartTime = LocalDateTime.parse("01.01.0001 00:00", FileBackedTasksManager.DATE_TIME_FORMATTER);
        this.duration = Duration.ofMinutes(0);
        this.endTime = setStartTime;
    }

    public LocalDateTime getStartDateTime() {
        return this.setStartTime;
    }

    public void setStartTime(String setStartTime) {
        LocalDateTime ldtm = LocalDateTime.parse(setStartTime, FileBackedTasksManager.DATE_TIME_FORMATTER);

        if (ldtm.isBefore(FileBackedTasksManager.startDateTime)) {
            throw new ManagerSaveException("Incorrect start time of " + this.getName()
                    + ". Start time should be after than " + FileBackedTasksManager.startDateTime);
        }

        if (this.setStartTime.getMinute() % FileBackedTasksManager.GRID_TIME_SPACE != 0) {
            throw new ManagerSaveException("Start time should be exactly divisible by "
                    + FileBackedTasksManager.startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER) + ".");
        }

        this.setStartTime = ldtm;

        if (this.duration.toMinutes() != 0) {
            setEndTime();
        }
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime() {
        this.endTime = this.setStartTime.plus(this.duration);
    }

    public Duration getDuraTion() {
        return this.duration;
    }

    public void setDuration(Long duration) {

        if (duration % FileBackedTasksManager.GRID_TIME_SPACE != 0) {
            throw new ManagerSaveException("Duration time should by exactly divisible by "
                    + FileBackedTasksManager.GRID_TIME_SPACE + ".");
        }

        if (duration < 0) {
            throw new ManagerSaveException("Duration must be positive.");
        }


        this.duration = Duration.ofMinutes(duration);

        if (!this.setStartTime.equals(LocalDateTime.parse("01.01.0001 00:00",
                FileBackedTasksManager.DATE_TIME_FORMATTER))) {
            setEndTime();
        }
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return this.status;
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + id +
                ", taskName='" + name + '\'' +
                ", taskDescription='" + description + '\'' +
                ", taskStatus='" + status + '\'' +
                ", setStartTime='" + setStartTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER) + '\'' +
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

        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description)
                && status == task.status && Objects.equals(setStartTime, task.setStartTime)
                && Objects.equals(duration, task.duration) && Objects.equals(endTime, task.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, setStartTime, duration, endTime);
    }

    public Integer getEpicId() {
        return 0;
    }
}