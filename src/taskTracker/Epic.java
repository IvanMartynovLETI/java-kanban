package taskTracker;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    protected ArrayList<Integer> idsOfSubtsks;

    protected Epic(int epicId, String epicName, String epicDescription) {
        super(epicId, epicName, epicDescription);
        idsOfSubtsks = new ArrayList<>();
    }

    protected void attachSubtaskToEpic(int subtaskId) {
        idsOfSubtsks.add(subtaskId);
    }

    protected void unattachSubtaskFromEpic(int subtaskId) {
        Object obj = subtaskId;
        idsOfSubtsks.remove(obj);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "epicId=" + taskId +
                ", epicName='" + taskName + '\'' +
                ", epicDescription='" + taskDescription + '\'' +
                ", epicStatus='" + taskStatus + '\'' +
                ", idsOfSubtsks=" + idsOfSubtsks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(idsOfSubtsks, epic.idsOfSubtsks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idsOfSubtsks);
    }
}



