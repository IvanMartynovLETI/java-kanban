package manager;

import org.junit.Test;

import java.io.File;

public class InMemoryHistoryManagerTest extends HistoryManagerTest<HistoryManager> {

    public InMemoryHistoryManagerTest() {
        super(Managers.getDefaultHistory(), new FileBackedTasksManager(new File("log/log.csv")));
    }

    //tests for methods from HistoryManager interface

    @Test
    public void shouldReturnEmptyHistory() {
        super.shouldReturnEmptyHistory();
    }

    @Test
    public void shouldNotAddSameElementTwice() {
        super.shouldNotAddSameElementTwice();
    }

    @Test
    public void shouldReturnNonEmptyHistory() {
        super.shouldReturnNonEmptyHistory();
    }

    @Test
    public void shouldAddElement() {
        super.shouldAddElement();
    }

    @Test
    public void shouldRemoveElement() {
        super.shouldRemoveElement();
    }

    @Test
    public void shouldNotThrowNoExceptionWhileRemovingByIncorrectId() {
        super.shouldNotThrowNoExceptionWhileRemovingByIncorrectId();
    }

    @Test
    public void shouldRemoveFromBeginningOfHistory() {
        super.shouldRemoveFromBeginningOfHistory();
    }

    @Test
    public void shouldRemoveFromEndOfHistory() {
        super.shouldRemoveFromEndOfHistory();
    }

    @Test
    public void shouldRemoveFromMiddleOfHistory() {
        super.shouldRemoveFromMiddleOfHistory();
    }

    //tests for InMemoryTaskManager-specific methods
    @Test
    public void shouldPutFirstElementToEmptyList() {
        super.shouldPutFirstElementToEmptyList();
    }

    @Test
    public void shouldThrowNoExceptionWhilePuttingSameElementTwice() {
        super.shouldThrowNoExceptionWhilePuttingSameElementTwice();
    }

    @Test
    public void shouldPutElementToNonEmptyList() {
        super.shouldPutElementToNonEmptyList();
    }

    @Test
    public void shouldThrowNoExceptionWhileRemovingNodeByIncorrectId() {
        super.shouldThrowNoExceptionWhileRemovingNodeByIncorrectId();
    }

    @Test
    public void shouldRemoveNodeFromBeginningOfList() {
        super.shouldRemoveNodeFromBeginningOfList();
    }

    @Test
    public void shouldRemoveNodeFromEndOfList() {
        super.shouldRemoveNodeFromEndOfList();
    }

    @Test
    public void shouldRemoveNodeFromMiddleOfList() {
        super.shouldRemoveNodeFromMiddleOfList();
    }

    @Test
    public void shouldReturnEmptyListOfTasks() {
        super.shouldReturnEmptyListOfTasks();
    }

    @Test
    public void shouldReturnNonEmptyListOfTasks() {
        super.shouldReturnNonEmptyListOfTasks();
    }
}