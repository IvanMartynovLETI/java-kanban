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
    protected LocalDateTime startDateTime;
    protected Duration duration;
    protected LocalDateTime endDateTime;

    public Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.startDateTime = LocalDateTime.parse("01.01.0001 00:00", FileBackedTasksManager.DATE_TIME_FORMATTER);
        this.duration = Duration.ofMinutes(0);
        this.endDateTime = startDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }

    public void setStartDateTime(String setStartDateTime) {
        LocalDateTime ldtm = LocalDateTime.parse(setStartDateTime, FileBackedTasksManager.DATE_TIME_FORMATTER);

        if (this.startDateTime.getMinute() % FileBackedTasksManager.GRID_TIME_SPACE != 0) {
            throw new ManagerSaveException("Start time should be exactly divisible by "
                    + FileBackedTasksManager.startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER) + ".");
        }

        this.startDateTime = ldtm;

        if (this.duration.toMinutes() != 0) {
            setEndDateTime();
        }
    }

    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

    public void setEndDateTime() {
        this.endDateTime = this.startDateTime.plus(this.duration);
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

        if (!this.startDateTime.equals(LocalDateTime.parse("01.01.0001 00:00",
                FileBackedTasksManager.DATE_TIME_FORMATTER))) {
            setEndDateTime();
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
                ", startDateTime='" + startDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER) + '\'' +
                ", duration='" + duration.toMinutes() + '\'' +
                ", endDateTime='" + endDateTime.format(FileBackedTasksManager.DATE_TIME_FORMATTER) + '\'' +
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
                && status == task.status && Objects.equals(startDateTime, task.startDateTime)
                && Objects.equals(duration, task.duration) && Objects.equals(endDateTime, task.endDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, startDateTime, duration, endDateTime);
    }

    public Integer getEpicId() {
        return 0;
    }
}