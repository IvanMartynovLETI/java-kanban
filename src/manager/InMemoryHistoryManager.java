package manager;

import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();

    @SuppressWarnings({"uncheked", "rawtypes"})
    public static class CustomLinkedList<T extends Task> {

        public static class Node<E extends Task> {
            private final E task;
            private Node<E> prev;
            private Node<E> next;

            public Node(E task) {
                this.task = task;
                this.prev = null;
                this.next = null;
            }
        }

        private final Map<Integer, Node> history = new HashMap<>();
        private Node head;
        private Node tail;

        public void linkLast(T task) {
            Node newNode = new Node<>(task);
            if ((head == null) && (tail == null)) {//изначально список пустой
                head = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
            }
            tail = newNode;
            history.put(newNode.task.getId(), newNode);
        }

        public ArrayList<Task> getTasks() {
            ArrayList<Task> listOfTasksFromHistory = new ArrayList<>();
            Node currentNode = head;

            while (currentNode != null) {
                listOfTasksFromHistory.add(currentNode.task);
                currentNode = currentNode.next;
            }
            return listOfTasksFromHistory;
        }

        public void removeNode(Node node) {
            if (history.containsKey(node.task.getId())) {
                if (head != null) { //удаление имеет смысл, только когда есть, что удалять
                    if (history.containsKey(node.task.getId())) { //удаляемый элемент должен существовать в history
                        Node nodeFound = history.get(node.task.getId());
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
                        history.remove(node.task.getId());
                    }
                }
            }
        }
    }

    @Override
    public void add(Task task) {
        remove(task.getId());
        customLinkedList.linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (customLinkedList.history.containsKey(id)) {
            customLinkedList.removeNode(customLinkedList.history.get(id));
        }
    }

    @Override
    public List<Task> getHistory() {
        return customLinkedList.getTasks();
    }
}