package taskTracker;

import java.util.Objects;

public class Task {
    protected int taskId;
    protected String taskName;
    protected String taskDescription;
    protected String taskStatus;

    protected Task(int taskId, String taskName, String taskDescription) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = "NEW";
    }

    protected int getTaskId() {
        return taskId;
    }

    protected String getTaskStatus() {
        return taskStatus;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId
                && Objects.equals(taskName, task.taskName)
                && Objects.equals(taskDescription, task.taskDescription)
                && taskStatus.equals(task.taskStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, taskName, taskDescription, taskStatus);
    }
}



