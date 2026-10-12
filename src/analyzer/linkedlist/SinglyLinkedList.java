package analyzer.linkedlist;

import analyzer.search.SearchResult;
import analyzer.util.EmptyStructureException;

/**
 * Singly linked list of integers.
 * insertFirst / deleteFirst are O(1); search, insertLast, deleteByValue are O(n).
 */
public class SinglyLinkedList {

    /** One node: a value plus a reference to the next node. */
    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node head;
    private int size;

    public int size() { return size; }

    public boolean isEmpty() { return head == null; }

    /** Insert at the head - O(1). */
    public void insertFirst(int value) {
        Node node = new Node(value);
        node.next = head;
        head = node;
        size++;
    }

    /** Insert at the tail - O(n) (we must walk to the last node). */
    public void insertLast(int value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
        } else {
            Node cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = node;
        }
        size++;
    }

    /** Insert so the new node ends up at the given position (0 = head). */
    public void insertAt(int position, int value) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Position must be between 0 and " + size);
        }
        if (position == 0) {
            insertFirst(value);
            return;
        }
        Node prev = head;
        for (int i = 0; i < position - 1; i++) {
            prev = prev.next;
        }
        Node node = new Node(value);
        node.next = prev.next;
        prev.next = node;
        size++;
    }

    /** Removes the head node and returns its value. */
    public int deleteFirst() {
        if (head == null) {
            throw new EmptyStructureException("Linked list is empty - nothing to delete.");
        }
        int value = head.value;
        head = head.next;
        size--;
        return value;
    }

    /** Deletes the first node holding the value. Returns true if one was removed. */
    public boolean deleteByValue(int value) {
        if (head == null) {
            throw new EmptyStructureException("Linked list is empty - nothing to delete.");
        }
        if (head.value == value) {
            head = head.next;
            size--;
            return true;
        }
        Node cur = head;
        while (cur.next != null && cur.next.value != value) {
            cur = cur.next;
        }
        if (cur.next == null) {
            return false;
        }
        cur.next = cur.next.next;   // unlink the node
        size--;
        return true;
    }

    /** Linear search through the nodes; reports position and steps. */
    public SearchResult search(int target) {
        long start = System.nanoTime();
        int steps = 0;
        int position = -1;
        Node cur = head;
        int i = 0;
        while (cur != null) {
            steps++;
            if (cur.value == target) {
                position = i;
                break;
            }
            cur = cur.next;
            i++;
        }
        return new SearchResult("Linked List", position, steps, System.nanoTime() - start);
    }

    /** Reverses the list in place - O(n). */
    public void reverse() {
        Node prev = null;
        Node cur = head;
        while (cur != null) {
            Node next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        head = prev;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    public void display() {
        if (head == null) {
            System.out.println("Linked list is empty.");
            return;
        }
        StringBuilder sb = new StringBuilder("HEAD -> ");
        for (Node cur = head; cur != null; cur = cur.next) {
            sb.append("[").append(cur.value).append("] -> ");
        }
        sb.append("NULL");
        System.out.println(sb);
        System.out.println("Size: " + size);
    }
}
