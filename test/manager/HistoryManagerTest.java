package manager;

import org.junit.Test;
import task.Task;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
        assertTrue("History is not an empty.", inMemoryTaskManager.getHistory().isEmpty());
    }

    @Test
    public void shouldReturnNonEmptyHistory() {
        historyManager.add(task1);
        assertFalse("History is not an empty.", historyManager.getHistory().isEmpty());
    }

    @Test
    public void shouldNotAddSameElementTwice() {
        historyManager.add(task1);
        int sizeBeforeTwiceAdding = historyManager.getHistory().size();
        historyManager.add(task1);
        assertEquals("Same element added twice to history.",
                sizeBeforeTwiceAdding, historyManager.getHistory().size());
    }

    @Test
    public void shouldAddElement() {
        assertTrue("History is not an empty.", historyManager.getHistory().isEmpty());
        historyManager.add(task1);
        assertTrue("History does not contain task1.", historyManager.getHistory().contains(task1));
        assertFalse("History is empty.", historyManager.getHistory().isEmpty());
    }

    @Test
    public void shouldRemoveElement() {
        historyManager.add(task1);
        assertTrue("History does not contain task1.", historyManager.getHistory().contains(task1));
        historyManager.remove(task1.getId());
        assertTrue("History contains task1.", historyManager.getHistory().isEmpty());
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
        assertTrue("History doesn't contain task1.", historyManager.getHistory().contains(task1));
        assertTrue("History doesn't contain task2.", historyManager.getHistory().contains(task2));
        assertTrue("History doesn't contain task3.", historyManager.getHistory().contains(task3));
        historyManager.remove(task1.getId());
        assertFalse("History contains task1", historyManager.getHistory().contains(task1));
        assertTrue("History doesn't contain task2.", historyManager.getHistory().contains(task2));
        assertTrue("History doesn't contain task3.", historyManager.getHistory().contains(task3));
    }

    @Test
    public void shouldRemoveFromEndOfHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        assertTrue("History doesn't contain task1.", historyManager.getHistory().contains(task1));
        assertTrue("History doesn't contain task2.", historyManager.getHistory().contains(task2));
        assertTrue("History doesn't contain task3.", historyManager.getHistory().contains(task3));
        historyManager.remove(task3.getId());
        assertFalse("History contains task3", historyManager.getHistory().contains(task3));
        assertTrue("History doesn't contain task1.", historyManager.getHistory().contains(task1));
        assertTrue("History doesn't contain task2.", historyManager.getHistory().contains(task2));
    }

    @Test
    public void shouldRemoveFromMiddleOfHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        assertTrue("History doesn't contain task1.", historyManager.getHistory().contains(task1));
        assertTrue("History doesn't contain task2.", historyManager.getHistory().contains(task2));
        assertTrue("History doesn't contain task3.", historyManager.getHistory().contains(task3));
        historyManager.remove(task2.getId());
        assertFalse("History contains task2", historyManager.getHistory().contains(task2));
        assertTrue("History doesn't contain task1.", historyManager.getHistory().contains(task1));
        assertTrue("History doesn't contain task3.", historyManager.getHistory().contains(task3));
    }

    //tests for InMemoryTaskManager-specific methods

    @Test
    public void shouldPutFirstElementToEmptyList() {
        assertTrue("List is not an empty",
                ((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty());
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task1);
        assertTrue("List doesn't contain task1",
                ((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().contains(task1));
    }

    @Test
    public void shouldThrowNoExceptionWhilePuttingSameElementTwice() {
        assertTrue("List is not an empty",
                ((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty());
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task1);
        assertTrue("List doesn't contain task1",
                ((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().contains(task1));
        assertDoesNotThrow(() -> ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task1));
    }

    @Test
    public void shouldPutElementToNonEmptyList() {
        assertTrue("List is not an empty",
                ((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty());
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task1);
        assertTrue("List doesn't contain task1",
                ((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().contains(task1));
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task2);
        assertTrue("List doesn't contain task1",
                ((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().contains(task2));
        assertEquals("An error occurred while putting elements to list", 2,
                ((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().size());
    }

    @Test
    public void shouldThrowNoExceptionWhileRemovingNodeByIncorrectId() {
        assertTrue("List is not an empty",
                ((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty());
        InMemoryHistoryManager.CustomLinkedList.Node<Task> node =
                new InMemoryHistoryManager.CustomLinkedList.Node<>(task1);
        assertDoesNotThrow(() -> ((InMemoryHistoryManager) historyManager).customLinkedList.
                removeNode(node));
    }

    @Test
    public void shouldRemoveNodeFromBeginningOfList() {
        assertTrue("List is not an empty",
                ((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty());
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task1);
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task2);
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task3);
        assertTrue("History doesn't contain task1.", historyManager.getHistory().contains(task1));
        assertTrue("History doesn't contain task2.", historyManager.getHistory().contains(task2));
        assertTrue("History doesn't contain task3.", historyManager.getHistory().contains(task3));
        InMemoryHistoryManager.CustomLinkedList.Node<Task> node =
                new InMemoryHistoryManager.CustomLinkedList.Node<>(task1);
        ((InMemoryHistoryManager) historyManager).customLinkedList.removeNode(node);
        assertFalse("History contains task1", historyManager.getHistory().contains(task1));
        assertTrue("History doesn't contain task2.", historyManager.getHistory().contains(task2));
        assertTrue("History doesn't contain task3.", historyManager.getHistory().contains(task3));
    }

    @Test
    public void shouldRemoveNodeFromEndOfList() {
        assertTrue("List is not an empty",
                ((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty());
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task1);
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task2);
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task3);
        assertTrue("History doesn't contain task1.", historyManager.getHistory().contains(task1));
        assertTrue("History doesn't contain task2.", historyManager.getHistory().contains(task2));
        assertTrue("History doesn't contain task3.", historyManager.getHistory().contains(task3));
        InMemoryHistoryManager.CustomLinkedList.Node<Task> node =
                new InMemoryHistoryManager.CustomLinkedList.Node<>(task3);
        ((InMemoryHistoryManager) historyManager).customLinkedList.removeNode(node);
        assertFalse("History contains task3", historyManager.getHistory().contains(task3));
        assertTrue("History doesn't contain task1.", historyManager.getHistory().contains(task1));
        assertTrue("History doesn't contain task2.", historyManager.getHistory().contains(task2));
    }

    @Test
    public void shouldRemoveNodeFromMiddleOfList() {
        assertTrue("List is not an empty",
                ((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty());
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task1);
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task2);
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task3);
        assertTrue("History doesn't contain task1.", historyManager.getHistory().contains(task1));
        assertTrue("History doesn't contain task2.", historyManager.getHistory().contains(task2));
        assertTrue("History doesn't contain task3.", historyManager.getHistory().contains(task3));
        InMemoryHistoryManager.CustomLinkedList.Node<Task> node =
                new InMemoryHistoryManager.CustomLinkedList.Node<>(task2);
        ((InMemoryHistoryManager) historyManager).customLinkedList.removeNode(node);
        assertFalse("History contains task2", historyManager.getHistory().contains(task2));
        assertTrue("History doesn't contain task1.", historyManager.getHistory().contains(task1));
        assertTrue("History doesn't contain task.", historyManager.getHistory().contains(task3));
    }

    @Test
    public void shouldReturnEmptyListOfTasks() {
        assertTrue("List is not an empty",
                ((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty());
    }

    @Test
    public void shouldReturnNonEmptyListOfTasks() {
        assertTrue("List is not an empty",
                ((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty());
        ((InMemoryHistoryManager) historyManager).customLinkedList.linkLast(task1);
        assertFalse("List is empty",
                ((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().isEmpty());
        assertTrue("List doesn't contain task1",
                ((InMemoryHistoryManager) historyManager).customLinkedList.getTasks().contains(task1));
    }
}