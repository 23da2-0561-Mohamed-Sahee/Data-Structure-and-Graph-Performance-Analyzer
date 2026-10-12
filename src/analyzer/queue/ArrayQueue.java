package analyzer.queue;

import analyzer.util.EmptyStructureException;

/**
 * Fixed-capacity circular queue (FIFO) implemented with an array.
 * The circular design re-uses freed slots, so enqueue / dequeue are O(1).
 */
public class ArrayQueue {

    private final int[] items;
    private int front;   // index of the first element
    private int count;   // number of stored elements

    public ArrayQueue(int capacity) {
        items = new int[capacity];
        front = 0;
        count = 0;
    }

    public boolean isEmpty() { return count == 0; }

    public boolean isFull() { return count == items.length; }

    public int size() { return count; }

    public int capacity() { return items.length; }

    /** Adds an element at the rear. Throws IllegalStateException when full. */
    public void enqueue(int value) {
        if (isFull()) {
            throw new IllegalStateException("Queue Overflow - the queue is full (capacity "
                    + items.length + ").");
        }
        int rear = (front + count) % items.length;
        items[rear] = value;
        count++;
    }

    /** Removes and returns the front element. Throws if the queue is empty. */
    public int dequeue() {
        if (isEmpty()) {
            throw new EmptyStructureException("Queue Underflow - cannot dequeue from an empty queue.");
        }
        int value = items[front];
        front = (front + 1) % items.length;
        count--;
        return value;
    }

    /** Returns the front element without removing it. */
    public int peek() {
        if (isEmpty()) {
            throw new EmptyStructureException("Queue is empty - nothing at the front.");
        }
        return items[front];
    }

    public void clear() {
        front = 0;
        count = 0;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }
        StringBuilder sb = new StringBuilder("FRONT -> ");
        for (int i = 0; i < count; i++) {
            sb.append("[").append(items[(front + i) % items.length]).append("] ");
        }
        sb.append("<- REAR");
        System.out.println(sb);
        System.out.println("Size: " + count + " / " + items.length);
    }
}
