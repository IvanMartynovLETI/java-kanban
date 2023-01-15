package TaskTracker;

public class Subtask extends Task {
    protected String upperEpicName;

    protected Subtask(int subtaskID, String subtaskName, String subtaskDescription, Epic epic) {
        super(subtaskID, subtaskName, subtaskDescription);
        this.upperEpicName = epic.getTaskName();
    }

    protected String getUpperEpicName() {
        return upperEpicName;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "upperEpicName='" + upperEpicName + '\'' +
                ", SubtaskID=" + taskID +
                ", SubtaskName='" + taskName + '\'' +
                ", SubtaskDescription='" + taskDescription + '\'' +
                ", SubtaskStatus='" + taskStatus + '\'' +
                '}';
    }
}
