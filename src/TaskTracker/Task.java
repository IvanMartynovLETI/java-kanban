package TaskTracker;

public class Task {
    protected int taskID;
    protected String taskName;
    protected String taskDescription;
    protected String taskStatus;

    protected Task(int taskID, String taskName, String taskDescription) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = "NEW";
    }

    protected String getTaskName() {
        return taskName;
    }

    protected int getTaskID() {
        return taskID;
    }

    protected String getTaskStatus() {
        return taskStatus;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskID=" + taskID +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                '}';
    }
}
