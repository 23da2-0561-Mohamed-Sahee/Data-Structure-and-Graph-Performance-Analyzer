package analyzer.stack;

import analyzer.util.EmptyStructureException;

/**
 * Fixed-capacity stack (LIFO) implemented with an array.
 * push / pop / peek are all O(1).
 */
public class ArrayStack {

    private final int[] items;
    private int top;          // index of the top element, -1 when empty

    public ArrayStack(int capacity) {
        items = new int[capacity];
        top = -1;
    }

    public boolean isEmpty() { return top == -1; }

    public boolean isFull() { return top == items.length - 1; }

    public int size() { return top + 1; }

    public int capacity() { return items.length; }

    /** Adds an element on top. Throws IllegalStateException on overflow. */
    public void push(int value) {
        if (isFull()) {
            throw new IllegalStateException("Stack Overflow - the stack is full (capacity "
                    + items.length + ").");
        }
        items[++top] = value;
    }

    /** Removes and returns the top element. Throws if the stack is empty. */
    public int pop() {
        if (isEmpty()) {
            throw new EmptyStructureException("Stack Underflow - cannot pop from an empty stack.");
        }
        return items[top--];
    }

    /** Returns the top element without removing it. */
    public int peek() {
        if (isEmpty()) {
            throw new EmptyStructureException("Stack is empty - nothing to peek.");
        }
        return items[top];
    }

    public void clear() { top = -1; }

    public void display() {
        if (isEmpty()) {
            System.out.println("Stack is empty.");
            return;
        }
        System.out.println("   +-------+");
        for (int i = top; i >= 0; i--) {
            System.out.printf("   | %5d |%s%n", items[i], i == top ? "  <-- TOP" : "");
            System.out.println("   +-------+");
        }
        System.out.println("Size: " + size() + " / " + capacity());
    }
}
