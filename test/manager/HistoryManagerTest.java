package manager;

import org.junit.jupiter.api.Test;

import task.Task;

import static org.junit.jupiter.api.Assertions.*;

public abstract class HistoryManagerTest<T extends HistoryManager> {
    protected T historyManager;
    protected InMemoryTaskManager inMemoryTaskManager;
    protected Task task1;
    protected Task task2;
    protected Task task3;

    public HistoryManagerTest(T historyManager) {
        this.historyManager = historyManager;
        this.inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();
        this.task1 = inMemoryTaskManager.createTask("task1", "1st task");
        this.task2 = inMemoryTaskManager.createTask("task2", "2d task");
        this.task3 = inMemoryTaskManager.createTask("task3", "3rd task");
    }

    //tests for methods from HistoryManager interface
    @Test
    public void shouldReturnEmptyHistory() {
        assertTrue(inMemoryTaskManager.getHistory().isEmpty(), "History is not an empty.");
    }

    @Test
    public void shouldReturnNonEmptyHistory() {
        historyManager.add(task1);
        assertFalse(historyManager.getHistory().isEmpty(), "History is not an empty.");
    }

    @Test
    public void shouldNotAddSameElementTwice() {
        historyManager.add(task1);

        int sizeBeforeTwiceAdding = historyManager.getHistory().size();

        historyManager.add(task1);

        assertEquals(sizeBeforeTwiceAdding, historyManager.getHistory().size(),
                "Same element added twice to history.");
    }

    @Test
    public void shouldAddElement() {
        assertTrue(historyManager.getHistory().isEmpty(), "History is not an empty.");

        historyManager.add(task1);

        assertTrue(historyManager.getHistory().contains(task1), "History does not contain task1.");
        assertFalse(historyManager.getHistory().isEmpty(), "History is empty.");
    }

    @Test
    public void shouldRemoveElement() {
        historyManager.add(task1);

        assertTrue(historyManager.getHistory().contains(task1), "History does not contain task1.");

        historyManager.remove(task1.getId());

        assertTrue(historyManager.getHistory().isEmpty(), "History contains task1.");
    }

    @Test
    public void shouldNotThrowNoExceptionWhileRemovingByIncorrectId() {
        assertDoesNotThrow(() -> {
            historyManager.remove(task1.getId());
            historyManager.remove(100500);
            historyManager.remove(0);
            historyManager.remove(-100500);
        });
    }

    @Test
    public void shouldRemoveFromBeginningOfHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        assertTrue(historyManager.getHistory().contains(task1), "History doesn't contain task1.");
        assertTrue(historyManager.getHistory().contains(task2), "History doesn't contain task2.");
        assertTrue(historyManager.getHistory().contains(task3), "History doesn't contain task3.");

        historyManager.remove(task1.getId());

        assertFalse(historyManager.getHistory().contains(task1), "History contains task1");
        assertTrue(historyManager.getHistory().contains(task2), "History doesn't contain task2.");
        assertTrue(historyManager.getHistory().contains(task3), "History doesn't contain task3.");
    }

    @Test
    public void shouldRemoveFromEndOfHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        assertTrue(historyManager.getHistory().contains(task1), "History doesn't contain task1.");
        assertTrue(historyManager.getHistory().contains(task2), "History doesn't contain task2.");
        assertTrue(historyManager.getHistory().contains(task3), "History doesn't contain task3.");

        historyManager.remove(task3.getId());

        assertFalse(historyManager.getHistory().contains(task3), "History contains task3");
        assertTrue(historyManager.getHistory().contains(task1), "History doesn't contain task1.");
        assertTrue(historyManager.getHistory().contains(task2), "History doesn't contain task2.");
    }

    @Test
    public void shouldRemoveFromMiddleOfHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        assertTrue(historyManager.getHistory().contains(task1), "History doesn't contain task1.");
        assertTrue(historyManager.getHistory().contains(task2), "History doesn't contain task2.");
        assertTrue(historyManager.getHistory().contains(task3), "History doesn't contain task3.");

        historyManager.remove(task2.getId());

        assertFalse(historyManager.getHistory().contains(task2), "History contains task2");
        assertTrue(historyManager.getHistory().contains(task1), "History doesn't contain task1.");
        assertTrue(historyManager.getHistory().contains(task3), "History doesn't contain task3.");
    }

    //tests for InMemoryTaskManager-specific methods

    @Test
    public void shouldPutFirstElementToEmptyList() {
        assertTrue(((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty(),
                "List is not an empty");

        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task1);

        assertTrue(((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().contains(task1),
                "List doesn't contain task1");
    }

    @Test
    public void shouldThrowNoExceptionWhilePuttingSameElementTwice() {
        assertTrue(((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty(),
                "List is not an empty");

        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task1);

        assertTrue(((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().contains(task1),
                "List doesn't contain task1");
        assertDoesNotThrow(() -> ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task1));
    }

    @Test
    public void shouldPutElementToNonEmptyList() {
        assertTrue(((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty(),
                "List is not an empty");

        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task1);

        assertTrue(((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().contains(task1),
                "List doesn't contain task1");

        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task2);

        assertTrue(((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().contains(task2),
                "List doesn't contain task1");
        assertEquals(2, ((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().size(),
                "An error occurred while putting elements to list");
    }

    @Test
    public void shouldThrowNoExceptionWhileRemovingNodeByIncorrectId() {
        assertTrue(((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty(),
                "List is not an empty");

        InMemoryHistoryManager.CustomLinkedList.Node<Task> node =
                new InMemoryHistoryManager.CustomLinkedList.Node<>(task1);

        assertDoesNotThrow(() -> ((InMemoryHistoryManager) historyManager).customLinkedList.
                removeNode(node));
    }

    @Test
    public void shouldRemoveNodeFromBeginningOfList() {
        assertTrue(((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty(),
                "List is not an empty");

        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task1);
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task2);
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task3);

        assertTrue(historyManager.getHistory().contains(task1), "History doesn't contain task1.");
        assertTrue(historyManager.getHistory().contains(task2), "History doesn't contain task2.");
        assertTrue(historyManager.getHistory().contains(task3), "History doesn't contain task3.");

        InMemoryHistoryManager.CustomLinkedList.Node<Task> node =
                new InMemoryHistoryManager.CustomLinkedList.Node<>(task1);

        ((InMemoryHistoryManager) historyManager).customLinkedList.removeNode(node);

        assertFalse(historyManager.getHistory().contains(task1), "History contains task1");
        assertTrue(historyManager.getHistory().contains(task2), "History doesn't contain task2.");
        assertTrue(historyManager.getHistory().contains(task3), "History doesn't contain task3.");
    }

    @Test
    public void shouldRemoveNodeFromEndOfList() {
        assertTrue(((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty(),
                "List is not an empty");

        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task1);
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task2);
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task3);

        assertTrue(historyManager.getHistory().contains(task1), "History doesn't contain task1.");
        assertTrue(historyManager.getHistory().contains(task2), "History doesn't contain task2.");
        assertTrue(historyManager.getHistory().contains(task3), "History doesn't contain task3.");

        InMemoryHistoryManager.CustomLinkedList.Node<Task> node =
                new InMemoryHistoryManager.CustomLinkedList.Node<>(task3);

        ((InMemoryHistoryManager) historyManager).customLinkedList.removeNode(node);

        assertFalse(historyManager.getHistory().contains(task3), "History contains task3");
        assertTrue(historyManager.getHistory().contains(task1), "History doesn't contain task1.");
        assertTrue(historyManager.getHistory().contains(task2), "History doesn't contain task2.");
    }

    @Test
    public void shouldRemoveNodeFromMiddleOfList() {
        assertTrue(((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty(),
                "List is not an empty");

        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task1);
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task2);
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task3);

        assertTrue(historyManager.getHistory().contains(task1), "History doesn't contain task1.");
        assertTrue(historyManager.getHistory().contains(task2), "History doesn't contain task2.");
        assertTrue(historyManager.getHistory().contains(task3), "History doesn't contain task3.");

        InMemoryHistoryManager.CustomLinkedList.Node<Task> node =
                new InMemoryHistoryManager.CustomLinkedList.Node<>(task2);

        ((InMemoryHistoryManager) historyManager).customLinkedList.removeNode(node);

        assertFalse(historyManager.getHistory().contains(task2), "History contains task2");
        assertTrue(historyManager.getHistory().contains(task1), "History doesn't contain task1.");
        assertTrue(historyManager.getHistory().contains(task3), "History doesn't contain task.");
    }

    @Test
    public void shouldReturnEmptyListOfTasks() {
        assertTrue(((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty(),
                "List is not an empty");
    }

    @Test
    public void shouldReturnNonEmptyListOfTasks() {
        assertTrue(((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty(),
                "List is not an empty");

        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task1);

        assertFalse(((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty(),
                "List is empty");
        assertTrue(((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().contains(task1),
                "List doesn't contain task1");
    }
}