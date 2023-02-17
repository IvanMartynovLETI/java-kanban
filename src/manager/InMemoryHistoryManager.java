package manager;

import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    @SuppressWarnings({"uncheked", "rawtypes"})
    private final Map<Integer, Node> history = new HashMap<>();
    @SuppressWarnings({"uncheked", "rawtypes"})
    private Node head;
    @SuppressWarnings({"uncheked", "rawtypes"})
    private Node tail;

    private static class Node<T extends Task> {
        private final T task;
        private Node<T> prev;
        private Node<T> next;

        protected Node(T task) {
            this.task = task;
            this.prev = null;
            this.next = null;
        }
    }

    @SuppressWarnings({"uncheked", "rawtypes"})
    public void linkLast(Node node) {
        if ((head == null) && (tail == null)) {//изначально список пустой
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> listOfTasksFromHistory = new ArrayList<>();
        @SuppressWarnings({"uncheked", "rawtypes"})
        Node currentNode = head;

        while (currentNode != null) {
            listOfTasksFromHistory.add(currentNode.task);
            currentNode = currentNode.next;
        }
        return listOfTasksFromHistory;
    }

    @Override
    public void add(Task task) {
        @SuppressWarnings({"uncheked", "rawtypes"})
        Node newNode = new Node<>(task);
        removeNode(newNode);
        linkLast(newNode);
        history.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
        if (head != null) { //удаление имеет смысл, только когда есть, что удалять
            if (history.containsKey(id)) { //удаляемый элемент должен существовать в history
                @SuppressWarnings({"uncheked", "rawtypes"})
                Node nodeFound = history.get(id);
                if ((nodeFound.prev == null) && (nodeFound.next == null)) {//удаляемый элемент-единственный
                    head = null;
                    tail = null;
                } else if (nodeFound.prev == null) {//удаляемый элемент-первый
                    head = nodeFound.next;
                    head.prev = null;
                    nodeFound.next = null;
                } else if (nodeFound.next == null) {//удаляемый элемент-последний
                    tail = nodeFound.prev;
                    tail.next = null;
                    nodeFound.prev = null;
                } else {//удаляемый элемент не первый и не последний
                    nodeFound.prev.next = nodeFound.next;
                    nodeFound.next.prev = nodeFound.prev;
                    nodeFound.prev = null;
                    nodeFound.next = null;
                }
                history.remove(id);
            }
        }
    }

    @SuppressWarnings({"uncheked", "rawtypes"})
    public void removeNode(Node node) {
        if (history.containsKey(node.task.getId())) {
            remove(node.task.getId());
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }
}
