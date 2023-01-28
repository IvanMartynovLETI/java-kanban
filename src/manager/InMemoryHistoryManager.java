package manager;

import task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final List<Task> history = new ArrayList<>();
    private static final int HISTORY_LENGTH = 10;

    @Override
    public void add(Task task) {
        if (history.size() >= HISTORY_LENGTH)
            history.remove(0);

        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }

}
