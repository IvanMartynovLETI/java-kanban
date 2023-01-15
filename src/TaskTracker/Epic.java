package TaskTracker;

public class Epic extends Task {
    protected Epic(int epicID, String epicName, String epicDescription) {
        super(epicID, epicName, epicDescription);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "epicID=" + taskID +
                ", epicName='" + taskName + '\'' +
                ", epicDescription='" + taskDescription + '\'' +
                ", epicStatus='" + taskStatus + '\'' +
                '}';
    }
}
